package lv.ctco.cukes.ldap.sample;

public class Application {

    public static void main(String[] args) throws Exception {
        EmbeddedLDAPServer server = new EmbeddedLDAPServer();
        server.start();
    }
}
