package main.java.ru.relz.javacore2017;

class Product {

	Product(int id, String name, double price, int amount, ProductType productType, int bonus, boolean forAdult) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.productType = productType;
		this.bonus = bonus;
		this.forAdult = forAdult;
	}

	private final int id;
	int getId() {
		return id;
	}

	private final String name;
	String getName() {
		return name;
	}

	private final double price;
	double getPrice() {
		return price;
	}

	private final int amount;
	int getAmount() {
		return amount;
	}

	private final ProductType productType;
	ProductType getType() {
		return productType;
	}

	private final int bonus;
	int getBonus() {
		return bonus;
	}

	private final boolean forAdult;
	boolean isForAdult() {
		return forAdult;
	}
}
