package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.List;

class Bill {
	private final List<Product> _products = new ArrayList<>();

	private int _totalAmount = 0;
	int getTotalAmount() {
		return _totalAmount;
	}

	private int _totalBonuses = 0;
	int getTotalBonuses() {
		return _totalBonuses;
	}

	/**
	 * Appends product to end of the bill
	 * */
	void add(Product product) {
		_products.add(product);
		_totalAmount += product.getPrice() * product.getAmount();
		_totalBonuses += product.getBonus() * product.getAmount();
	}

	/**
	 * Removes product from the bill
	 * */
	void remove(Product product) {
		if (_products.remove(product)) {
			_totalAmount -= product.getPrice() * product.getAmount();
			_totalBonuses -= product.getBonus() * product.getAmount();
		}
	}

	/**
	 * Removes all products from the bill
	 * */
	void clear() {
		_products.clear();
		_totalAmount = 0;
	}

	/**
	 * Processes payment
	 * */
	double pay(double customerMoney) {
		return customerMoney - _totalAmount;
	}

	void applyDiscount(Discount discount) {
		_totalAmount -= _totalAmount * discount.getPercentage() / 100;
	}
}
