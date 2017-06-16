package lv.ctco.cukes.rest.loadrunner.function;

/*
Utility C function to generate random string of specified length
 */
public class InitializeGenerateRandomStringFunction implements LoadRunnerFunction {

    @Override
    public String format() {
        return "random_Generator(char* paramname, int length) {\n" +
            " char buffer[32] = \"\";\n" +
            " int r,i;\n" +
            " char c;\n" +
            " srand((unsigned int)time(0));\n" +
            " for (i = 0; i < length; i++) {\n" +
            " r = rand() % 25 + 65;\n" +
            " c = (char)r;\n" +
            " buffer[i] = c;\n" +
            "  if (buffer[i] == buffer[i-1])\n" +
            "  {\n" +
            "  r = rand() % 25 + 65;\n" +
            "  c = (char)r;\n" +
            "  buffer[i] = c;\n" +
            "  }\n" +
            " }\n" +
            " lr_save_string(buffer, paramname);\n" +
            "return 0;\n" +
            "}\n";
    }
}
