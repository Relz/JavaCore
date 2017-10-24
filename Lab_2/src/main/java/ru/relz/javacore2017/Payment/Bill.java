package main.java.ru.relz.javacore2017.Payment;

import main.java.ru.relz.javacore2017.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class Bill {
	private final List<Product> products = new ArrayList<>();

	private int totalAmount = 0;
	public int getTotalAmount() {
		return totalAmount;
	}

	private int totalBonuses = 0;
	public int getTotalBonuses() {
		return totalBonuses;
	}

	/**
	 * Appends product to end of the bill
	 * */
	public void add(Product product) {
		products.add(product);
		totalAmount += product.getPrice() * product.getAmount();
		totalBonuses += product.getBonus() * product.getAmount();
	}

	/**
	 * Removes product from the bill
	 * */
	public void remove(Product product) {
		if (products.remove(product)) {
			totalAmount -= product.getPrice() * product.getAmount();
			totalBonuses -= product.getBonus() * product.getAmount();
		}
	}

	/**
	 * Removes all products from the bill
	 * */
	public void clear() {
		products.clear();
		totalAmount = 0;
	}

	/**
	 * Applies discount, decreasing total amount
	 * */
	public void applyDiscount(Discount discount) {
		totalAmount -= totalAmount * discount.getPercentage() / 100;
	}
}
