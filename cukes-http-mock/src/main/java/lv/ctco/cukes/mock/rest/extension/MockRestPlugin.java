package lv.ctco.cukes.mock.rest.extension;

import com.google.inject.Inject;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.mock.rest.internal.MockClientServerFacade;

public class MockRestPlugin implements CukesPlugin {
    @Inject
    private MockClientServerFacade mockClientServerFacade;

    @Override
    public void beforeAllTests() {
        mockClientServerFacade.startAllServers();
    }

    @Override
    public void afterAllTests() {
        mockClientServerFacade.stopAllServers();
    }

    @Override
    public void beforeScenario() {
        mockClientServerFacade.resetAllServers();
    }

    @Override
    public void afterScenario() {
        //TODO verifyNoMoreInterations
    }
}
