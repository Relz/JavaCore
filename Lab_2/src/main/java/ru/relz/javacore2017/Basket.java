package main.java.ru.relz.javacore2017;

import java.util.List;

class Basket {
	private List<Product> _items;

/**
 * Adds a Product object to the Basket object back.
 * */
	public void add(Product product) {
		_items.add(product);
	}

/**
 * Returns a Product object located in the Basket object beginning, removing it from Basket object.
 *
 * @return the product
 * */
	public Product get() {
		return _items.remove(0);
	}

/**
 * Returns {@code true} if Basket object contains no elements.
 *
 * @return {@code true} if Basket object contains no elements
 */
	public boolean isEmpty() {
		return _items.isEmpty();
	}
}
