package lv.ctco.cukes.ldap.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.ldap.internal.EntityService;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ModificationFacade {

    private static final Map<String, Integer> OP_MAPPING = new HashMap<>();
    static {
        OP_MAPPING.put("add", DirContext.ADD_ATTRIBUTE);
        OP_MAPPING.put("remove", DirContext.REMOVE_ATTRIBUTE);
        OP_MAPPING.put("replace", DirContext.REPLACE_ATTRIBUTE);
    }

    @Inject
    EntityService entityService;

    private List<ModificationItem> modifications = new ArrayList<>();

    public void reset() {
        modifications.clear();
    }

    public void add(String attribute, String operation, String value) {
        Integer op = OP_MAPPING.get(operation);
        if (op == null) {
            throw new CukesRuntimeException("Unknown operation: " + operation);
        }
        modifications.add(new ModificationItem(op, new BasicAttribute(attribute, value)));
    }

    public void execute(String dn) {
        entityService.modifyByDn(dn, modifications);
        reset();
    }
}
