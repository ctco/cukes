package lv.ctco.cukes.ldap.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

@Singleton
public class EntityService {

    @Inject
    ConnectionService connectionService;

    public Attributes getEntityByDn(String dn) {
        try {
            DirContext context = connectionService.getContext();
            return context.getAttributes(dn);
        } catch (NamingException e) {
            throw new CukesRuntimeException("Cannot retrieve entity by dn " + dn, e);
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

}
