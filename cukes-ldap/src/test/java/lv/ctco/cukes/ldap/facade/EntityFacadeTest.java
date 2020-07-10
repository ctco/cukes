package lv.ctco.cukes.ldap.facade;

import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.BasicAttributes;

import static org.mockito.internal.util.reflection.FieldSetter.setField;

public class EntityFacadeTest {
    EntityFacade entityFacade;

    @Before
    public void setUp() {
        entityFacade = new EntityFacade();
    }

    @Test
    public void byteArrayValueIsCheckedAsString() throws Exception {
        BasicAttributes entity = new BasicAttributes(true);
        entity.put("userPassword", new byte[]{50, 82, 115, 48, 67, 99, 54, 74});

        setField(entityFacade, entityFacade.getClass().getDeclaredField("entity"), entity);

        entityFacade.entityHasAttributeWithValue("userpassword", "2Rs0Cc6J");
    }

    @Test
    public void charArrayValueIsCheckedAsString() throws Exception {
        BasicAttributes entity = new BasicAttributes(true);
        entity.put("userPassword", new char[]{'h', 'e', 'l', 'l', 'o'});

        setField(entityFacade, entityFacade.getClass().getDeclaredField("entity"), entity);

        entityFacade.entityHasAttributeWithValue("userpassword", "hello");
    }

    @Test
    public void stringValueIsCheckedAsString() throws Exception {
        BasicAttributes entity = new BasicAttributes(true);
        entity.put("userPassword", "hello");

        setField(entityFacade, entityFacade.getClass().getDeclaredField("entity"), entity);

        entityFacade.entityHasAttributeWithValue("userpassword", "hello");
    }

    @Test
    public void intArrayValueIsCheckedAsString() throws Exception {
        BasicAttributes entity = new BasicAttributes(true);
        entity.put("userPassword", new int[]{1, 2, 3});

        setField(entityFacade, entityFacade.getClass().getDeclaredField("entity"), entity);

        entityFacade.entityHasAttributeWithValue("userpassword", "{1,2,3}");
    }

    @Test
    public void intValueIsCheckedAsString() throws Exception {
        BasicAttributes entity = new BasicAttributes(true);
        entity.put("userPassword", 3);

        setField(entityFacade, entityFacade.getClass().getDeclaredField("entity"), entity);

        entityFacade.entityHasAttributeWithValue("userpassword", "3");
    }
}
