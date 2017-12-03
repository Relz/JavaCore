package main.java.ru.relz.javacore2017.model.product;

import java.sql.ResultSet;
import java.sql.SQLException;

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

	private final int amount;
	public int getAmount() {
		return amount;
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
}
