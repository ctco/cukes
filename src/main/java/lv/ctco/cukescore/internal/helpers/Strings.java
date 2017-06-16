package lv.ctco.cukescore.internal.helpers;

public class Strings {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String escapeRegex(String regex) {
        return regex.replace("{", "\\{").replace("}", "\\}");
    }
}
