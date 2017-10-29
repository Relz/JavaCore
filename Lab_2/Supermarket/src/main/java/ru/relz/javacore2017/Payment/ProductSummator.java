package main.java.ru.relz.javacore2017.Payment;

import main.java.ru.relz.javacore2017.Product.Product;

import java.util.Collection;
import java.util.HashMap;

public class ProductSummator {
	private final HashMap<Integer, Product> products = new HashMap<>();
	public Collection<Product> getProducts() {
		return products.values();
	}

	public double calculateTotalAmount() {
		double result = 0;
		for (Product product : getProducts()) {
			result += product.getPrice() * product.getAmount();
		}

		return result;
	}

	public double calculateTotalBonuses() {
		double result = 0;
		for (Product product : getProducts()) {
			result += product.getBonus() * product.getAmount();
		}

		return result;
	}

	/**
	 * Appends product to end of the bill
	 * */
	public void add(Product product) {
		if (products.containsKey(product.getId())) {
			products.put(
					product.getId(),
					new Product(product.getId(), product.getName(), product.getPrice(), product.getAmount() + products.get(product.getId()).getAmount(), product.getType(), product.getBonus(), product.isForAdult())
			);
		} else {
			products.put(product.getId(), product);
		}
	}

	/**
	 * Removes product from the bill
	 * */
	public void remove(Product product) {
		products.remove(product.getId());
	}

	/**
	 * Removes all products from the bill
	 * */
	public void clear() {
		products.clear();
	}
}
