package lv.ctco.cukes.plugins.api;

import cucumber.api.java.en.Given;

public class TestSteps {

    @Given("^wait for (\\d+) seconds?$")
    public void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }
}
