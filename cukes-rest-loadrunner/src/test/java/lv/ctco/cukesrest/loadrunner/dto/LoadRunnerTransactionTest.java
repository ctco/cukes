package lv.ctco.cukesrest.loadrunner.dto;

import lv.ctco.cukesrest.loadrunner.LoadRunnerTransaction;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class LoadRunnerTransactionTest {

    @Test
    public void formatShouldEscapeWhitespaces() throws Exception {
        LoadRunnerTransaction trx = new LoadRunnerTransaction() {{
            setName("hello world");
            setTrxFlag("LR_AUTO");
        }};
        assertThat(trx.format(), containsString("hello_world"));
    }
}