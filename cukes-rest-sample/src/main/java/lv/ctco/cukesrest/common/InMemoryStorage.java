package lv.ctco.cukesrest.common;

import static lv.ctco.cukesrest.gadgets.dto.GadgetType.LAPTOP;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.SMARTPHONE;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.SMART_WATCH;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.TABLET;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import lv.ctco.cukesrest.gadgets.dto.GadgetDto;
import lv.ctco.cukesrest.gadgets.dto.GadgetType;
import lv.ctco.cukesrest.gadgets.dto.Owner;

@Singleton
public class InMemoryStorage {

    private Map<Integer, GadgetDto> gadgets = new HashMap<Integer, GadgetDto>();

    @Inject
    public void init() throws Exception {
        initGadget(1857, SMARTPHONE, "LG Nexus 5", "Lisa", 8);
        initGadget(1858, LAPTOP, "Macbook Air", "Homer", 38);
        initGadget(1859, LAPTOP, "Alienware 17 R3", "Bart", 10);
        initGadget(1860, SMART_WATCH, "Apple Watch", "Marge", 36);
        initGadget(1861, TABLET, "Samsung Galaxy Tab 2", "Maggie", 2);
    }

    public Map<Integer, GadgetDto> getGadgets() {
        return this.gadgets;
    }

    private void initGadget(Integer id, GadgetType type, String gadgetName, String name, Integer age) {
        GadgetDto gadget = new GadgetDto();

        gadget.setId(id);
        gadget.setType(type);
        gadget.setName(gadgetName);
        gadget.setOwner(new Owner(name, "Simpson", age, Arrays.asList("Mr.Burns' slave")));
        gadget.setCreatedDate(new Date());

        this.gadgets.put(id, gadget);
    }
}
