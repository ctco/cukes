package lv.ctco.cukesrest.gadgets;

import lv.ctco.cukesrest.gadgets.dto.GadgetDto;
import lv.ctco.cukesrest.gadgets.dto.GadgetType;
import lv.ctco.cukesrest.gadgets.dto.Owner;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static lv.ctco.cukesrest.gadgets.dto.GadgetType.BOOK_READER;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.LAPTOP;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.SMARTPHONE;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.SMART_WATCH;
import static lv.ctco.cukesrest.gadgets.dto.GadgetType.TABLET;

public class DummyGadgetService {

    private Map<Integer, GadgetDto> gadgets = new HashMap<Integer, GadgetDto>();

    public void init() {
        initGadget(1857, SMARTPHONE, "LG Nexus 5", "Lisa", 8);
        initGadget(1858, LAPTOP, "Macbook Air", "Homer", 38);
        initGadget(1859, LAPTOP, "Alienware 17 R3", "Bart", 10);
        initGadget(1860, SMART_WATCH, "Apple Watch", "Marge", 36);
        initGadget(1861, TABLET, "Samsung Galaxy Tab 2", "Maggie", 2);
    }

    public Collection<GadgetDto> searchGadgets() {
        return gadgets.values();
    }

    public GadgetDto getGadget(Integer id) {
        return gadgets.get(id);
    }

    public Integer addGadget(GadgetDto gadget) {
        if (gadget == null || gadget.getType() == BOOK_READER) {
            return null;
        }

        Set<Integer> gadgetIds = gadgets.keySet();
        Integer newId = Collections.max(gadgetIds) + 1;
        gadget.setId(newId);
        gadget.setUpdatedDate(null);
        gadget.setCreatedDate(new Date());

        gadgets.put(newId, gadget);
        return newId;
    }

    public boolean updateGadget(Integer id, GadgetDto updated) {
        GadgetDto gadget = gadgets.get(id);
        if (gadget == null) {
            return false;
        }
        gadget.setName(updated.getName());
        gadget.setType(updated.getType());
        gadget.setOwner(updated.getOwner());
        gadget.setUpdatedDate(new Date());
        return true;
    }

    public boolean removeGadget(Integer id) {
        return gadgets.remove(id) == null; //TODO check
    }

    private void initGadget(Integer id, GadgetType type, String gadgetName, String name, Integer age) {
        GadgetDto gadget = new GadgetDto();

        gadget.setId(id);
        gadget.setType(type);
        gadget.setName(gadgetName);
        gadget.setOwner(new Owner(name, "Simpson", age));
        gadget.setCreatedDate(new Date());

        gadgets.put(id, gadget);
    }
}
