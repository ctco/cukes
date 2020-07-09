package lv.ctco.cukes.rest.loadrunner.dto;

import lv.ctco.cukes.rest.loadrunner.LoadRunnerTransaction;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoadRunnerTransactionTest {

    @Test
    public void formatShouldEscapeWhitespaces() {
        LoadRunnerTransaction trx = new LoadRunnerTransaction() {{
            setName("hello world");
            setTrxFlag("LR_AUTO");
        }};
        assertThat(trx.format(), containsString("hello_world"));
    }
}
