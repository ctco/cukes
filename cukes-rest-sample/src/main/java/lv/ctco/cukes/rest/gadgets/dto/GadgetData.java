package lv.ctco.cukes.rest.gadgets.dto;

import java.util.*;

public class GadgetData {

    public GadgetData(Collection<GadgetDto> gadgets) {
        this.gadgets = gadgets;
    }

    private Collection<GadgetDto> gadgets;

    public Collection<GadgetDto> getGadgets() {
        return gadgets;
    }

    public void setGadgets(Collection<GadgetDto> gadgets) {
        this.gadgets = gadgets;
    }
}
