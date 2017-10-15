package main.java.ru.relz.javacore2017;

class BulkProduct extends Product {
	BulkProduct(int id, int price, int bonus, int weight) {
		super(id, price, bonus);

		_weight = weight;
	}

	private final int _weight;
	int getWeight() {
		return _weight;
	}
}
