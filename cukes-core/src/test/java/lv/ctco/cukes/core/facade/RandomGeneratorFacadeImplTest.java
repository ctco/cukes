package lv.ctco.cukes.core.facade;

import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.matchers.ContainsPattern;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RandomGeneratorFacadeImplTest {
    private RandomGeneratorFacadeImpl generator = new RandomGeneratorFacadeImpl();

    @Test(expected = CukesRuntimeException.class)
    public void byInvalidPattern() throws Exception {
        generator.byPattern("b");
    }

    @Test
    public void byPattern1() throws Exception {
        assertThat(generator.byPattern("A"), ContainsPattern.matchesPattern("[A-Z]"));
        assertThat(generator.byPattern("a"), ContainsPattern.matchesPattern("[a-z]"));
        assertThat(generator.byPattern("0"), ContainsPattern.matchesPattern("[0-9]"));

        assertThat(generator.byPattern("0Aa"), ContainsPattern.matchesPattern(Pattern.compile("[0-9][A-Z][a-z]")));
    }

    @Test
    public void withLength() throws Exception {
        assertThat(generator.withLength(5), ContainsPattern.matchesPattern(Pattern.compile("[A-Za-z0-9]{5}")));
    }
}
