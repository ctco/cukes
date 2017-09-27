package lv.ctco.cukes.ldap.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.matchers.ContainsPattern;
import lv.ctco.cukes.core.internal.resources.FilePathService;
import lv.ctco.cukes.ldap.internal.EntityService;
import lv.ctco.cukes.ldap.internal.ldif.LDIFUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Singleton
public class EntityFacade {

    private static final Map<String, Function<Integer, Matcher<Integer>>> sizeMatchers = new HashMap<>();

    static {
        sizeMatchers.put("=", Matchers::is);
        sizeMatchers.put(">", Matchers::greaterThan);
        sizeMatchers.put(">=", Matchers::greaterThanOrEqualTo);
        sizeMatchers.put("<", Matchers::lessThan);
        sizeMatchers.put("<=", Matchers::lessThanOrEqualTo);
        sizeMatchers.put("<>", Matchers::not);
    }

    private Attributes entity;
    private List<Attributes> searchResults = new ArrayList<>();

    @Inject
    EntityService entityService;
    @Inject
    FilePathService filePathService;

    public void initConfiguration() {
        entity = null;
    }

    public void readEntityByDn(String dn) {
        entity = entityService.getEntityByDn(dn);
    }

    public void entityExists() {
        assertThat(entity, notNullValue());
    }

    public void entityDoesNotExist() {
        assertThat(entity, nullValue());
    }

    public void deleteEntityByDn(String dn) {
        entityService.deleteEntityByDn(dn);
    }

    public Attribute getAttribute(String attribute) {
        if (entity == null) {
            throw new CukesRuntimeException("Entity was not loaded");
        }
        return entity.get(attribute);
    }

    public Attribute getNotNullAttribute(String attribute) {
        if (entity == null) {
            throw new CukesRuntimeException("Entity was not loaded");
        }
        Attribute attr = entity.get(attribute);
        assertThat("Expected that attribute '" + attribute + "' will be present", attr, notNullValue());
        return attr;
    }

    public void entityHasAttributeWithValue(String expectedAttr, String expectedValue) {
        Attribute actualAttr = getNotNullAttribute(expectedAttr);
        List<String> attributesList = new ArrayList<>();
        for(int i = 0; i < actualAttr.size(); i++){
            try {
                attributesList.add(actualAttr.get(i).toString());
            } catch (NamingException e) {
                throw new CukesRuntimeException(e);
            }
        }
        assertThat("Should have attribute '" + expectedAttr + "' with value '" + expectedValue + "'", attributesList, hasItem(expectedValue));
    }

    public void entityHasAttributeWithValueOtherThat(String attribute, String value) {
        Attribute attr = getNotNullAttribute(attribute);
        assertThat(attr.contains(value), is(false));
    }

    public void entityContainsAttribute(String attribute) {
        getNotNullAttribute(attribute);
    }

    public void entityDoesNotContainAttribute(String attribute) {
        Attribute attr = getAttribute(attribute);
        assertThat(attr, nullValue());
    }

    public void entityHasAttributeAsArrayOfSize(String attribute, String operator, int size) {
        Attribute attr = getNotNullAttribute(attribute);
        int count = 0;
        try {
            for (NamingEnumeration<?> e = attr.getAll(); e.hasMore(); e.next(), count++) {
            }
        } catch (NamingException e) {
            throw new CukesRuntimeException(e);
        }
        Function<Integer, Matcher<Integer>> matcherFunction = sizeMatchers.get(operator);
        if (matcherFunction == null) {
            throw new IllegalArgumentException("Unknown operator: " + operator);
        }
        assertThat(count, matcherFunction.apply(size));
    }

    public void entityHasAttributeWithValueMatchingPattern(String attribute, String pattern) {
        Attribute attr = getNotNullAttribute(attribute);
        Matcher<CharSequence> matcher = ContainsPattern.containsPattern(pattern);
        try {
            NamingEnumeration<?> e = attr.getAll();
            while (e.hasMore()) {
                Object next = e.next();
                String s = String.valueOf(next);
                if (matcher.matches(s)) {
                    return;
                }
            }
        } catch (NamingException ex) {
            throw new CukesRuntimeException(ex);
        }
        fail();
    }

    public void entityHasAttributeWithValueNotMatchingPattern(String attribute, String pattern) {
        Attribute attr = getNotNullAttribute(attribute);
        try {
            NamingEnumeration<?> e = attr.getAll();
            while (e.hasMore()) {
                Object next = e.next();
                String s = String.valueOf(next);
                assertThat(s, not(ContainsPattern.containsPattern(pattern)));
            }
        } catch (NamingException ex) {
            throw new CukesRuntimeException(ex);
        }
    }

    public void importLdif(String ldif) {
        try {
            importLdif(new ByteArrayInputStream(ldif.getBytes("UTF-8")));
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void importLdifFromFile(String ldifFile) {
        try {
            String path = filePathService.normalize(ldifFile);
            importLdif(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            throw new CukesRuntimeException(e);
        }
    }

    private void importLdif(InputStream inputStream) {
        try {
            Map<String, Attributes> entities = LDIFUtils.read(inputStream);
            for (Map.Entry<String, Attributes> entry : entities.entrySet()) {
                entityService.createEntity(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void entityMatchesLDIF(String ldif) {
        try {
            Map<String, Attributes> entities = LDIFUtils.read(new ByteArrayInputStream(ldif.getBytes("UTF-8")));
            assertThat(entities.size(), is(1));
            Attributes ldifEntity = entities.values().iterator().next();
            NamingEnumeration<? extends Attribute> attributes = ldifEntity.getAll();
            while (attributes.hasMore()) {
                Attribute attribute = attributes.next();
                NamingEnumeration<?> values = attribute.getAll();
                while (values.hasMore()) {
                    Object value = values.next();
                    entityHasAttributeWithValue(attribute.getID(), String.valueOf(value));
                }
            }
        } catch (NamingException | IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void searchByFilter(String dn, String filter) {
        searchResults.clear();
        searchResults.addAll(entityService.searchByFilter(dn, filter));
    }

    public void searchResultHasSize(String operator, int size) {
        Function<Integer, Matcher<Integer>> matcherFunction = sizeMatchers.get(operator);
        if (matcherFunction == null) {
            throw new IllegalArgumentException("Unknown operator: " + operator);
        }
        assertThat(searchResults.size(), matcherFunction.apply(size));
    }

    public void takeEntityFromSearchResults(int index) {
        if (index < 0 || index >= searchResults.size()) {
            throw new IllegalArgumentException("Cannot extract entity from search results set by index " + index +
                ". Total result set size is " + searchResults.size());
        }
        this.entity = searchResults.get(index);
    }
}
