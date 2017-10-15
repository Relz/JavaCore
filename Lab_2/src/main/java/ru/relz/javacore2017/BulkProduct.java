package main.java.ru.relz.javacore2017;

public class BulkProduct extends Product {
	BulkProduct(String name, int price, int bonus, int weight) {
		super(name, price, bonus);

		_weight = weight;
	}

	private final int _weight;
	public int getWeight() {
		return _weight;
	}
}
