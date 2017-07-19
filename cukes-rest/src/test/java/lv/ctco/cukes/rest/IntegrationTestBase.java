package lv.ctco.cukes.rest;

import cucumber.api.java.ObjectFactory;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;

public class IntegrationTestBase {

    private ObjectFactory objectFactory = SingletonObjectFactory.instance();

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }
}
