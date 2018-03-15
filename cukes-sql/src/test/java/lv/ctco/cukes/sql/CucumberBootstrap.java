package lv.ctco.cukes.sql;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import lv.ctco.cukes.core.extension.CukesPlugin;

import java.sql.SQLException;

@Slf4j
@Singleton
public class CucumberBootstrap implements CukesPlugin {

    @Inject
    private H2MemoryDatabase database;

    @Override
    public void beforeAllTests() {
        database.openConnection();
    }

    @Override
    public void afterAllTests() {
        try {
            database.closeConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void beforeScenario() {
        try {
            database.clearDataBase();
            database.createAndFillTable();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterScenario() {}
}
