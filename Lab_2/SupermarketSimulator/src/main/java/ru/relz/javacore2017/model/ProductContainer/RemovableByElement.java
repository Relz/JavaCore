package ru.relz.javacore2017.model.ProductContainer;

import ru.relz.javacore2017.model.product.Product;

public interface RemovableByElement {

	/**
	 * Returns product, removing it from container
	 * or {@code null} if element was not found.
	 *
	 * @param product product to remove from container and return
	 *
	 * @return the removed product or {@code null}
	 */
	Product remove(Product product);
}
