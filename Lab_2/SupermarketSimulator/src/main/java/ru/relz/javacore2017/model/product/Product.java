package ru.relz.javacore2017.model.product;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class Product implements ProductInterface {
	public Product(
			int id, String name, double price, int amount, ProductType type, double bonus, boolean forAdult
	) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.type = type;
		this.bonus = bonus;
		this.forAdult = forAdult;
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

	private final ProductType type;
	public ProductType getType() {
		return type;
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
		if (type == ProductType.Packed) {
			return getRandomNumber(1, maxProductAmount);
		} else {
			return getRandomNumber(100, maxProductAmount);
		}
	}
}
