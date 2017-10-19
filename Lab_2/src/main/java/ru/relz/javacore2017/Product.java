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
	int getId() {
		return _id;
	}

	private final String _name;
	String getName() {
		return _name;
	}

	private final double _price;
	double getPrice() {
		return _price;
	}

	private final int _amount;
	int getAmount() {
		return _amount;
	}

	private final ProductType _productType;
	ProductType getType() {
		return _productType;
	}

	private final int _bonus;
	int getBonus() {
		return _bonus;
	}
}
