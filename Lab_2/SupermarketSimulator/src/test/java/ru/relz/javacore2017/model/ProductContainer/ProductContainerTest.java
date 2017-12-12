package ru.relz.javacore2017.model.ProductContainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.relz.javacore2017.model.ProductContainer.ProductContainer;
import ru.relz.javacore2017.model.product.Product;
import ru.relz.javacore2017.model.product.ProductType;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductContainerTest {
	private ProductContainer productContainer;

	@BeforeEach
	void setUp() {
		productContainer = new ProductContainer();
	}

	@Test
	void add() {
		productContainer.add(getProduct(1));
		assertEquals(productContainer.getProducts().size(), 1);
		assertTrue(areProductsEqual(productContainer.getProducts().values().iterator().next(), getProduct(1)));

		productContainer.add(getProduct(2));
		assertEquals(productContainer.getProducts().size(), 2);

		Iterator<Product> productIterator = productContainer.getProducts().values().iterator();
		Product product = null;
		while (productIterator.hasNext()) {
			product = productIterator.next();
		}
		assertNotNull(product);
		assertTrue(areProductsEqual(product, getProduct(2)));

		int amountBefore = getProduct(2).getAmount();
		productContainer.add(getProduct(2));
		assertEquals(productContainer.getProducts().size(), 2);

		Product expectedProduct = getProduct(2);
		expectedProduct.setAmount(2 * amountBefore);
		assertTrue(areProductsEqual(getProductById(productContainer, 2), expectedProduct));
	}

	@Test
	void isEmpty() {
		assertTrue(productContainer.getProducts().isEmpty());
		productContainer.add(getProduct(1));
		assertFalse(productContainer.getProducts().isEmpty());
	}

	@Test
	void removeByProduct() {
		Product product = getProduct(1);
		productContainer.add(product);
		Product removedProduct = productContainer.remove(product);
		assertTrue(productContainer.isEmpty());
		assertEquals(product, removedProduct);
	}

	@Test
	void removeByProductIterator() {
		Product product = getProduct(1);
		productContainer.add(product);
		Iterator<Product> productIterator = productContainer.getProducts().values().iterator();
		Product removedProduct = productContainer.remove(productIterator);
		assertTrue(productContainer.isEmpty());
		assertEquals(product, removedProduct);
	}

	@Test
	void clear() {
		productContainer.add(getProduct(1));
		productContainer.add(getProduct(2));
		productContainer.add(getProduct(3));
		assertEquals(productContainer.getProducts().size(), 3);
		productContainer.clear();
		assertTrue(productContainer.isEmpty());
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

	private Product getProductById(ProductContainer productContainer, int id) {
		for (Product product : productContainer.getProducts().values()) {
			if (product.getId() == id) {
				return product;
			}
		}

		return null;
	}
}
