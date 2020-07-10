package lv.ctco.cukes.rest.gadgets.dto;

import java.util.Collection;

public class GadgetData {

    private Collection<GadgetDto> gadgets;

    public GadgetData(Collection<GadgetDto> gadgets) {
        this.gadgets = gadgets;
    }

    public Collection<GadgetDto> getGadgets() {
        return gadgets;
    }

    public void setGadgets(Collection<GadgetDto> gadgets) {
        this.gadgets = gadgets;
    }
}
