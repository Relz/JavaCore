package main.java.ru.relz.javacore2017;

class PackedProduct extends Product {
	PackedProduct(String name, int price, int bonus, int quantity) {
		super(name, price, bonus);

		_quantity = quantity;
	}

	private int _quantity;
	int getQuantity() {
		return _quantity;
	}
}
