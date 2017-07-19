package lv.ctco.cukes.ldap.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.ldap.internal.ConnectionService;

@Singleton
public class SetupFacade {
    @Inject
    GlobalWorldFacade world;
    @Inject
    ConnectionService connectionService;

    public void initConfiguration() {
        connectionService.close();
    }

    public void setUrl(String url) {
        world.put(ConnectionService.URL, url);
        connectionService.close();
    }

    public void setUserDn(String userDn) {
        world.put(ConnectionService.USER, userDn);
        connectionService.close();
    }

    public void setPassword(String password) {
        world.put(ConnectionService.PASSWORD, password);
        connectionService.close();
    }
}
