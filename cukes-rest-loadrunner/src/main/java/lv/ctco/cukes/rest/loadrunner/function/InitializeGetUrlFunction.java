package lv.ctco.cukes.rest.loadrunner.function;

/*
 Utility C function to extract last part of the string that starts with http:// or https://.
 Imagine the following case:
 Scenario:
    Given baseUri is "http://localhost:8080"
    When the client performs POST request on "/resource"
    Then let variable "resourceUri" equal to header "Location" value
    When the client performs GET request on "{(resourceUri)}"

 Depending on server implementation value of resourceUri can be either of these:
 - http://localhost:8080/resource/12345
 - /resource/12345

 However cukes-rest (together with RestAssured) with consider that GET request should be done to http://localhost:8080/{(resourceUri)}
 that might result in http://localhost:8080/http://localhost:8080/resource/12345 that is obviously wrong
 */
public class InitializeGetUrlFunction implements LoadRunnerFunction {

    public String format() {
        return "char *getUrl(char *in) {\n" +
            "\tint start;\n" +
            "\tint suffixLen = 0;\n" +
            "\tchar *value = lr_eval_string(in);\n" +
            "\tfor (start = ((int) value) + strlen(value) - 1; start >= ((int) value); start--, suffixLen++) {\n" +
            "\t\tif (suffixLen >= 7 && strncmp((char *) start, \"http://\", 7) == 0) {\n" +
            "\t    \treturn (char *)start;\n" +
            "\t\t} else if (suffixLen >= 8 && strncmp((char *)start, \"https://\", 8) == 0) {\n" +
            "       \treturn (char *)start;\n" +
            "       }\n" +
            "\t}\n" +
            "\t\n" +
            "\treturn value;\n" +
            "}\n";
    }

}
