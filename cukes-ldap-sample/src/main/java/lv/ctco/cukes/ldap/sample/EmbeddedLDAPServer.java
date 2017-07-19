package lv.ctco.cukes.ldap.sample;

import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.partition.Partition;
import org.apache.directory.server.core.factory.DefaultDirectoryServiceFactory;
import org.apache.directory.server.core.factory.DirectoryServiceFactory;
import org.apache.directory.server.core.partition.impl.avl.AvlPartition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.store.LdifFileLoader;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;

import java.io.File;

public class EmbeddedLDAPServer {
    public static final String USER = "uid=admin,ou=system";
    public static final String PASSWORD = "secret";
    public static final int PORT = 10389;

    private DirectoryService service;
    private LdapServer server;

    public void start() throws Exception {
        DirectoryServiceFactory factory = new DefaultDirectoryServiceFactory();
        factory.init("server");
        service = factory.getDirectoryService();
        service.addPartition(createPartition("default", "cn=test"));
        service.addPartition(createPartition("domain", "dc=example,dc=com"));

        server = new LdapServer();
        server.setDirectoryService(service);
        server.setTransports(new TcpTransport(PORT));
        server.start();
    }

    private Partition createPartition(String id, String suffix) throws LdapInvalidDnException {
        Partition partition = new AvlPartition(service.getSchemaManager());
        partition.setId(id);
        partition.setSuffixDn(new Dn(suffix));
        return partition;
    }

    public void stop() throws Exception {
        server.stop();
        service.shutdown();
    }

    public void loadLDIF(String fileName) {
        File ldifFile = new File(fileName);
        LdifFileLoader loader = new LdifFileLoader(service.getAdminSession(), ldifFile, null, getClass().getClassLoader());
        loader.execute();
    }
}
