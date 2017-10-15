package main.java.ru.relz.javacore2017;

abstract class Product implements IProduct {

	Product(int id, int price, int bonus) {
		_id = id;
		_price = price;
		_bonus = bonus;
	}

	private final int _id;
	public int getId() {
		return _id;
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
		static String sugar = "Сахар";
		static String salt = "Соль";
		static String tomato = "Помидоры";
		static String potato = "Картофель";
	}
}
