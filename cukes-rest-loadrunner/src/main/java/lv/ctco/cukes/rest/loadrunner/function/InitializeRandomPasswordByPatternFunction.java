package lv.ctco.cukes.rest.loadrunner.function;

public class InitializeRandomPasswordByPatternFunction implements LoadRunnerFunction {
    @Override
    public String format() {
        return "\n" +
            "/* Random password generators */\n" +
            "char generateRandomChar(char pattern){\n" +
            "    switch(pattern){\n" +
            "        case 'a':\n" +
            "            return (char)('a' + rand() % 26);\n" +
            "        case 'A':\n" +
            "            return (char)('A' + rand() % 26);\n" +
            "        case '0':\n" +
            "        default:\n" +
            "            return (char)('0' + rand() % 10);\n" +
            "        }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "char* generateRandomPassword(char* pattern){\n" +
            "    char* current = pattern;\n" +
            "    char* result;\n" +
            "    char* currentResult;\n" +
            "    \n" +
            "    if(!pattern)\n" +
            "        return NULL;\n" +
            "        \n" +
            "    result = (char*)malloc(strlen(pattern));\n" +
            "    currentResult = result;\n" +
            "    while(*current){\n" +
            "        *currentResult=generateRandomChar(*current);\n" +
            "        current++;\n" +
            "        currentResult++;\n" +
            "    }\n" +
            "    return result;\n" +
            "}\n" +
            "\n" +
            "char* generateRandomPasswordN(int length){\n" +
            "    char* result;\n" +
            "    char patterns[3] = {'a', 'A', '0'};\n" +
            "    int i;\n" +
            "    \n" +
            "    result = (char*)malloc(length);\n" +
            "    for(i = 0; i < length; i++)\n" +
            "        result[i] = generateRandomChar(patterns[i % 3]);\n" +
            "    \n" +
            "    return result;\n" +
            "}\n\n";
    }
}
