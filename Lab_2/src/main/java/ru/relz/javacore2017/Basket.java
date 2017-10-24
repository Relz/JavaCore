package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

class Basket {
	private final List<Product> products = new ArrayList<>();

	/**
	 * Adds a Product object to the Basket object back.
	 */
	void add(Product product) {
		products.add(product);
	}

	/**
	 * Performs the given action for each element of the basket
	 * */
	void forEachProduct(Consumer<Iterator<Product>> action) {
		Iterator<Product> productIterator = products.iterator();
		while (productIterator.hasNext()) {
			action.accept(productIterator);
		}
	}

	/**
	 * Returns a product object located in the basket object beginning, removing it from basket object.
	 *
	 * @return the product
	 */
	Product get() {
		return products.remove(0);
	}

	/**
	 * Returns a product object, removing it from basket object.
	 *
	 * @return the product
	 */
	Product get(Product product) {
		products.remove(product);

		return product;
	}

	/**
	 * Removes product from basket object.
	 */
	void get(Iterator<Product> productIterator) {
		productIterator.remove();
	}

	/**
	 * Returns {@code true} if Basket object contains no elements.
	 *
	 * @return {@code true} if Basket object contains no elements
	 */
	boolean isEmpty() {
		return products.isEmpty();
	}
}
