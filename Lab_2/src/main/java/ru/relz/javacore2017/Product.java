package main.java.ru.relz.javacore2017;

abstract class Product implements IProduct {
	Product(int price, int bonus) {
		_price = price;
		_bonus = bonus;
	}

	private final int _price;
	public int getPrice() {
		return _price;
	}

	private final int _bonus;
	public int getBonus() {
		return _bonus;
	}
}
