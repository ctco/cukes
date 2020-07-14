package lv.ctco.cukes.oauth.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.oauth.GrantType;
import lv.ctco.cukes.oauth.OAuthCukesConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Singleton
public class OAuthTokenRetriever {

    @Inject
    GlobalWorldFacade world;

    public Optional<String> getAuthorizationHeader() throws IOException {
        if (!world.getBoolean(OAuthCukesConstants.ENABLED, false)) {
            return Optional.empty();
        }
        Optional<String> cachedToken = world.get(OAuthCukesConstants.CACHED_TOKEN);
        Optional<String> expiresOn = world.get(OAuthCukesConstants.TOKEN_EXPIRES_ON);
        if (!cachedToken.isPresent()) {
            return retrieveAndCacheAccessToken();
        }
        long expiresOnAsSeconds = Long.parseLong(expiresOn.get());
        // Threshold - 1 minute till token expires
        if (System.currentTimeMillis() / 1000 + 60 > expiresOnAsSeconds) {
            return retrieveAndCacheAccessToken();
        }
        return Optional.of(cachedToken.get());
    }

    public Optional<String> retrieveAndCacheAccessToken() throws IOException {
        Map<String, String> map = getOAuthResponse();
        String accessToken = map.get("access_token");
        String expires = map.get("expires_in");
        world.put(OAuthCukesConstants.CACHED_TOKEN, accessToken);
        if (expires != null) {
            String expiresOn = String.valueOf(System.currentTimeMillis() / 1000 + Long.parseLong(expires));
            world.put(OAuthCukesConstants.TOKEN_EXPIRES_ON, expiresOn);
        }
        return Optional.of(accessToken);
    }

    public Map<String, String> getOAuthResponse() throws IOException {
        String authServer = world.getOrThrow(OAuthCukesConstants.AUTH_SERVER);
        String clientId = world.getOrThrow(OAuthCukesConstants.CLIENT_ID);
        String clientSecret = world.getOrThrow(OAuthCukesConstants.CLIENT_SECRET);
        String grantType = world.getOrThrow(OAuthCukesConstants.GRANT_TYPE);

        URL url = new URL(authServer);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Authorization", "Basic " + Base64.encodeBase64String((clientId + ":" + clientSecret).getBytes()));
        connection.addRequestProperty("content-type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write("a=b" + getRequestParameter(grantType));
        writer.flush();
        int responseCode = connection.getResponseCode();
        if (responseCode >= 400) {
            throw new IllegalStateException("Cannot retrieve OAuth token: " + IOUtils.toString(connection.getErrorStream()));
        }
        String response = IOUtils.toString(connection.getInputStream());
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return new Gson().fromJson(response, type);
    }

    String getRequestParameter(String grantType) throws UnsupportedEncodingException {
        Map<String, String> params = GrantType.valueOf(grantType).getParameters(world);
        Optional<String> scope = world.get(OAuthCukesConstants.SCOPE);
        scope.ifPresent(s -> params.put("scope", s));
        return params.entrySet().
            stream().
            map(e -> e.getKey() + "=" + e.getValue()).
            reduce("", (s, s2) -> s + "&" + s2);
    }
}
