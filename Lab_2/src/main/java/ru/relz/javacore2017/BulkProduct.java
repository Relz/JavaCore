package main.java.ru.relz.javacore2017;

public class BulkProduct extends Product {
	BulkProduct(int price, int bonus, int weight) {
		super(price, bonus);

		_weight = weight;
	}

	private final int _weight;
	public int getWeight() {
		return _weight;
	}
}
