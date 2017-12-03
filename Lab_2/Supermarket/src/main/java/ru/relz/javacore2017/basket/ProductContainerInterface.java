package main.java.ru.relz.javacore2017.basket;

import main.java.ru.relz.javacore2017.model.product.Product;

import java.util.Iterator;

public interface ProductContainerInterface {
	/**
	 * Adds a product object to container.
	 *
	 * @param product product to add to basket
	 */
	void add(Product product);

	/**
	 * Returns {@code true} if container contains no elements.
	 *
	 * @return {@code true} if container contains no elements
	 */
	boolean isEmpty();

	/**
	 * Returns product, removing it from container
	 * or {@code null} if element was not found.
	 *
	 * @param product product to remove from container and return
	 *
	 * @return the removed product or {@code null}
	 */
	Product remove(Product product);

	/**
	 * Returns product, removing it from container by it's iterator.
	 *
	 * @param productIterator product iterator to remove from container
	 *
	 * @return the removed product or {@code null}
	 */
	Product remove(Iterator<Product> productIterator);

	/**
	 * Removes all products from container
	 * */
	void clear();
}
