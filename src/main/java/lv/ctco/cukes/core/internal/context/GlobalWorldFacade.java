package lv.ctco.cukes.core.internal.context;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;
import lv.ctco.cukes.core.CukesOptions;

import java.util.Set;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public class GlobalWorldFacade {

    @Inject
    GlobalWorld world;

    private RestAssuredConfig restAssuredConfig;

    @CaptureContext
    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        world.put(key, value);
    }

    public Optional<String> get(String key) {
        return world.get(key);
    }

    public String get(String key, String defaultValue) {
        Optional<String> value = world.get(key);
        return value.isPresent() ? value.get() : defaultValue;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.valueOf(get(key, Boolean.toString(defaultValue)));
    }

    public void reconstruct() {
        world.reconstruct();
        buildRestAssuredConfig();
    }

    public Set<String> getKeysStartingWith(final String headerPrefix) {
        Set<String> keys = world.keys();
        return Sets.filter(keys, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.startsWith(headerPrefix);
            }
        });
    }

    public void remove(String key) {
        world.remove(key);
    }

    public Set<String> keys() {
        return world.keys();
    }

    private RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config = newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        if (!getBoolean(CukesOptions.GZIP_SUPPORT, true)) {
            config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
        config.sslConfig(new SSLConfig().allowAllHostnames());
        return config;
    }

    public RestAssuredConfig getRestAssuredConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }
}
