package lv.ctco.cukes.rest.gadgets;

import lv.ctco.cukes.rest.common.InMemoryStorage;
import lv.ctco.cukes.rest.gadgets.dto.GadgetDto;
import lv.ctco.cukes.rest.gadgets.dto.GadgetType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DummyGadgetService {

    @Inject
    InMemoryStorage storage;

    public Collection<GadgetDto> searchGadgets(Integer top, Integer skip) {
        Iterator<GadgetDto> iterator = storage.getGadgets().values().iterator();
        if (skip != null) {
            for (int i = 0; i < skip; i++) iterator.next();
        }
        List<GadgetDto> result = new ArrayList<GadgetDto>();
        for (int i = 0; (top == null || i < top) && iterator.hasNext(); i++) {
            result.add(iterator.next());
        }
        return result;
    }

    public GadgetDto getGadget(Integer id) {
        return storage.getGadgets().get(id);
    }

    public Integer addGadget(GadgetDto gadget) {
        if (gadget == null || !isValidType(gadget)) return null;

        Set<Integer> gadgetIds = storage.getGadgets().keySet();
        Integer newId = Collections.max(gadgetIds) + 1;
        gadget.setId(newId);
        gadget.setUpdatedDate(null);
        gadget.setCreatedDate(new Date());

        storage.getGadgets().put(newId, gadget);
        return newId;
    }

    public boolean updateGadget(Integer id, GadgetDto updated) {
        GadgetDto gadget = storage.getGadgets().get(id);
        if (gadget == null || updated == null || !isValidType(updated)) return false;

        gadget.setName(updated.getName());
        gadget.setType(updated.getType());
        gadget.setOwner(updated.getOwner());
        gadget.setUpdatedDate(new Date());
        return true;
    }

    public boolean removeGadget(Integer id) {
        Map<Integer, GadgetDto> gadgets = storage.getGadgets();
        GadgetDto gadget = gadgets.get(id);
        if (gadget != null) {
            gadgets.remove(id);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidType(GadgetDto gadget) {
        GadgetType type = gadget.getType();
        return type != null && type != GadgetType.BOOK_READER;
    }
}
