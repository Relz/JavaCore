package main.java.ru.relz.javacore2017.basket;

import main.java.ru.relz.javacore2017.model.product.Product;

import java.util.HashMap;
import java.util.Iterator;

public class ProductContainer implements ProductContainerInterface {
	private final HashMap<Integer, Product> products = new HashMap<>();
	public HashMap<Integer, Product> getProducts() {
		return products;
	}

	public void add(Product product) {
		if (products.containsKey(product.getId())) {
			products.put(
					product.getId(),
					new Product(
							product.getId(),
							product.getName(),
							product.getPrice(),
							product.getAmount() + products.get(product.getId()).getAmount(),
							product.getType(),
							product.getBonus(),
							product.isForAdult()
					)
			);
		} else {
			products.put(product.getId(), product);
		}
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	public Product remove(Product product) {
		return products.remove(product.getId());
	}

	public Product remove(Iterator<Product> productIterator) {
		Product result = productIterator.next();
		productIterator.remove();

		return result;
	}

	/**
	 * Removes all products from the bill
	 * */
	public void clear() {
		products.clear();
	}
}
