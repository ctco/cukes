package lv.ctco.cukes.ldap.sample;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.extension.CukesPlugin;

public class CukesLDAPBootstrap implements CukesPlugin {

    private EmbeddedLDAPServer server;

    @Override
    public void beforeAllTests() {
        server = new EmbeddedLDAPServer();
        try {
            server.start();
            server.loadLDIF("init.ldif");
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    @Override
    public void afterAllTests() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    @Override
    public void beforeScenario() {

    }

    @Override
    public void afterScenario() {

    }

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {

    }

    @Override
    public void afterRequest(Response response) {

    }
}
