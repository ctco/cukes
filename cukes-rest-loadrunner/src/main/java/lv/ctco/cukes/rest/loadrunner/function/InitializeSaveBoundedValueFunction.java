package lv.ctco.cukes.rest.loadrunner.function;

/*
 Utility C function to save part of cInStr string (bounded by cLB and cRB) into LR parameter called cPName
 */
public class InitializeSaveBoundedValueFunction implements LoadRunnerFunction {

    public String format() {
        return "char *SaveBoundedValue(const char *cPName, char *cInStr, char *cLB, char *cRB) {\n" +
            " char *cWorkPtr;\n" +
            " char *cEndPtr;\n" +
            " char cFound;\n" +
            " char cWorkName[128];\n" +
            " char *cRet;\n" +
            "\n" +
            " if (!cInStr || !cPName)    {\n" +
            "     cRet = NULL;\n" +
            " } else {\n" +
            " \tcWorkPtr = (char*)strstr(cInStr,cLB);\n" +
            "     if (!cWorkPtr) {\n" +
            "         lr_save_string(\"\",cPName);\n" +
            "         cRet = NULL;\n" +
            "     } else {\n" +
            "         cWorkPtr += strlen(cLB);\n" +
            "         cEndPtr = cWorkPtr + 1;\n" +
            "         cEndPtr = (char*)strstr(cEndPtr,cRB);\n" +
            "         if (cEndPtr) {\n" +
            "             cFound = *cEndPtr;\n" +
            "             *cEndPtr = 0;\n" +
            "             lr_save_string(cWorkPtr,cPName);\n" +
            "             *cEndPtr = cFound;\n" +
            "             sprintf(cWorkName,\"{%s}\",cPName);\n" +
            "             cRet = lr_eval_string(cWorkName);\n" +
            "         } else {\n" +
            "             lr_save_string(\"\",cPName);\n" +
            "             cRet = NULL;\n" +
            "         }\n" +
            "     }\n" +
            " }\n" +
            " return cRet;\n" +
            "} \n";
    }

}
