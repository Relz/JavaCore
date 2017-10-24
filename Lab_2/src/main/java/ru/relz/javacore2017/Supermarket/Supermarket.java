package main.java.ru.relz.javacore2017.Supermarket;

import main.java.ru.relz.javacore2017.CashDesk.*;
import main.java.ru.relz.javacore2017.Customer.*;
import main.java.ru.relz.javacore2017.Database.Database;
import main.java.ru.relz.javacore2017.Product.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

import static main.java.ru.relz.javacore2017.RandomHelper.RandomHelper.getRandomNumber;

public class Supermarket {
	static final int TIME_UNIT_MINUTES = 5;

	private static final int SECONDS_IN_MINUTE = 60;
	private static final int MILLISECONDS_IN_SECOND = 1000;

	private final List<Customer> customers = new ArrayList<>();
	private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final Date startDate = new Date();
	private final HashMap<Customer, Integer> customersToIds = new HashMap<>();
	private final List<CashDesk> cashDesks = new ArrayList<>();

	private int customerId = 0;
	private int workingTimeLeft = 0;

	public Supermarket() {
		Database.createConnection();
		products = Database.getProducts();
	}

	private int workingTimeMinutes;
	public int getWorkingTimeMinutes() {
		return workingTimeMinutes;
	}

	public void setWorkingTimeMinutes(int workingTimeMinutes) {
		this.workingTimeMinutes = workingTimeMinutes;
	}

	private List<Product> products = new ArrayList<>();
	public List<Product> getProducts() {
		return products;
	}

	private HashMap<CustomerType, Integer> discountPercentages = new HashMap<CustomerType, Integer>() {{
		put(CustomerType.Child, 0);
		put(CustomerType.Adult, 0);
		put(CustomerType.Retired, 20);
	}};
	public HashMap<CustomerType, Integer> getDiscountPercentages() {
		return discountPercentages;
	}
	/**
	 * Adds cash desk to supermarket with specified name
	 *
	 * @param name name for cash desk
	 * */
	public void addCashDesk(String name) {
		CashDesk cashDesk = new CashDesk(this, name);
		cashDesks.add(cashDesk);
	}

	/**
	 * Performs the given action for each Customer
	 * */
	public void forEachCustomer(Consumer<Iterator<Customer>> action) {
		Iterator<Customer> customerIterator = customers.iterator();
		while (customerIterator.hasNext()) {
			action.accept(customerIterator);
		}
	}

	/**
	 * Adds a Customer object to the list of supermarket's customers,
	 * prints about Customer arrival.
	 */
	public void addCustomer(Customer customer) {
		++customerId;
		customers.add(customer);
		customersToIds.put(customer, customerId);
		System.out.println(getCustomerName(customer) + " вошёл в магазин");
	}

	/**
	 * Asks database for product with specified id and amount.
	 * removes product amount from database and prints Customer's product getting action
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
	 * prints Customer's action
	 * */
	public void removeCustomer(Iterator<Customer> customerIterator, Customer customer) {
		String customerName = getCustomerName(customer);
		if (!customer.getBacket().isEmpty()) {
			System.out.println("Предотвращена попытка ухода покупателя " + customerName + " с корзиной с продуктами");

			return;
		}
		System.out.println(customerName + " вышел из магазина");
		if (customerIterator == null) {
			customers.remove(customer);
		} else {
			customerIterator.remove();
		}
	}

	/**
	 * Returns product back to supermarket, prints Customer's return back action
	 * */
	public void returnProductBack(Product product, Customer customer) {
		Database.returnBackProduct(product);
		System.out.println(getCustomerName(customer) + " положил " + product.getName() + " обратно");
	}

	/**
	 * Performs supermarket work cycle until end of working time.
	 * Calls {@code onEachTimeUnit} method each unit of time.
	 * Calls {@code onFinished} method on working time ends.
	 */
	public void work(SupermarketWorkInterface supermarketWorkInterface) throws IOException {
		System.out.println("Магазин начал свою работу");
		while (workingTimeMinutes > 0) {
			System.out.println("Сейчас " + dateFormat.format(startDate.getTime() + workingTimeLeft * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND));
			supermarketWorkInterface.onEachTimeUnit(this);
			workingTimeMinutes -= TIME_UNIT_MINUTES;
			workingTimeLeft += TIME_UNIT_MINUTES;
			System.in.read();
		}
		System.out.println("Кассы завершили свою работу, магазин готовится к закрытию");
		supermarketWorkInterface.onFinished(this);
	}

	public CashDesk getBestCashDesk() {
		CashDesk bestCashDesk = cashDesks.get(0);
		for (CashDesk cashDesk : cashDesks) {
			if (cashDesk.isOpened() && cashDesk.getQueueSize() < bestCashDesk.getQueueSize()) {
				bestCashDesk = cashDesk;
			}
		}

		return bestCashDesk;
	}

	public void processCashDesks() {
		cashDesks.forEach((CashDesk cashDesk) -> {
			cashDesk.processQueue(Supermarket.TIME_UNIT_MINUTES);
			if (getRandomNumber(0, 10) == 0) {
				if (cashDesk.isOpened()) {
					cashDesk.close();
				} else {
					cashDesk.open();
				}
			}
		});
	}

	/**
	 * Returns Customer name by his type and number
	 *
	 * @return Customer type and number in string typedef
	 * */
	public String getCustomerName(Customer customer) {
		return customer.getType().toString() + " " + customersToIds.get(customer);
	}
}
