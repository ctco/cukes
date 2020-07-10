package lv.ctco.cukes.sql.utils;

import org.assertj.core.api.AbstractIntegerAssert;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TableAssertUtils {

    private TableAssertUtils() {
    }

    public static void assertEqualsTableValues(Collection<Map<String, String>> actualValues, Collection<Map<String, String>> expectedValues) {
        assertThat(actualValues).as("Values from the table should exactly match expected values")
            .containsExactlyInAnyOrder(toArray(expectedValues));
    }

    private static Map<String, String>[] toArray(Collection<Map<String, String>> values) {
        return (Map<String, String>[]) values.toArray(new Map[values.size()]);
    }

    public static void assertContainsTableValues(Collection<Map<String, String>> actualValues, Collection<Map<String, String>> expectedValues) {
        assertThat(actualValues).as("Values from the table should contain expected values")
            .contains(toArray(expectedValues));
    }

    public static void assertValueRelatesToValue(Integer actual, String sign, Integer number) {
        assertWithSign(assertThat(actual).as("Table row count should be %s %s", sign, number), sign, number);
    }

    private static void assertWithSign(AbstractIntegerAssert<?> anAssert, String sign, Integer number) {
        switch (sign) {
            case "<":
                anAssert.isLessThan(number);
                break;
            case "<=":
                anAssert.isLessThanOrEqualTo(number);
                break;
            case "!=":
            case "<>":
                anAssert.isNotEqualTo(number);
                break;
            case ">=":
                anAssert.isGreaterThanOrEqualTo(number);
                break;
            case ">":
                anAssert.isGreaterThan(number);
                break;
            case "=":
                anAssert.isEqualTo(number);
                break;
        }
    }
}
