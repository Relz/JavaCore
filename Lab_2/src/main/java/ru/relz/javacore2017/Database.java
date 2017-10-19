package main.java.ru.relz.javacore2017;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

final class Database {
	private static final String tableName = "product";
	private static Connection connection = null;
	private static Statement statement = null;

	/**
	 * Loads apache derby embedded driver and initializes connection to database.
	 * Prints stack trace if a database access error occurs or the url is {@code null}
	 */
	static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:D:/workspace/Java/Lab_2/product;create=true");
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	/**
	 * Performs the sql query to database, selecting all rows in "product" table.
	 * Prints stack trace if a database access error occurs.
	 *
	 * @return the list of available supermarket's products
	 */
	static List<Product> getProducts() {
		try {
			List<Product> result = new ArrayList<>();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
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
	 * Asks database for product with specified id and amount.
	 * removes product amount from database
	 * if there is such product id and enough amount.
	 *
	 * @return {@code null} if there is no product with such id or there is not enough product amount,
	 * 			otherwise product object
	 * */
	static Product getProduct(int productId, int productAmount) {
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + productId);
			Product product = getProductFromResultSet(resultSet);
			if (product == null || product.getAmount() < productAmount) {
				return null;
			}
			statement.execute("UPDATE " + tableName + " SET AMOUNT = AMOUNT - " + productAmount + " WHERE ID = " + productId);
			statement.close();

			return new Product(product.getId(), product.getName(), product.getPrice(), productAmount, product.getType(), product.getBonus());
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return null;
	}

	/**
	 * Parses {@code ResultSet object} for product object
	 *
	 * @return {@code Product object} if resultSet has required columns,
	 * 			otherwise {@code null}
	 * */
	private static Product getProductFromResultSet(ResultSet resultSet) {
		try {
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				double price = resultSet.getDouble(3);
				int amount = resultSet.getInt(4);
				ProductType productType = ProductType.toProductType(resultSet.getInt(5));
				int bonus = resultSet.getInt(6);

				return new Product(id, name, price, amount, productType, bonus);
			}

			return null;
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return null;
	}
}
