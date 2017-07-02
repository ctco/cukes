package lv.ctco.cukes.rabbitmq.api.given;

import cucumber.api.java.en.Given;

public class SetupConnectionSteps {

    @Given("^connecting to host \"(.+)\"$")
    public void setHost(String host) {

    }

    @Given("^server responds on port (\\d+)$")
    public void setPort(int port) {

    }

    @Given("^connecting using username \"(.+)\" and password \"(.+)\"$")
    public void setUsernameAndPassword(String username, String password) {

    }

    @Given("^virtual host is \"(.+)\"$")
    public void setVirtualHost(String virtualHost) {

    }

    @Given("^using SSL$")
    public void setUseSSL() {

    }

    @Given("^not using SSL$")
    public void setDontUseSSL() {

    }
}
