package lv.ctco.cukes.ldap.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Singleton
public class ConnectionService {

    public static final String URL = "ldap.url";
    public static final String USER = "ldap.user";
    public static final String PASSWORD = "ldap.password";

    @Inject
    GlobalWorldFacade world;

    private DirContext context;

    public DirContext getContext() {
        if (context == null) {
            Hashtable<String, String> environment = new Hashtable<>();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            environment.put(Context.PROVIDER_URL, world.get(URL, "ldap://localhost:389"));
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, world.get(USER, "cn=admin"));
            environment.put(Context.SECURITY_CREDENTIALS, world.get(PASSWORD, "password"));
            try {
                context = new InitialDirContext(environment);
            } catch (NamingException e) {
                throw new CukesRuntimeException(e);
            }
        }
        return context;
    }

    public void close() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                throw new CukesRuntimeException(e);
            }
        }
        context = null;
    }

}
