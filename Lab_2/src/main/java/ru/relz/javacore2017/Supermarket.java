package main.java.ru.relz.javacore2017;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class Supermarket {
	private static final int timeUnitMinutes = 5;
	private static final int _secondsInMinute = 60;
	private static final int _millisecondsInSecond = 1000;

	private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final Date startDate = new Date();

	private int _workingTimeLeft = 0;

	Supermarket() {
		Database.createConnection();
		_products = Database.getProducts();
	}

	private int _workingTimeMinutes;
	int getWorkingTimeMinutes() {
		return _workingTimeMinutes;
	}

	void setWorkingTimeMinutes(int workingTimeMinutes) {
		_workingTimeMinutes = workingTimeMinutes;
	}

	private final List<Customer> _customers = new ArrayList<>();
	List<Customer> getCustomers() {
		return _customers;
	}

	private List<Product> _products = new ArrayList<>();
	List<Product> getProducts() {
		return _products;
	}

	/**
	 * Adds a Customer object to the list of supermarket's customers,
	 * prints about customer arrival.
	 */
	void addCustomer(Customer customer) {
		_customers.add(customer);
		System.out.println(customer.getType().toString() + " вошёл в магазин");
	}

	/**
	 * Asks database for product with specified id and amount.
	 * removes product amount from database and prints customer's product getting action
	 * if there is such product id and enough amount.
	 *
	 * @return {@code null} if there is no product with such id or there is not enough product amount,
	 * 			otherwise product object
	 * */
	Product getProduct(CustomerType customerType, int productId, int productAmount) {
		Product result = Database.getProduct(productId, productAmount);
		if (result != null) {
			System.out.print(customerType.toString() + " положил в свою корзину " + result.getAmount());
			if (result.getType() == ProductType.Packed) {
				System.out.print(" шт ");
			} else if (result.getType() == ProductType.Bulk) {
				System.out.print(" гр ");
			}
			System.out.println(result.getName());
		}

		return result;
	}

	/**
	 * Performs supermarket work cycle until end of working time.
	 * Call {@code onEachTimeUnit} method each unit of time.
	 * Call {@code onFinished} method on working time ends.
	 */
	void work(SupermarketWorkInterface supermarketWorkInterface) {
		System.out.println("Магазин начал свою работу");
		while (_workingTimeMinutes > 0) {
			System.out.println("Сейчас " + dateFormat.format(startDate.getTime() + _workingTimeLeft * _secondsInMinute * _millisecondsInSecond));
			supermarketWorkInterface.onEachTimeUnit(this);
			_workingTimeMinutes -= timeUnitMinutes;
			_workingTimeLeft += timeUnitMinutes;
		}
		supermarketWorkInterface.onFinished();
	}
}
