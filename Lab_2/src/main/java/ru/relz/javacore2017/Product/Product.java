package main.java.ru.relz.javacore2017.Product;

public class Product {

	public Product(int id, String name, double price, int amount, ProductType productType, int bonus, boolean forAdult) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.productType = productType;
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

	private final int amount;
	public int getAmount() {
		return amount;
	}

	private final ProductType productType;
	public ProductType getType() {
		return productType;
	}

	private final int bonus;
	public int getBonus() {
		return bonus;
	}

	private final boolean forAdult;
	public boolean isForAdult() {
		return forAdult;
	}
}
