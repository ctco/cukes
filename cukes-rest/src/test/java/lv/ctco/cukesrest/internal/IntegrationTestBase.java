package lv.ctco.cukesrest.internal;

import cucumber.api.java.ObjectFactory;
import lv.ctco.cukesrest.di.SingletonObjectFactory;

public class IntegrationTestBase {

    private ObjectFactory objectFactory = SingletonObjectFactory.instance();

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }
}
