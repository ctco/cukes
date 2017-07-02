package lv.ctco.cukes.rabbitmq.api.given;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.rabbitmq.facade.SetupFacade;

@Singleton
public class SetupConnectionSteps {

    @Inject
    SetupFacade setupFacade;

    @Given("^connecting to host \"(.+)\"$")
    public void setHost(String host) {
        setupFacade.setHost(host);
    }

    @Given("^server responds on port (\\d+)$")
    public void setPort(int port) {
        setupFacade.setPort(port);
    }

    @Given("^connecting using username \"(.+)\" and password \"(.+)\"$")
    public void setUsernameAndPassword(String username, String password) {
        setupFacade.setUsername(username);
        setupFacade.setPassword(password);
    }

    @Given("^virtual host is \"(.+)\"$")
    public void setVirtualHost(String virtualHost) {
        setupFacade.setVirtualHost(virtualHost);
    }

    @Given("^using SSL$")
    public void setUseSSL() {
        setupFacade.setSsl(true);
    }

    @Given("^not using SSL$")
    public void setDontUseSSL() {
        setupFacade.setSsl(false);
    }
}
