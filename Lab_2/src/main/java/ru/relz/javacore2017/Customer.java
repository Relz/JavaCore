package main.java.ru.relz.javacore2017;

class Customer {
	Customer(CustomerType type) {
		_type = type;
	}

	private final CustomerType _type;
	CustomerType getType() {
		return _type;
	}

	private final Basket _basket = new Basket();
	Basket getBacket() {
		return _basket;
	}
}
