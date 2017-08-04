package lv.ctco.cukes.rest.run.custom.steps;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomSteps {

    @Inject
    private StateSaver stateSaver;

    @Given("^save state for \"(.*)\" as (STARTED|WORKING|STOPPED)$")
    public void saveState(String object, String state) {
        stateSaver.put(object, State.valueOf(state));
    }

    @Then("^state for \"(.*)\" should be (STARTED|WORKING|STOPPED)$")
    public void checkState(String object, String state) {
        assertEquals(State.valueOf(state), stateSaver.get(object));
    }

    @Then("^state should be missing for \"(.*)\"$")
    public void checkState(String object) {
        assertTrue(!stateSaver.containsKey(object));
    }

    public static class StateSaver extends ForwardingMap<String, State> {

        private final Map<String, State> states = Maps.newHashMap();

        @Override
        protected Map<String, State> delegate() {
            return states;
        }
    }

    public enum State {
        STARTED, WORKING, STOPPED
    }
}
