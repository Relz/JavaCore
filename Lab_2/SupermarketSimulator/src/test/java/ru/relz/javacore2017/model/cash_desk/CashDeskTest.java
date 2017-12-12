package ru.relz.javacore2017.model.cash_desk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.relz.javacore2017.model.customer.Customer;
import ru.relz.javacore2017.model.customer.CustomerType;
import ru.relz.javacore2017.model.product.Product;
import ru.relz.javacore2017.model.product.ProductType;
import ru.relz.javacore2017.payment.ProductSummator;
import ru.relz.javacore2017.supermarket.Reporter;
import ru.relz.javacore2017.supermarket.Supermarket;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CashDeskTest {
	private static final int supermarketWorkingTime = 5;
	private static final int customerCount = 5;
	private static final Product processQueueProduct = new Product(1, "Продукт", 50, 2, ProductType.Packed, 10, false);
	private CashDesk cashDesk;

	@BeforeAll
	static void beforeAll() {
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) { }
		}));
	}

	@BeforeEach
	void setUp() throws SQLException, ClassNotFoundException {
		cashDesk = new CashDesk(new Supermarket(supermarketWorkingTime));
	}

	@Test
	void closeClearsQueue() {
		addCustomersToCashDesk(cashDesk, customerCount);
		assertTrue(cashDesk.isOpened());
		assertFalse(cashDesk.isClosed());
		assertEquals(customerCount, cashDesk.getQueueSize());
		cashDesk.close();
		assertFalse(cashDesk.isOpened());
		assertTrue(cashDesk.isClosed());
		assertEquals(0, cashDesk.getQueueSize());
	}

	@Test
	void processQueueDoNothingIfClose() {
		int customerCountToProcess = 2;
		cashDesk.close();
		addCustomersToCashDesk(cashDesk, customerCount);
		cashDesk.processQueue(customerCountToProcess);
		assertEquals(customerCount, cashDesk.getQueueSize());
	}

	@Test
	void processQueueGiveProductBackIfProductIsForAdultAndCustomerIsChild() {
		Customer customer = createCustomer(100, 99, 99);
		customer.getBacket().add(new Product(1, "Продукт для взрослых", 50, 2, ProductType.Packed, 10, true));
		cashDesk.addCustomerToQueue(customer);
		cashDesk.processQueue(1);
		assertEquals(0, cashDesk.getQueueSize());
		assertTrue(customer.getBacket().isEmpty());
		assertEquals(100, customer.getCash());
	}

	@Test
	void processQueueGiveProductBackIfProductIsTooExpensiveForChild() {
		Customer customer = createCustomer(100, 99, 99);
		customer.getBacket().add(new Product(1, "Очень дорогой товар", 99999, 1, ProductType.Packed, 10, false));
		cashDesk.addCustomerToQueue(customer);
		cashDesk.processQueue(1);
		assertEquals(0, cashDesk.getQueueSize());
		assertTrue(customer.getBacket().isEmpty());
		assertEquals(100, customer.getCash());
	}

	@Test
	void processQueuePayByCash() throws IllegalAccessException {
		Product product = processQueueProduct;
		Customer customer = createCustomer(100, 99, 99);
		customer.getBacket().add(product);
		cashDesk.addCustomerToQueue(customer);
		Reporter reporter = new Reporter();
		cashDesk.setReporter(reporter);
		cashDesk.processQueue(1);
		assertEquals(0, cashDesk.getQueueSize());
		assertTrue(customer.getBacket().isEmpty());
		assertEquals(0, customer.getCash());

		Field productSummatorField = setReporterFieldAccessible(reporter, "productSummator");
		ProductSummator productSummator = (ProductSummator) productSummatorField.get(reporter);
		assertFalse(productSummator.isEmpty());
		assertEquals(product.getPrice() * product.getAmount(), productSummator.calculateTotalAmount());
		assertEquals(product.getBonus() * product.getAmount(), productSummator.calculateTotalBonuses());
	}

	@Test
	void processQueuePayByCardCash() throws IllegalAccessException {
		Product product = processQueueProduct;
		Customer customer = createCustomer(99, 100, 99);
		customer.getBacket().add(product);
		cashDesk.addCustomerToQueue(customer);
		cashDesk.processQueue(1);
		assertEquals(0, customer.getCardCash());
	}

	@Test
	void processQueuePayByBonuses() throws IllegalAccessException {
		Product product = processQueueProduct;
		Customer customer = createCustomer(99, 99, 100);
		customer.getBacket().add(product);
		cashDesk.addCustomerToQueue(customer);
		cashDesk.processQueue(1);
		assertEquals(0, customer.getBonusCount());
	}

	private void addCustomersToCashDesk(CashDesk cashDesk, int count) {
		for (int i = 0; i < count; ++i) {
			cashDesk.addCustomerToQueue(createCustomer(0, 0, 0));
		}
	}

	private Customer createCustomer(double cash, double cardCash, double bonusCount) {
		Customer customer = new Customer(CustomerType.Child, cash, cardCash, bonusCount);
		customer.setId(0);

		return customer;
	}

	private static Field setReporterFieldAccessible(Reporter reporter, String fieldName) {
		Field field = null;
		try {
			field = reporter.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			fail("Reporter does not have field \"" + fieldName + "\"");
		}
		return field;
	}
}
