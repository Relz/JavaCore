package main.java.ru.relz.javacore2017;

public class PackedProduct extends Product {
	PackedProduct(int price, int bonus, int quantity) {
		super(price, bonus);

		_quantity = quantity;
	}

	private int _quantity;
	public int getQuantity() {
		return _quantity;
	}
}
