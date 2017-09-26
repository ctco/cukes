package lv.ctco.cukes.ldap.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Singleton
public class EntityService {

    @Inject
    ConnectionService connectionService;

    public Attributes getEntityByDn(String dn) {
        try {
            DirContext context = connectionService.getContext();
            return context.getAttributes(dn);
        } catch (NamingException e) {
            return null;
        } finally {
            connectionService.close();
        }
    }

    public void createEntity(String dn, Attributes attributes) {
        try {
            DirContext context = connectionService.getContext();
            context.createSubcontext(dn, attributes);
        } catch (NamingException e) {
            throw new CukesRuntimeException("Cannot create entity by dn " + dn, e);
        } finally {
            connectionService.close();
        }
    }

    public void deleteEntityByDn(String dn) {
        try {
            LdapContext context = connectionService.getContext();
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> children = context.search(dn, "(objectclass=*)", searchControls);
            TreeSet<String> dnsToDelete = new TreeSet<>(new DnComparator(true));
            while (children.hasMoreElements()) {
                SearchResult childResult = children.nextElement();
                String childDn = childResult.getNameInNamespace();
                dnsToDelete.add(childDn);
            }
            for (String s : dnsToDelete) {
                context.destroySubcontext(s);
            }
        } catch (NamingException e) {
            throw new CukesRuntimeException("Cannot delete entity by dn " + dn, e);
        } finally {
            connectionService.close();
        }
    }

    public List<Attributes> searchByFilter(String dn, String filter){
        try {
            LdapContext context = connectionService.getContext();
            NamingEnumeration<SearchResult> searchResults = context.search(dn, filter, new SearchControls());
            List<Attributes> attributesList = new ArrayList<>();
            while (searchResults.hasMore()) {
                SearchResult searchResult = searchResults.next();
                attributesList.add(searchResult.getAttributes());
            }
            return attributesList;
        } catch (NamingException ex) {
            throw new CukesRuntimeException(ex);
        } finally {
            connectionService.close();
        }
    }

    public void modifyByDn(String dn, List<ModificationItem> modificationItems) {
        try {
            LdapContext context = connectionService.getContext();
            context.modifyAttributes(dn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
        } catch (NamingException e) {
            throw new CukesRuntimeException("Cannot modify entity by dn " + dn, e);
        } finally {
            connectionService.close();
        }
    }
}
