package main.java.ru.relz.javacore2017;

class PackedProduct extends Product {
	PackedProduct(int id, int price, int bonus, int quantity) {
		super(id, price, bonus);

		_quantity = quantity;
	}

	private final int _quantity;
	int getQuantity() {
		return _quantity;
	}
}
