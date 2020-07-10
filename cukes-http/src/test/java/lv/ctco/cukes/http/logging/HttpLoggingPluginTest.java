package lv.ctco.cukes.http.logging;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.http.RestAssuredConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static lv.ctco.cukes.core.CukesOptions.LOGGING_REQUEST_INCLUDES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.FieldSetter.setField;

@RunWith(MockitoJUnitRunner.class)
public class HttpLoggingPluginTest {
    private static final String EXPECTED_RESULT =
        "" +
            "Request method:\tGET\n" +
            "Request URI:\thttp://google.com/?q=hi\n" +
            "Proxy:\t\t\t<none>\n" +
            "Request params:\tq=hi\n" +
            "Query params:\t<none>\n" +
            "Form params:\t<none>\n" +
            "Path params:\t<none>\n" +
            "Headers:\t\tAccept=*/*\n" +
            "Cookies:\t\t<none>\n" +
            "Multiparts:\t\t<none>\n" +
            "Body:\t\t\t<none>\n" +
            "";
    private ByteArrayOutputStream testOut;
    private RestAssuredConfiguration config;

    @Mock
    private GlobalWorldFacade world;

    private HttpLoggingPlugin plugin;

    @Before
    public void setUp() throws NoSuchFieldException {
        testOut = new ByteArrayOutputStream();
        PrintStream testStream = new PrintStream(testOut);

        config = new RestAssuredConfiguration();
        setField(config, config.getClass().getDeclaredField("world"), world);

        plugin = new HttpLoggingPlugin(world, config);
        setField(plugin, plugin.getClass().getDeclaredField("logStream"), testStream);
        plugin.beforeAllTests();
    }

    @Test
    public void testOutputStream() throws UnsupportedEncodingException {
        when(world.get(LOGGING_REQUEST_INCLUDES, "")).thenReturn("all");

        RequestSpecification specification = RestAssured.given()
            .config(config.getConfig())
            .baseUri("http://google.com")
            .param("q", "hi");

        plugin.beforeRequest(specification);

        specification.get();

        String requestLog = testOut.toString("UTF-8");
        assertThat(requestLog, is(EXPECTED_RESULT));
    }
}
