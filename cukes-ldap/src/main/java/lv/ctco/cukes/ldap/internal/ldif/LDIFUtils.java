package lv.ctco.cukes.ldap.internal.ldif;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class LDIFUtils {

    public static Map<String, Attributes> read(InputStream in) throws IOException {
        Map<String, Attributes> result = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            Attributes currentEntity = null;
            String currentLine = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.isEmpty()) {
                    if (currentLine != null) {
                        storeAttribute(result, currentEntity, currentLine);
                    }
                    //next entity
                    currentEntity = null;
                    currentLine = null;
                    continue;
                }
                if (currentEntity == null) {
                    currentEntity = new BasicAttributes();
                }
                if (!line.startsWith(" ")) {
                    if (currentLine != null) {
                        storeAttribute(result, currentEntity, currentLine);
                    }
                    currentLine = line;
                } else {
                    currentLine = currentLine + line;
                }
            }
            if (currentLine != null) {
                storeAttribute(result, currentEntity, currentLine);
            }
        }
        return result;
    }

    private static void storeAttribute(Map<String, Attributes> result, Attributes currentEntity, String line) {
        String[] split = line.split(":");
        String attrName = split[0].trim();
        String attrValue = split[1].trim();
        if (attrName.equalsIgnoreCase("dn")) {
            result.put(attrValue, currentEntity);
        } else {
            Attribute attribute = currentEntity.get(attrName);
            if (attribute == null) {
                attribute = new BasicAttribute(attrName);
                currentEntity.put(attribute);
            }
            attribute.add(attrValue);
        }
    }

}
