package lv.ctco.cukes.oauth.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.oauth.OAuthCukesConstants;

@Singleton
public class OAuthFacade {

    @Inject
    GlobalWorldFacade worldFacade;

    public void setGrantType(String grantType) {
        worldFacade.put(OAuthCukesConstants.GRANT_TYPE, grantType);
        invalidateToken();
    }

    public void setScopes(String scopes) {
        worldFacade.put(OAuthCukesConstants.SCOPE, scopes);
        invalidateToken();
    }

    private void invalidateToken() {
        worldFacade.remove(OAuthCukesConstants.CACHED_TOKEN);
        worldFacade.remove(OAuthCukesConstants.TOKEN_EXPIRES_ON);
    }
}
