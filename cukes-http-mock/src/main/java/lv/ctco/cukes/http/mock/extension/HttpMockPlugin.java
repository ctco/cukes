package lv.ctco.cukes.http.mock.extension;

import com.google.inject.Inject;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.http.mock.internal.MockClientServerFacade;

public class HttpMockPlugin implements CukesPlugin {
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
