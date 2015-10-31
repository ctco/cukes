package lv.ctco.cukesrest.run;

import cucumber.api.junit.*;
import lv.ctco.cukesrest.*;
import org.junit.*;
import org.junit.runner.*;

@Ignore
@RunWith(Cucumber.class)
public class RunCukesTest {

    @Before
    public void setUp() throws Exception {
        new SampleApplication().run("server", "server.yml");
    }
}
