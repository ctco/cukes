package lv.ctco.cukesrest.loadrunner.mapper;

import com.jayway.restassured.response.Header;
import lv.ctco.cukesrest.loadrunner.function.WebAddHeader;

public class WebAddHeaderMapper {

    public WebAddHeader map(Header header) {
        WebAddHeader result = new WebAddHeader();
        result.setName(header.getName());
        result.setValue(header.getValue());

        return result;
    }
}
