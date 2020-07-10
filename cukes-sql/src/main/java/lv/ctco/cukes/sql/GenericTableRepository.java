package lv.ctco.cukes.sql;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lv.ctco.cukes.core.CukesRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class GenericTableRepository {

    private static final String ROW_COUNT_COLUMN = "ROW_COUNT";
    @Inject
    private ConnectionFactory connectionFactory;

    @SneakyThrows(SQLException.class)
    public List<Map<String, String>> getTableValues(String schema, String tableName, List<String> columns) {
        if (columns.isEmpty()) {
            throw new CukesRuntimeException("Can not build query from empty columns list");
        }
        String query = createSelectQuery(schema, tableName, columns);
        return tryExecuteSelectQuery(columns, query);
    }

    private String createSelectQuery(String schema, String tableName, List<String> columns) {
        String joinedColumns = columns.stream().collect(Collectors.joining(","));
        String table = constructTableName(schema, tableName);
        return String.format("SELECT %s FROM %s", joinedColumns, table);
    }

    private List<Map<String, String>> tryExecuteSelectQuery(List<String> columns, String query) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            return extractResults(columns, resultSet);
        }
    }

    private String constructTableName(String schema, String tableName) {
        return StringUtils.isEmpty(schema) ? tableName : schema + "." + tableName;
    }

    private List<Map<String, String>> extractResults(List<String> columns, ResultSet resultSet) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> row = new HashMap<>();
            for (String column : columns) {
                row.put(column, resultSet.getString(column));
            }
            results.add(row);
        }
        return results;
    }

    @SneakyThrows(SQLException.class)
    public Integer countTableValues(String schema, String tableName) {
        String query = createCountQuery(schema, tableName);
        return Integer.valueOf(tryExecuteSelectQuery(Collections.singletonList(ROW_COUNT_COLUMN), query).get(0).get(ROW_COUNT_COLUMN));
    }

    private String createCountQuery(String schema, String tableName) {
        String table = constructTableName(schema, tableName);
        return String.format("SELECT COUNT(*) as %s FROM %s", ROW_COUNT_COLUMN, table);
    }

    @SneakyThrows(SQLException.class)
    public void createTableEntities(String schema, String tableName, List<String> columnNames, List<List<String>> columnValueList) {
        for (List<String> columnValues : columnValueList) {
            String query = createInsertQuery(schema, tableName, columnNames, columnValues);
            tryExecuteInsertQuery(query);
        }
    }

    private String createInsertQuery(String schema, String tableName, List<String> columnNames, List<String> columnValues) {
        String joinedColumnNames = columnNames.stream().collect(Collectors.joining(", "));
        String joinedColumnValues = "'" + columnValues.stream().collect(Collectors.joining("', '")) + "'";
        String table = constructTableName(schema, tableName);
        return String.format("INSERT INTO %s(%s) VALUES(%s)", table, joinedColumnNames, joinedColumnValues);
    }

    private void tryExecuteInsertQuery(String query) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    @SneakyThrows(SQLException.class)
    public void createTableEntitiesBySql(String query) {
        tryExecuteInsertQuery(query);
    }
}
