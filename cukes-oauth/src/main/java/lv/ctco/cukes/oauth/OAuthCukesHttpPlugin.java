package lv.ctco.cukes.oauth;

import com.google.inject.Inject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.oauth.internal.OAuthTokenRetriever;

import java.io.IOException;
import java.util.Optional;

public class OAuthCukesHttpPlugin implements CukesHttpPlugin {

    @Inject
    OAuthTokenRetriever tokenRetriever;
    @Inject
    GlobalWorldFacade worldFacade;

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {
        com.google.common.base.Optional<String> authType = worldFacade.get(CukesOptions.AUTH_TYPE);
        if (authType.isPresent()) {
            if (!"OAuth".equalsIgnoreCase(authType.get())) {
                return;
            }
        } else {
            return;
        }
        try {
            Optional<String> authorizationHeader = tokenRetriever.getAuthorizationHeader();
            authorizationHeader.ifPresent(s -> requestSpecification.auth().oauth2(s));
        } catch (IOException e) {
            throw new CukesRuntimeException("Cannot get OAuth token", e);
        }
    }

    @Override
    public void afterRequest(Response response) {

    }
}
