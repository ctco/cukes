package lv.ctco.cukes.soap.sample;

import lv.ctco.cukes.soap.sample.dto.CalculatorRequest;
import lv.ctco.cukes.soap.sample.dto.CalculatorResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CalculatorEndpoint {

    @PayloadRoot(namespace = "http://github.com/ctco/cukes/cukes-soap-sample", localPart = "calculatorRequest")
    @ResponsePayload
    public CalculatorResponse calculate(@RequestPayload CalculatorRequest request) {
        CalculatorResponse response = new CalculatorResponse();
        response.setResult(request.getA() + request.getB());
        return response;
    }
}
