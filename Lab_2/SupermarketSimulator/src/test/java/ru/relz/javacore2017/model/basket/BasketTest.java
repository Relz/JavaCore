package ru.relz.javacore2017.model.basket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.relz.javacore2017.model.product.Product;
import ru.relz.javacore2017.model.product.ProductType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
	private Basket basket;

	@BeforeEach
	void setUp() {
		basket = new Basket();
	}

	@Test
	void forEachProduct() {
		basket.add(getProduct(1));
		basket.add(getProduct(2));
		basket.add(getProduct(3));
		List<Product> products = new ArrayList<>();
		basket.forEachProduct((Iterator<Product> productIterator) -> {
			products.add(productIterator.next());
		});
		for (int i = 0; i < products.size(); ++i) {
			Product product = products.get(i);
			Product expectedProduct = getProduct(i + 1);
			assertTrue(areProductsEqual(product, expectedProduct));
		}
	}

	private Product getProduct(int id) {
		return new Product(id, "Test", 2, 3, ProductType.Packed, 4, true);
	}

	private boolean areProductsEqual(Product lhs, Product rhs) {
		return lhs.getId() == rhs.getId()
				&& lhs.getName().equals(rhs.getName())
				&& lhs.getPrice() == rhs.getPrice()
				&& lhs.getAmount() == rhs.getAmount()
				&& lhs.getType() == rhs.getType()
				&& lhs.getBonus() == rhs.getBonus()
				&& lhs.isForAdult() == rhs.isForAdult();
	}
}
