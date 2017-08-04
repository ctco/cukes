Feature: Calculate
  Background:
    Given content type is "text/xml"
    And accept "text/xml" mediaTypes

  Scenario: Should sum 2 numbers
    Given request body:
    """
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cuk="http://github.com/ctco/cukes/cukes-soap-sample">
       <soapenv:Header/>
       <soapenv:Body>
          <cuk:calculatorRequest>
             <a>1</a>
             <b>2</b>
          </cuk:calculatorRequest>
       </soapenv:Body>
    </soapenv:Envelope>
    """
    When the client performs POST request on "/ws"
    Then status code is 200
    And response contains property "Envelope.Body.calculatorResponse.result" with value "3"
