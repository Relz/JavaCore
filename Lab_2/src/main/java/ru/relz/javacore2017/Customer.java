package main.java.ru.relz.javacore2017;

class Customer {
	Customer(CustomerType type) {
		_type = type;
	}

	private final CustomerType _type;
	public CustomerType getType() {
		return _type;
	}

	private final Basket _basket = new Basket();
	public Basket getBacket() {
		return _basket;
	}
}
