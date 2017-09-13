package lv.ctco.cukes.http.templating;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.templating.TemplatingEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TemplatingEngineTest {

    @Mock
    private GlobalWorldFacade world;

    @InjectMocks
    private lv.ctco.cukes.core.internal.templating.TemplatingEngine TemplatingEngine = new TemplatingEngine();

    @Before
    public void setUp() throws Exception {
        Mockito.when(world.getBoolean(CukesOptions.REQUEST_BODY_TEMPLATES_ENABLED)).thenReturn(true);
        Mockito.when(world.keys()).thenReturn(Sets.newHashSet("contractName", "profitCentre"));
        Mockito.when(world.get("contractName")).thenReturn(Optional.of("test1"));
        Mockito.when(world.get("profitCentre")).thenReturn(Optional.of("24342"));
    }

    @Test
    public void testBody() {
        String body = "{\n" +
            " \"business\": {\n" +
            " \"businessDirection\": 1006415,\n" +
            " \"transactionType\": 101759,\n" +
            " \"businessSegment\": 1022645\n" +
            " },\n" +
            " \"contractName\": \"@contractName\",\n" +
            " \"underwritingYear\": 2015,\n" +
            " \"businessAndParticipationType\": 1001011,\n" +
            " \"agreementType\": \"@agreementType\",\n" +
            " \"fasClassification\": \"@fasClassification\",\n" +
            " \"accountingBasis\": 100003,\n" +
            " \"underwritingObjectStatus\": 1003797,\n" +
            " \"inceptionDate\": \"2015-01-01T00:00:00.000+0000\",\n" +
            " \"expirationDate\": \"2015-12-31T00:00:00.000+0000\",\n" +
            " \"contractCurrency\": \"EUR\",\n" +
            " \"profitCentre\": @profitCentre,\n" +
            " \"involvedParties\": [\n" +
            " {\n" +
            " \"partnerId\": \"@partnerId_1\",\n" +
            " \"partnerRole\": @partnerRole\n" +
            " },\n" +
            " {\n" +
            " \"partnerId\": @partnerId_2,\n" +
            " \"partnerRole\": 2173\n" +
            " }\n" +
            " ]\n" +
            "}";

        String processBody = TemplatingEngine.processBody(body);

        assertTrue(processBody.contains("\"contractName\": \"test1\""));
        assertTrue(processBody.contains("\"profitCentre\": 24342"));
    }
}
