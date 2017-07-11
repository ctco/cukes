package lv.ctco.cukes.graphql.facade;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.https.TrustAllTrustManager;
import lv.ctco.cukes.graphql.internal.GraphQLRequest;

import java.net.URI;
import java.net.URISyntaxException;

@Singleton
@InflateContext
public class GQLRequestFacade {

    @Inject
    private GlobalWorldFacade world;

    private RequestSpecification specification;

    private GraphQLRequest graphQLRequest;

    @Inject
    public GQLRequestFacade(GlobalWorldFacade world) {
        this.world = world;
        initNewSpecification();
    }

    public void initNewSpecification() {
        specification = RestAssured
            .given()
            .config(world.getRestAssuredConfig());
        onCreate();
        graphQLRequest = new GraphQLRequest();
    }

    private void onCreate() {
        Optional<String> $baseUri = world.get(CukesOptions.BASE_URI);
        if ($baseUri.isPresent()) {
            baseUri($baseUri.get());
        }

        Optional<String> $proxy = world.get(CukesOptions.PROXY);
        if ($proxy.isPresent()) {
            try {
                specification.proxy(new URI($proxy.get()));
            } catch (URISyntaxException ignore) {
                throw new CukesRuntimeException("Unable to set Proxy, please check the URL");
            }
        }

        if (world.getBoolean(CukesOptions.RELAXED_HTTPS)) {
            specification.relaxedHTTPSValidation();
            TrustAllTrustManager.trustAllHttpsCertificates();
        }
    }

    public void accept(String mediaTypes) {
        specification.accept(mediaTypes);
    }

    public void baseUri(String baseUri) {
        world.put(CukesOptions.BASE_URI, baseUri);
        specification.baseUri(baseUri);
    }

    public void contentType(String contentType) {
        specification.contentType(contentType);
    }

    public void cookie(String cookieName, String cookieValue) {
        specification.cookie(cookieName, cookieValue);
    }

    public void queryBody(String body) {
        graphQLRequest.setQuery(body);
    }

    public void body(GraphQLRequest request) {
        specification.body(request);
    }

    public RequestSpecification value() {
        return specification;
    }

    public GraphQLRequest getGraphQLRequest() {
        return graphQLRequest;
    }
}
