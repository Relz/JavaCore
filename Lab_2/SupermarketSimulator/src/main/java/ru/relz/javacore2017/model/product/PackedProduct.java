package ru.relz.javacore2017.model.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class PackedProduct extends Product {
	public PackedProduct(int id, String name, double price, int amount, double bonus, boolean forAdult) {
		super(id, name, price, amount, ProductType.Packed, bonus, forAdult);
	}

	public PackedProduct(ResultSet resultSet) throws SQLException {
		super(resultSet);
	}

	@Override
	public int getRandomProductAmount(int maxProductAmount) {
		return getRandomNumber(1, maxProductAmount);
	}
}
