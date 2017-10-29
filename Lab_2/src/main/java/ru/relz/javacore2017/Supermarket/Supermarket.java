package main.java.ru.relz.javacore2017.Supermarket;

import main.java.ru.relz.javacore2017.CashDesk.*;
import main.java.ru.relz.javacore2017.Customer.*;
import main.java.ru.relz.javacore2017.Database.Database;
import main.java.ru.relz.javacore2017.Payment.Discount;
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
	private final List<NamedCashDesk> cashDesks = new ArrayList<>();
	private final HashMap<CustomerType, Discount> discountPercentages = new HashMap<>();
	private final Logger logger = new Logger();

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
	List<Product> getProducts() {
		return products;
	}

	/**
	 * Returns discount for specified customer type
	 *
	 * @param customerType customer type
	 *
	 * @return Discount object for {@code customerType}
	 * */
	public Discount getDiscount(CustomerType customerType) {
		return discountPercentages.containsKey(customerType)
				? discountPercentages.get(customerType)
				: new Discount(0);
	}

	/**
	 * Adds percentage discount to all customers with specified type
	 *
	 * @param customerType customer type
	 * @param percentage percentage of discount to specified customer type
	 * */
	public void addDiscount(CustomerType customerType, int percentage) {
		discountPercentages.put(customerType, new Discount(percentage));
	}

	/**
	 * Adds cash desk to supermarket with specified name
	 *
	 * @param name name for cash desk
	 * */
	public void addCashDesk(String name) {
		NamedCashDesk cashDesk = new NamedCashDesk(this, name);
		cashDesk.setLogger(logger);
		cashDesks.add(cashDesk);
	}

	/**
	 * Performs the given action for each Customer
	 * */
	void forEachCustomer(Consumer<Iterator<Customer>> action) {
		Iterator<Customer> customerIterator = customers.iterator();
		while (customerIterator.hasNext()) {
			action.accept(customerIterator);
		}
	}

	/**
	 * Adds a Customer object to the list of supermarket's customers,
	 * prints about Customer arrival.
	 */
	void addCustomer(Customer customer) {
		++customerId;
		customers.add(customer);
		customer.setId(customerId);
		System.out.printf("%s вошёл в магазин\n", customer.getFullName());
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
			System.out.printf("%s положил в свою корзину %s", customer.getFullName(), result.getAmount());
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
		String customerName = customer.getFullName();
		if (!customer.getBacket().isEmpty()) {
			System.out.printf("Предотвращена попытка ухода покупателя %s с корзиной с продуктами\n", customerName);

			return;
		}
		System.out.printf("%s вышел из магазина\n", customerName);
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
		System.out.printf("%s положил %s обратно\n", customer.getFullName(), product.getName());
	}

	/**
	 * Performs supermarket work cycle until end of working time.
	 * Calls {@code onEachTimeUnit} method each unit of time.
	 * Calls {@code onFinished} method on working time ends.
	 */
	public void work(SupermarketWorkInterface supermarketWorkInterface) throws IOException {
		System.out.println("Магазин начал свою работу");
		while (workingTimeMinutes > 0) {
			System.out.printf("Сейчас %s\n", dateFormat.format(startDate.getTime() + workingTimeLeft * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND));
			supermarketWorkInterface.onEachTimeUnit(this);
			workingTimeMinutes -= TIME_UNIT_MINUTES;
			workingTimeLeft += TIME_UNIT_MINUTES;
			System.in.read();
		}
		System.out.println("Кассы завершили свою работу, магазин готовится к закрытию");
		supermarketWorkInterface.onFinished(this);
	}

	/**
	 * Determines opened cash desk with less customers in queue
	 *
	 * @return opened NamedCashDesk object in supermarket with less customers in queue
	 * */
	NamedCashDesk getBestCashDesk() {
		NamedCashDesk bestCashDesk = cashDesks.get(0);
		for (NamedCashDesk cashDesk : cashDesks) {
			if (cashDesk.isOpened() && cashDesk.getQueueSize() < bestCashDesk.getQueueSize()) {
				bestCashDesk = cashDesk;
			}
		}

		return bestCashDesk;
	}

	/**
	 * Processes each opened cash desk in supermarket
	 * */
	void processCashDesks() {
		cashDesks.forEach((NamedCashDesk cashDesk) -> {
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

	void printOutLog() {
		logger.printOut();
	}
}
