package ru.relz.javacore2017.database;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DatabaseHelper {
	public static Connection connection = null;

	/**
	 * Loads apache derby embedded driver and initializes connection to database.
	 * Prints stack trace if a database access error occurs or the url is {@code null}
	 */
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection(
					"jdbc:derby:/data/workspace/Java/Lab_2/SupermarketSimulator/supermarket;create=false"
			);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
}
