package main.java.ru.relz.javacore2017.Basket;

import main.java.ru.relz.javacore2017.Product.Product;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Container {
	/**
	 * Adds a Product object to the Basket object back.
	 *
	 * @param product product to add to basket
	 */
	void add(Product product);

	/**
	 * Performs the given action for each element of the basket
	 *
	 * @param action callback to call every product iterator
	 * */
	void forEachProduct(Consumer<Iterator<Product>> action);

	/**
	 * Returns {@code true} if Basket object contains no elements.
	 *
	 * @return {@code true} if Basket object contains no elements
	 */
	boolean isEmpty();
}
