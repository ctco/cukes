package lv.ctco.cukes.rest.common;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.rest.gadgets.dto.GadgetDto;
import lv.ctco.cukes.rest.gadgets.dto.GadgetType;
import lv.ctco.cukes.rest.gadgets.dto.Owner;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryStorage {

    private Map<Integer, GadgetDto> gadgets = new HashMap<Integer, GadgetDto>();

    @Inject
    public void init() throws Exception {
        initGadget(1857, GadgetType.SMARTPHONE, "LG Nexus 5", "Lisa", 8);
        initGadget(1858, GadgetType.LAPTOP, "Macbook Air", "Homer", 38);
        initGadget(1859, GadgetType.LAPTOP, "Alienware 17 R3", "Bart", 10);
        initGadget(1860, GadgetType.SMART_WATCH, "Apple Watch", "Marge", 36);
        initGadget(1861, GadgetType.TABLET, "Samsung Galaxy Tab 2", "Maggie", 2);
    }

    public Map<Integer, GadgetDto> getGadgets() {
        return this.gadgets;
    }

    private void initGadget(Integer id, GadgetType type, String gadgetName, String name, Integer age) {
        GadgetDto gadget = new GadgetDto();

        gadget.setId(id);
        gadget.setType(type);
        gadget.setName(gadgetName);
        gadget.setOwner(new Owner(name, "Simpson", age, Collections.singletonList("Mr.Burns' slave")));
        gadget.setCreatedDate(new Date());

        this.gadgets.put(id, gadget);
    }
}
