package lv.ctco.cukes.http;

import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;
import lv.ctco.cukes.core.CukesOptions;

import static com.sun.scenario.Settings.getBoolean;
import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public class RestAssuredConfiguration {

    static RestAssuredConfig restAssuredConfig;

    public static RestAssuredConfig getConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }

    private static RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config = newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        if (!getBoolean(CukesOptions.GZIP_SUPPORT, true)) {
            config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
        config.sslConfig(new SSLConfig().allowAllHostnames());
        return config;
    }
}
