package lv.ctco.cukes.rest.loadrunner.function;

public class WebRequestSaveResponseBody implements LoadRunnerFunction {

    @Override
    public String format() {
        return "web_reg_save_param(\"ResponseBody\",\"LB=\",\"RB=\",\"Search=Body\",LAST);\n";
    }
}
