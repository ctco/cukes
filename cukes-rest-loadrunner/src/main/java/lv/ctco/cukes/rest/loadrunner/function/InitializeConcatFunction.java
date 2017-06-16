package lv.ctco.cukes.rest.loadrunner.function;

/*
Utility C function that is used to concat 2 strings
 */
public class InitializeConcatFunction implements LoadRunnerFunction {

    public String format() {
        return "char* concat(const char *s1, const char *s2)\n" +
            "{\n" +
            "\tchar *result = (char *) malloc(strlen(s1)+strlen(s2)+1);\n" +
            "    strcpy(result, s1);\n" +
            "    strcat(result, s2);\n" +
            "    return result;\n" +
            "}\n";
    }

}
