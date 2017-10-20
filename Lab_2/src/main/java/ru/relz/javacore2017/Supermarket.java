package main.java.ru.relz.javacore2017;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

class Supermarket {
	private static final int timeUnitMinutes = 5;
	private static final int _secondsInMinute = 60;
	private static final int _millisecondsInSecond = 1000;

	private final List<Customer> _customers = new ArrayList<>();
	private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final Date startDate = new Date();
	private final HashMap<Customer, Integer> _customersToIds = new HashMap<>();
	private int _customerId = 0;

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

	private List<Product> _products = new ArrayList<>();
	List<Product> getProducts() {
		return _products;
	}

	/**
	 * Performs the given action for each customer
	 * */
	void forEachCustomer(Consumer<Iterator<Customer>> action) {
		Iterator<Customer> customerIterator = _customers.iterator();
		while (customerIterator.hasNext()) {
			action.accept(customerIterator);
		}
	}

	/**
	 * Adds a Customer object to the list of supermarket's customers,
	 * prints about customer arrival.
	 */
	void addCustomer(Customer customer) {
		++_customerId;
		_customers.add(customer);
		_customersToIds.put(customer, _customerId);
		System.out.println(getCustomerName(customer) + " вошёл в магазин");
	}

	/**
	 * Asks database for product with specified id and amount.
	 * removes product amount from database and prints customer's product getting action
	 * if there is such product id and enough amount.
	 *
	 * @return {@code null} if there is no product with such id or there is not enough product amount,
	 * 			otherwise product object
	 * */
	Product getProduct(Customer customer, int productId, int productAmount) {
		Product result = Database.getProduct(productId, productAmount);
		if (result != null) {
			System.out.print(getCustomerName(customer) + " положил в свою корзину " + result.getAmount());
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
	 * Removes the first occurrence of the specified element from this list of customers
	 * if he hasn't any product in his basket,
	 * prints customer's action
	 * */
	void removeCustomer(Iterator<Customer> customerIterator, Customer customer) {
		String customerName = getCustomerName(customer);
		if (!customer.getBacket().isEmpty()) {
			System.out.println("Предотвращена попытка ухода покупателя " + customerName + " с корзиной с продуктами");

			return;
		}
		System.out.println(customerName + " вышел из магазина");
		customerIterator.remove();
	}

	/**
	 * Returns product back to supermarket, prints customer's return back action
	 * */
	void returnProductBack(Product product, Customer customer) {
		Database.returnBackProduct(product);
		System.out.println(getCustomerName(customer) + " положил " + product.getName() + " обратно");
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

	/**
	 * Returns customer name by his type and number
	 *
	 * @return customer type and number in string typedef
	 * */
	private String getCustomerName(Customer customer) {
		return customer.getType().toString() + " " + _customersToIds.get(customer);
	}
}
