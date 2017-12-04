package ru.relz.javacore2017.model.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class Product implements ProductInterface {
	public Product(
			int id, String name, double price, int amount, ProductType productType, double bonus, boolean forAdult
	) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.productType = productType;
		this.bonus = bonus;
		this.forAdult = forAdult;
	}

	public Product(ResultSet resultSet) throws SQLException {
		this(
				resultSet.getInt(1),
				resultSet.getString(2),
				resultSet.getDouble(3),
				resultSet.getInt(4),
				ProductType.createFromInteger(resultSet.getInt(5)),
				resultSet.getInt(6),
				resultSet.getBoolean(7)
		);
	}

	private final int id;
	public int getId() {
		return id;
	}

	private final String name;
	public String getName() {
		return name;
	}

	private final double price;
	public double getPrice() {
		return price;
	}

	private int amount;
	public int getAmount() {
		return amount;
	}

	public void setAmount(int value) {
		amount = value;
	}

	private final ProductType productType;
	public ProductType getType() {
		return productType;
	}

	private final double bonus;
	public double getBonus() {
		return bonus;
	}

	private final boolean forAdult;
	public boolean isForAdult() {
		return forAdult;
	}

	public int getRandomProductAmount(int maxProductAmount) {
		return getRandomNumber(1, maxProductAmount);
	}
}
