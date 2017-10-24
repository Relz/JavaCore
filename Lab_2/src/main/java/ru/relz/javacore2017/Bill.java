package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.List;

class Bill {
	private final List<Product> products = new ArrayList<>();

	private int totalAmount = 0;
	int getTotalAmount() {
		return totalAmount;
	}

	private int totalBonuses = 0;
	int getTotalBonuses() {
		return totalBonuses;
	}

	/**
	 * Appends product to end of the bill
	 * */
	void add(Product product) {
		products.add(product);
		totalAmount += product.getPrice() * product.getAmount();
		totalBonuses += product.getBonus() * product.getAmount();
	}

	/**
	 * Removes product from the bill
	 * */
	void remove(Product product) {
		if (products.remove(product)) {
			totalAmount -= product.getPrice() * product.getAmount();
			totalBonuses -= product.getBonus() * product.getAmount();
		}
	}

	/**
	 * Removes all products from the bill
	 * */
	void clear() {
		products.clear();
		totalAmount = 0;
	}

	/**
	 * Processes payment
	 * */
	double pay(double customerMoney) {
		return customerMoney - totalAmount;
	}

	void applyDiscount(Discount discount) {
		totalAmount -= totalAmount * discount.getPercentage() / 100;
	}
}
