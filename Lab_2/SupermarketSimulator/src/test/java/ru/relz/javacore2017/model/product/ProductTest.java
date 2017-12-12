package ru.relz.javacore2017.model.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {
	private final int id = 1;
	private final String productName = "Продукт";
	private final int price = 1000;
	private final int amount = 1;
	private final ProductType productType = ProductType.Packed;
	private final int bonus = 2;
	private final boolean isForAdult = false;

	private final Product product = new Product(
			id,
			productName,
			price,
			amount,
			productType,
			bonus,
			isForAdult
	);

	@Test
	void constructorSetsAllFields() {
		assertEquals(id, product.getId());
		assertEquals(productName, product.getName());
		assertEquals(price, product.getPrice());
		assertEquals(amount, product.getAmount());
		assertEquals(productType, product.getType());
		assertEquals(bonus, product.getBonus());
		assertEquals(isForAdult, product.isForAdult());
	}

	@Test
	void getRandomProductAmount() {
		int maxProductAmount = 1000;
		int packedProductAmount = createProduct(ProductType.Packed).getRandomProductAmount(maxProductAmount);
		int bulkProductAmount = createProduct(ProductType.Bulk).getRandomProductAmount(maxProductAmount);

		assertTrue(packedProductAmount >= 1 && packedProductAmount <= maxProductAmount);
		assertTrue(bulkProductAmount >= 100 && bulkProductAmount <= maxProductAmount);
	}

	@Test
	void setAmount() {
		int newAmount = amount + 10;
		product.setAmount(newAmount);
		assertEquals(newAmount, product.getAmount());
	}

	private Product createProduct(ProductType type) {
		return new Product(1, "Продукт", 1, 1, type, 1, false);
	}
}
