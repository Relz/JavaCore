package main.java.ru.relz.javacore2017.Basket;

import main.java.ru.relz.javacore2017.Product.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Basket implements Container {
	private final List<Product> products = new ArrayList<>();

	public void add(Product product) {
		products.add(product);
	}

	public void forEachProduct(Consumer<Iterator<Product>> action) {
		Iterator<Product> productIterator = products.iterator();
		while (productIterator.hasNext()) {
			action.accept(productIterator);
		}
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	/**
	 * Returns a product object located in the basket object beginning, removing it from basket object.
	 *
	 * @return the product
	 */
	public Product get() {
		return products.remove(0);
	}

	/**
	 * Returns a product object, removing it from basket object.
	 *
	 * @param product product to remove from basket and return
	 *
	 * @return the removed product
	 */
	public Product get(Product product) {
		products.remove(product);

		return product;
	}

	/**
	 * Removes product from basket object by it's iterator.
	 *
	 * @param productIterator product iterator to remove from basket
	 */
	public void get(Iterator<Product> productIterator) {
		productIterator.remove();
	}
}
