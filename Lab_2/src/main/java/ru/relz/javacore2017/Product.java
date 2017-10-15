package main.java.ru.relz.javacore2017;

abstract class Product implements IProduct {

	Product(String name, int price, int bonus) {
		_name = name;
		_price = price;
		_bonus = bonus;
	}

	private final String _name;
	public String getName() {
		return _name;
	}

	private final int _price;
	public int getPrice() {
		return _price;
	}

	private final int _bonus;
	public int getBonus() {
		return _bonus;
	}

	static class Name {
		static String milk = "Молоко";
		static String water = "Вода";
		static String juice = "Сок";
		static String bread = "Хлеб";
		static String bear = "Пиво";
		static String rolton = "Ролтон";
		static String vodka = "Водка";
		static String cigarette = "Сигареты";
	}
}
