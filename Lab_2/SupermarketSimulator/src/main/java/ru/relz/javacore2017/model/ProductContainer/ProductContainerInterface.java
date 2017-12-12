package ru.relz.javacore2017.model.ProductContainer;

import ru.relz.javacore2017.model.product.Product;

public interface ProductContainerInterface extends RemovableByElement, RemovableByIterator, Clearable {
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
}
