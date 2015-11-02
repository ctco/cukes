package lv.ctco.cukesrest.internal.switches;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SwitchedBy {
    String value();
}
