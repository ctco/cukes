package lv.ctco.cukes.rest.loadrunner.function;

public class WebRequestSaveParam implements LoadRunnerFunction {
    @Override
    public String format() {
        return "web_reg_save_param(\"httpcode\",\n " +
            "\"LB=HTTP/1.1 \",\n " +
            "\"RB= \",\n " +
            "\"Ord=1\",\n " +
            "LAST);\n";
    }
}
