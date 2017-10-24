package main.java.ru.relz.javacore2017.CashDesk;

import main.java.ru.relz.javacore2017.Customer.*;
import main.java.ru.relz.javacore2017.Payment.*;
import main.java.ru.relz.javacore2017.Product.*;
import main.java.ru.relz.javacore2017.Supermarket.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class NamedCashDesk implements CashDesk {
	private final String name;
	private final Supermarket supermarket;
	private final List<Customer> queue = new ArrayList<>();
	private final List<Product> products = new ArrayList<>();

	public NamedCashDesk(Supermarket supermarket, String name) {
		this.name = name;
		this.supermarket = supermarket;
	}

	public int getQueueSize() {
		return queue.size();
	}

	private boolean opened = true;
	public boolean isOpened() {
		return opened;
	}

	public void open() {
		opened = true;
	}

	public void close() {
		System.out.println("Касса " + name + " закрылась");
		opened = false;
		Iterator<Customer> customerIterator = queue.iterator();
		while (customerIterator.hasNext()) {
			Customer customer = customerIterator.next();
			removeCustomerFromQueue(customerIterator, customer);
		}
		queue.clear();
	}

	public void addCustomerToQueue(Customer customer) {
		queue.add(customer);
		customer.setInQueue(true);
		System.out.println(customer.getFullName() + " встал в очередь на кассу " + name);
	}

	public void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer) {
		customerIterator.remove();
		customer.setInQueue(false);
		System.out.println(customer.getFullName() + " покинул кассу " + name);
	}

	public void processQueue(int customerCount) {
		if (!opened) {
			return;
		}
		forEachNCustomers(customerCount, (Iterator<Customer> customerIterator) -> {
			Customer customer = customerIterator.next();
			products.clear();

			Bill bill = new Bill();
			while (!customer.getBacket().isEmpty()) {
				Product product = customer.getBacket().get();
				if (product.isForAdult() && customer.getType() == CustomerType.Child) {
					System.out.println("Предотвращена попытка " + customer.getFullName() + " купить " + product.getName());
					supermarket.returnProductBack(product, customer);
					continue;
				}
				bill.add(product);
				products.add(product);
			}

			System.out.print("Сумма покупки: " + bill.getTotalAmount());
			Discount discount = new Discount(supermarket.getDiscountPercentages().get(customer.getType()));
			bill.applyDiscount(discount);
			if (discount.getPercentage() != 0) {
				System.out.print(", со скидкой: " + bill.getTotalAmount());
			}
			System.out.println();

			PaymentMethod paymentMethod = customer.getDesiredPaymentMethod(bill.getTotalAmount());
			System.out.print(customer.getFullName());
			if (!customer.pay(bill.getTotalAmount(), bill.getTotalBonuses(), paymentMethod)) {
				System.out.println(" не в состоянии оплатить покупку (" + bill.getTotalAmount() + ")");
				products.forEach((Product product) -> supermarket.returnProductBack(product, customer));
			} else {
				System.out.print(" оплатил покупку (" + bill.getTotalAmount() + ")");
				switch (paymentMethod) {
					case Cash:
						System.out.println(" наличными и получил " + bill.getTotalBonuses() + " бонусов");
						break;
					case Card:
						System.out.println(" картой и получил " + bill.getTotalBonuses() + " бонусов");
						break;
					case Bonuses:
						System.out.println(" бонусами");
						break;
				}
			}

			customerIterator.remove();
			supermarket.removeCustomer(null, customer);
		});
	}

	private void forEachNCustomers(int customerCount, Consumer<Iterator<Customer>> action) {
		Iterator<Customer> customerIterator = queue.iterator();
		while (customerIterator.hasNext()) {
			if (customerCount == 0) {
				break;
			}
			action.accept(customerIterator);
			--customerCount;
		}
	}
}
