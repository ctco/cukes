package lv.ctco.cukes.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.io.PrintStream;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

@Singleton
public class RestAssuredConfiguration {

    @Inject
    GlobalWorldFacade world;

    private RestAssuredConfig restAssuredConfig;
    private PrintStream logStream;

    public RestAssuredConfig getConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }

    private RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config = newConfig()
            .jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
            .redirect(redirectConfig().followRedirects(world.getBoolean(CukesOptions.FOLLOW_REDIRECTS, true)));
        if(logStream != null)
            config = config.logConfig(logConfig().defaultStream(logStream));
        if (!world.getBoolean(CukesOptions.GZIP_SUPPORT, true)) {
            config = config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
        config = config.sslConfig(new SSLConfig().allowAllHostnames());
        return config;
    }

    public void setDefaultStream(PrintStream logStream) {
        this.logStream = logStream;
    }

    public void reset() {
        restAssuredConfig = null;
    }
}
