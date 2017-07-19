package lv.ctco.cukes.graphql.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import org.hamcrest.Matcher;

import static lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher.arrayWithSize;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.equalToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsPropertyValueByPathInArray;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath;
import static org.junit.Assert.assertThat;

@Singleton
@SwitchedBy(CukesOptions.ASSERTIONS_DISABLED)
@InflateContext
public class GQLAssertionFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    GQLResponseFacade responseFacade;

    private String getPath(String path) {
        return "data." + path;
    }

    public void responseContainsPropertyWithValue(String path, String value) {
        assertBodyValueByPath(path, equalToIgnoringType(value, this.world.getBoolean("case-insensitive")));
    }

    public void bodyContainsArrayWithSize(String path, String size) {
        assertBodyValueByPath(path, arrayWithSize(size));
    }

    public void bodyContainsArrayWithObjectHavingProperty(String path, String property, String value) {
        Response response = this.responseFacade.response();
        assertThat(response, containsPropertyValueByPathInArray(ResponseContentProvider.INSTANCE, getPath(path), property, equalToIgnoringType(value, this.world.getBoolean("case-insensitive")))
        );
    }

    private void assertBodyValueByPath(String path, Matcher matcher) {
        Response response = this.responseFacade.response();
        assertThat(response, containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), matcher));
    }
}
