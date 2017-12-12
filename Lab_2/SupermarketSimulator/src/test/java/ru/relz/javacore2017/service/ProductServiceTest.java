package ru.relz.javacore2017.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.relz.javacore2017.database.DatabaseHelper;
import ru.relz.javacore2017.model.product.Product;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
	@BeforeAll
	static void init() throws SQLException, ClassNotFoundException {
		DatabaseHelper.createConnection();
	}
	@AfterAll
	static void afterAll() throws SQLException {
		DatabaseHelper.closeConnection();
	}
	@Test
	void getProductReturnsNullIfIdNotFound() {
		Product product = ProductService.getProduct(999999999);
		assertNull(product);
	}
	@Test
	void getProducts() {
		List<Product> products = ProductService.getProducts();
		assertNotNull(products);
	}
	@Test
	void fetchProduct() {
		Product product = ProductService.getProduct(1);
		assertNotNull(product);
		Product fetchedProduct = ProductService.fetchProduct(product.getId(), product.getAmount());
		assertNotNull(fetchedProduct);
		assertTrue(areProductsEqual(product, fetchedProduct));
		ProductService.giveProductBack(fetchedProduct);
	}

//	@Test
//	void giveProductBack() {
//		Product product = ProductService.getProduct(1);
//		assertNotNull(product);
//		ProductService.giveProductBack(product);
//		Product productAfterGivingBack = ProductService.getProduct(product.getId());
//		assertNotNull(productAfterGivingBack);
//		product.setAmount(product.getAmount() * 2);
//		assertTrue(areProductsEqual(product, productAfterGivingBack));
//	}
	@Test
	void fetchProductReturnsNullIfFetchTooMuch() {
		Product product = ProductService.getProduct(1);
		assertNotNull(product);
		Product fetchedProduct = ProductService.fetchProduct(product.getId(), product.getAmount() + 1);
		assertNull(fetchedProduct);
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
