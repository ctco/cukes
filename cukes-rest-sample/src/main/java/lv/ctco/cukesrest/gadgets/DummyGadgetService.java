package lv.ctco.cukesrest.gadgets;

import lv.ctco.cukesrest.common.InMemoryStorage;
import lv.ctco.cukesrest.gadgets.dto.GadgetDto;
import lv.ctco.cukesrest.gadgets.dto.GadgetType;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class DummyGadgetService {

    @Inject
    InMemoryStorage storage;

    public Collection<GadgetDto> searchGadgets() {
        return storage.getGadgets().values();
    }

    public GadgetDto getGadget(Integer id) {
        return storage.getGadgets().get(id);
    }

    public Integer addGadget(GadgetDto gadget) {
        if (gadget == null || gadget.getType() == GadgetType.BOOK_READER) {
            return null;
        }

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
        return storage.getGadgets().remove(id) == null; //TODO check
    }
}
