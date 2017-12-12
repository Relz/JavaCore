package ru.relz.javacore2017.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHelper {
	public static Connection connection = null;
	private static String className = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String databasePath =
			"jdbc:derby:/data/workspace/Java/Lab_2/SupermarketSimulator/supermarket;create=false";
	/**
	 * Loads apache derby embedded driver and initializes connection to database.
	 */
	public static void createConnection() throws ClassNotFoundException, SQLException {
		if (connection == null || connection.isClosed()) {
			Class.forName(DatabaseHelper.className);
			connection = DriverManager.getConnection(DatabaseHelper.databasePath);
		}
	}

	public static void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
}
