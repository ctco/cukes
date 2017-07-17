package lv.ctco.cukes.ldap.internal.ldif;

import org.junit.Test;

import javax.naming.directory.Attributes;
import java.io.ByteArrayInputStream;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class LDIFUtilsTest {
    @Test
    public void read() throws Exception {
        Map<String, Attributes> entities = LDIFUtils.read(getClass().getResourceAsStream("/example.ldif"));
        assertThat(entities.size(), is(4));
    }

    @Test
    public void readSingleEntity() throws Exception {
        String ldif = "dn: dc=example,dc=com\n" +
            "objectClass: domain\n" +
            "objectClass: top\n" +
            "dc: example\n";
        Map<String, Attributes> entities = LDIFUtils.read(new ByteArrayInputStream(ldif.getBytes()));
        assertThat(entities.size(), is(1));
        String dn = "dc=example,dc=com";
        Attributes entity = entities.get(dn);
        assertThat(entity, notNullValue());

        assertThat(entity.get("dn"), nullValue());
        assertThat(entity.get("dc").get(), is("example"));
        assertThat(entity.get("objectClass").contains("domain"), is(true));
        assertThat(entity.get("objectClass").contains("top"), is(true));
    }

    @Test
    public void readMultipleEntities() throws Exception {
        String ldif = "dn: dc=example,dc=com\n" +
            "objectClass: domain\n" +
            "objectClass: top\n" +
            "dc: example\n" +
            "\n" +
            "dn: ou=Users,dc=example,dc=com\n" +
            "objectClass: organizationalUnit\n" +
            "objectClass: top\n" +
            "ou: Users\n";

        Map<String, Attributes> entities = LDIFUtils.read(new ByteArrayInputStream(ldif.getBytes()));
        assertThat(entities.size(), is(2));
        assertThat(entities.containsKey("dc=example,dc=com"), is(true));
        assertThat(entities.containsKey("ou=Users,dc=example,dc=com"), is(true));
    }

    @Test
    public void readWithLineBreaks() throws Exception {
        String ldif = "dn: dc=example,dc=com\n" +
            "objectClass: top\n" +
            "test: this is\n" +
            " multi-line text\n" +
            "dc: example\n";
        Map<String, Attributes> entities = LDIFUtils.read(new ByteArrayInputStream(ldif.getBytes()));
        assertThat(entities.size(), is(1));
        Attributes entity = entities.get("dc=example,dc=com");
        assertThat(entity.get("test").get(), is("this is multi-line text"));

    }
}
