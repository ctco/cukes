package lv.ctco.cukes.oauth;

import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.HashMap;
import java.util.Map;

public enum GrantType {

    client_credentials,
    password(OAuthCukesConstants.USER_NAME, OAuthCukesConstants.PASSWORD);

    private static final Map<String, String> attributeNameMapping = new HashMap<>();
    static {
        attributeNameMapping.put(OAuthCukesConstants.USER_NAME, "username");
        attributeNameMapping.put(OAuthCukesConstants.PASSWORD, "password");
    }

    private String[] requiredAttributes;

    GrantType(String... requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }

    public Map<String, String> getParameters(GlobalWorldFacade world) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", name());
        for (String attribute : requiredAttributes) {
            parameters.put(attributeNameMapping.get(attribute), world.getOrThrow(attribute));
        }
        return parameters;
    }
}
