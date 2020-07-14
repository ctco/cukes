package lv.ctco.cukes.sql;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class H2MemoryDatabase implements ConnectionFactory {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_dbConnection = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    public Connection dbConnection;

    @Inject
    private GenericTableRepository repository;

    @Override
    public Connection getConnection() {
        return startNewConnection();
    }

    public static Connection startNewConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_dbConnection, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return dbConnection;
    }

    public void openConnection() {
        dbConnection = startNewConnection();
    }

    public void clearDataBase() throws SQLException {
        dbConnection.setAutoCommit(false);
        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS PERSON");
            dbConnection.commit();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            closeConnection();
            throw e;
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.close();
    }

    public void createAndFillTable() throws SQLException {
//        Statement stmt;
        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255), surname VARCHAR(255), age INT)");
            stmt.execute("INSERT INTO PERSON(id, name, surname, age) VALUES(1, 'Anju', 'Ujna', 5)");
            stmt.execute("INSERT INTO PERSON(id, name, surname, age) VALUES(2, 'Sonia', 'Ainos', 19)");
            stmt.execute("INSERT INTO PERSON(id, name, surname, age) VALUES(3, 'Asha', 'Tear', 35)");

            log.info("H2 In-Memory Database inserted through Statement");
            dbConnection.commit();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            closeConnection();
            throw e;
        }
    }
}
