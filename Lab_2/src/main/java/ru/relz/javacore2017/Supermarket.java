package main.java.ru.relz.javacore2017;

import java.sql.*;
import java.util.*;

class Supermarket {
	private static final String tableName = "product";
	private static Connection connection = null;
	private static Statement statement = null;

	Supermarket() {
		createConnection();
	}

	private int _workingTime;
	int getWorkingTime() {
		return _workingTime;
	}

	void setWorkingTime(int workingTime) {
		_workingTime = workingTime;
	}

	/**
	 * Performs the sql query to database, selecting all rows in "product" table.
	 * Prints stack trace if a database access error occurs.
	 *
	 * @return the list of available supermarket's products
	 */
	List<Product> getProducts() {
		try {
			List<Product> result = new ArrayList<>();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + tableName);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				double price = resultSet.getDouble(3);
				int amount = resultSet.getInt(4);
				ProductType productType = ProductType.toProductType(resultSet.getInt(5));
				int bonus = resultSet.getInt(6);
				result.add(new Product(id, name, price, amount, productType, bonus));
			}
			resultSet.close();
			statement.close();

			return result;
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return null;
	}

	/**
	 * Performs supermarket work cycle until end of working time.
	 * Call {@code onEachUnitOfTime} method each unit of time.
	 * Call {@code onFinished} method on working time ends.
	 */
	void work(SupermarketWorkInterface supermarketWorkInterface) {
		supermarketWorkInterface.onEachUnitOfTime();
		supermarketWorkInterface.onFinished();
	}

	/**
	 * Loads apache derby embedded driver and initializes connection to database.
	 * Prints stack trace if a database access error occurs or the url is {@code null}
	 */
	private void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:D:/workspace/Java/Lab_2/product;create=true");
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
}
