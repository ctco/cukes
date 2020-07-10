package lv.ctco.cukes.sql.facade;

import com.google.inject.Inject;
import lv.ctco.cukes.sql.GenericTableRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static lv.ctco.cukes.sql.utils.TableAssertUtils.*;

public class DataBaseRequestFacade {

    @Inject
    private GenericTableRepository genericTableRepository;

    public void checkSchemeTableContains(String scheme, String tableName, List<Map<String, String>> tableValues) {
        List<Map<String, String>> values = genericTableRepository.getTableValues(scheme, tableName, extractColumnNames(tableValues));
        assertContainsTableValues(values, tableValues);
    }

    private List<String> extractColumnNames(List<Map<String, String>> tableValues) {
        if (tableValues.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(tableValues.get(0).keySet()));
    }

    public void checkSchemeTableMatch(String scheme, String tableName, List<Map<String, String>> tableValues) {
        List<Map<String, String>> values = genericTableRepository.getTableValues(scheme, tableName, extractColumnNames(tableValues));
        assertEqualsTableValues(values, tableValues);
    }

    public void checkSchemeTableCount(String scheme, String tableName, String sign, Integer number) {
        Integer value = genericTableRepository.countTableValues(scheme, tableName);
        assertValueRelatesToValue(value, sign, number);
    }

    public void createEntities(String scheme, String tableName, List<Map<String, String>> tableValues) {
        if (!tableValues.isEmpty()) {
            List<String> columnNames = extractColumnNames(tableValues);
            List<List<String>> columnValues = extractColumnValues(tableValues);
            genericTableRepository.createTableEntities(scheme, tableName, columnNames, columnValues);
        }
    }

    private List<List<String>> extractColumnValues(List<Map<String, String>> tableValues) {
        List<List<String>> columnValues = new ArrayList<>();
        for (Map<String, String> map : tableValues) {
            columnValues.add(new ArrayList<>(map.values()));
        }
        return columnValues;
    }

    public void createEntitiesBySql(String query) {
        genericTableRepository.createTableEntitiesBySql(query);
    }
}
