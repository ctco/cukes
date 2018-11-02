package lv.ctco.cukes.http.logging;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import static io.restassured.config.RestAssuredConfig.newConfig;
import static lv.ctco.cukes.core.CukesOptions.LOGGING_REQUEST_INCLUDES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpLoggingPluginTest {

    private static final String EXPECTED_RESULT = "Request method:\tGET\n" +
        "Request URI:\thttp://google.com/?q=hi\n" +
        "Headers:\t\tAccept=*/*\n" +
        "\t\t\t\tContent-Type=application/json; charset=UTF-8\n" +
        "Request params:\tq=hi\n" +
        "Query params:\t<none>\n" +
        "Form params:\t<none>\n" +
        "Path params:\t<none>\n" +
        "Multiparts:\t\t<none>\n" +
        "Body:\n" +
        "{\n" +
        "    \"param\": \"value\"\n" +
        "}\n";

    @InjectMocks
    @Spy
    private HttpLoggingPlugin plugin;

    @Mock
    private GlobalWorldFacade facade;

    private PrintStream logStream;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        logStream = new PrintStream(out, true, "UTF-8");
    }

    @Test
    public void fixedOutputOrderOfComponents() {
        RequestSpecification request = RestAssured.given()
            .config(newConfig().logConfig(new LogConfig(logStream, true)))
            .baseUri("http://google.com")
            .body("{\"param\":\"value\"}")
            .param("q", "hi")
            .header("Content-Type", "application/json");


        String[] loggingIncludes = {
            "body,params,uri,method,headers",
            "params,uri,body,method,headers",
            "body,method,headers,params,uri,",
            "method,headers,body,params,uri"
        };
        Stream.of(loggingIncludes)
            .forEach(
                includes -> {
                    when(facade.get(LOGGING_REQUEST_INCLUDES, "")).thenReturn(includes);

                    plugin.beforeRequest(request);

                    request.get();

                    String result = null;
                    try {
                        result = out.toString("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        fail(e.toString());
                    }
                    assertThat(result, is(EXPECTED_RESULT));
                    out.reset();
                }
            );
    }
}
