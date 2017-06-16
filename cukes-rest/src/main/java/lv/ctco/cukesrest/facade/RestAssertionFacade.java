package lv.ctco.cukesrest.facade;

public interface RestAssertionFacade {

    void bodyEqualTo(String body);

    void bodyNotEqualTo(String body);

    void bodyNotEmpty();

    void bodyContains(String body);

    void bodyDoesNotContain(String body);

    void headerIsEmpty(String headerName);

    void headerIsNotEmpty(String headerName);

    void statusCodeIs(int statusCode);

    void statusCodeIsNot(int statusCode);

    void headerEndsWith(String headerName, String suffix);

    void varAssignedFromHeader(String varName, String headerName);

    void headerEqualTo(String headerName, String value);

    void headerNotEqualTo(String headerName, String value);

    void headerContains(String headerName, String text);

    void headerDoesNotContain(String headerName, String text);

    void bodyContainsPropertiesFromJson(String json);

    void bodyContainsPathWithValue(String path, String value);

    void bodyContainsPathWithOtherValue(String path, String value);

    void bodyDoesNotContainPath(String path);

    void bodyContainsArrayWithSize(String path, String size);

    void bodyContainsArrayWithEntryHavingValue(String path, String value);

    void bodyContainsPathOfType(String path, String type);

    void bodyContainsPathMatchingPattern(String path, String pattern);

    void bodyContainsPathNotMatchingPattern(String path, String pattern);

    void varAssignedFromProperty(String varName, String property);

    void varAssignedFromBody(String varName);

    void bodyContainsJsonPathValueContainingPhrase(String jsonPath, String phrase);

    void failureOccurs(String exceptionClass);

    void failureIsExpected();
}
