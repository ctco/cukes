package lv.ctco.cukes.core.internal.helpers;

public class Strings {

    public static String escapeRegex(String regex) {
        return regex.replace("{", "\\{").replace("}", "\\}");
    }
}
