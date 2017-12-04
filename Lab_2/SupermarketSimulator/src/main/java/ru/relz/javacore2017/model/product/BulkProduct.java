package ru.relz.javacore2017.model.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class BulkProduct extends Product {
	public BulkProduct(int id, String name, double price, int amount, double bonus, boolean forAdult) {
		super(id, name, price, amount, ProductType.Bulk, bonus, forAdult);
	}

	public BulkProduct(ResultSet resultSet) throws SQLException {
		super(resultSet);
	}

	@Override
	public int getRandomProductAmount(int maxProductAmount) {
		return getRandomNumber(100, maxProductAmount);
	}
}
