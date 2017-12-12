package ru.relz.javacore2017.model.ProductContainer;

import ru.relz.javacore2017.model.product.Product;

import java.util.Iterator;

interface RemovableByIterator {

	/**
	 * Returns product, removing it from container by it's iterator.
	 *
	 * @param productIterator product iterator to remove from container
	 * @return the removed product or {@code null}
	 */
	Product remove(Iterator<Product> productIterator);
}
