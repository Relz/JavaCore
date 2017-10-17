package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.List;

class Basket {
	private final List<Product> _products = new ArrayList<>();

	/**
	 * Adds a Product object to the Basket object back.
	 */
	void add(Product product) {
		_products.add(product);
	}

	/**
	 * Returns a Product object located in the Basket object beginning, removing it from Basket object.
	 *
	 * @return the product
	 */
	Product get() {
		return _products.remove(0);
	}

	/**
	 * Returns {@code true} if Basket object contains no elements.
	 *
	 * @return {@code true} if Basket object contains no elements
	 */
	boolean isEmpty() {
		return _products.isEmpty();
	}
}
