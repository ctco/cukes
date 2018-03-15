package lv.ctco.cukes.sql;

import java.sql.Connection;

public interface ConnectionFactory {
	Connection getConnection();
}
