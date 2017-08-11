package lv.ctco.cukes.docgen;

import java.util.Comparator;

public class StepDefinition {

    public static final Comparator<StepDefinition> comparator = Comparator.comparing(StepDefinition::getPatten);

    private String patten;
    private String description;

    public StepDefinition(String patten, String description) {
        this.patten = patten;
        this.description = description;
    }

    public String getPatten() {
        return patten;
    }

    public String getDescription() {
        return description;
    }

}
