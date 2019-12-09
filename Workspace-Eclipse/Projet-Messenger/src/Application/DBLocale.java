package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBLocale {
	//https://www.tutorialspoint.com/sqlite/sqlite_java.htm
	//sqlite java
	protected Connection connectionDB(String nomDB) {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+nomDB);
		} catch (ClassNotFoundException e) {
			System.out.println("DBLocale: Error connectionDB, class not Found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error connectionDB, connection failed to db");
			e.printStackTrace();
		}
		return c;
	}
	

}
