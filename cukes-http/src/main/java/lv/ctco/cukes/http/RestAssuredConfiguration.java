package lv.ctco.cukes.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

@Singleton
public class RestAssuredConfiguration {

    @Inject
    GlobalWorldFacade world;

    private RestAssuredConfig restAssuredConfig;

    public RestAssuredConfig getConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }

    private RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config = newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        if (!world.getBoolean(CukesOptions.GZIP_SUPPORT, true)) {
            config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
        config.sslConfig(new SSLConfig().allowAllHostnames());
        return config;
    }
}
