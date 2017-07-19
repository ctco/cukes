package lv.ctco.cukes.rest.loadrunner.function;

public class WebAddHeader implements LoadRunnerFunction {

    private String name;
    private String value;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String format() {
        return "web_add_header(\"" + name + "\", \"" + value + "\");\n";
    }
}
