package main.java.ru.relz.javacore2017;

class Product {

	Product(int id, String name, double price, int amount, ProductType productType, int bonus) {
		_id = id;
		_name = name;
		_price = price;
		_amount = amount;
		_productType = productType;
		_bonus = bonus;
	}

	private final int _id;
	public int getId() {
		return _id;
	}

	private final String _name;
	public String getName() {
		return _name;
	}

	private final double _price;
	public double getPrice() {
		return _price;
	}

	private final int _amount;
	int getAmount() {
		return _amount;
	}

	private final ProductType _productType;
	public ProductType getProductType() {
		return _productType;
	}

	private final int _bonus;
	public int getBonus() {
		return _bonus;
	}
}
