package ru.relz.javacore2017.service;

import ru.relz.javacore2017.database.DatabaseHelper;
import ru.relz.javacore2017.model.product.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
	private static final String TABLE_NAME = "product";
	private static Statement statement = null;

	/**
	 * Asks database for product with specified id and amount.
	 * removes product amount from database
	 * if there is such product id and enough amount.
	 *
	 * @return {@code null} if there is no product with such id or there is not enough product amount,
	 * 			otherwise product object
	 * */
	public static Product getProduct(int productId, int productAmount) {
		try {
			statement = DatabaseHelper.connection.createStatement();
			ResultSet resultSet =
					statement.executeQuery(String.format(
							"SELECT * FROM %s WHERE ID_PRODUCT = %d", TABLE_NAME, productId
					));
			Product product = createProductFromResultSet(resultSet);
			if (product == null || product.getAmount() < productAmount) {
				return null;
			}
			statement.execute(String.format("UPDATE %s SET AMOUNT = AMOUNT - %d WHERE ID_PRODUCT = %d", TABLE_NAME, productAmount, productId));
			statement.close();

			return new Product(product.getId(), product.getName(), product.getPrice(), productAmount, product.getType(), product.getBonus(), product.isForAdult());
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return null;
	}

	/**
	 * Performs the sql query to database, selecting all rows in "product" table.
	 * Prints stack trace if a database access error occurs.
	 *
	 * @return the list of available supermarket's products
	 */
	public static List<Product> getProducts() {
		try {
			List<Product> result = new ArrayList<Product>();
			statement = DatabaseHelper.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", TABLE_NAME));
			while (resultSet.next()) {
				result.add(createProductFromResultSet(resultSet));
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
	 * Restores product to database
	 * */
	public static void giveProductBack(Product product) {
		try {
			statement = DatabaseHelper.connection.createStatement();
			statement.execute(String.format("UPDATE %s SET AMOUNT = AMOUNT + %d WHERE ID_PRODUCT = %d", TABLE_NAME, product.getAmount(), product.getId()));
			statement.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	/**
	 * Parses {@code ResultSet object} for product object
	 *
	 * @return {@code product object} if resultSet has required columns,
	 * 			otherwise {@code null}
	 * */
	private static Product createProductFromResultSet(ResultSet resultSet) {
		try {
			return resultSet.next() ? new Product(resultSet) : null;
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return null;
	}
}
