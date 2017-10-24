package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CashDesk {
	private final String name;
	private final Supermarket supermarket;
	private final List<Customer> queue = new ArrayList<>();
	private final List<Product> products = new ArrayList<>();

	CashDesk(Supermarket supermarket, String name) {
		this.name = name;
		this.supermarket = supermarket;
	}

	int getQueueSize() {
		return queue.size();
	}

	private boolean opened = true;
	boolean isOpened() {
		return opened;
	}

	void open() {
		opened = true;
	}

	void close() {
		System.out.println("Касса " + name + " закрылась");
		opened = false;
		Iterator<Customer> customerIterator = queue.iterator();
		while (customerIterator.hasNext()) {
			Customer customer = customerIterator.next();
			removeCustomerFromQueue(customerIterator, customer);
		}
		queue.clear();
	}

	void addCustomerToQueue(Customer customer) {
		queue.add(customer);
		customer.setInQueue(true);
		System.out.println(supermarket.getCustomerName(customer) + " встал в очередь на кассу " + name);
	}

	void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer) {
		customerIterator.remove();
		customer.setInQueue(false);
		System.out.println(supermarket.getCustomerName(customer) + " покинул кассу " + name);
	}

	void processQueue(int customerCount) {
		if (!opened) {
			return;
		}
		Iterator<Customer> customerIterator = queue.iterator();
		while (customerIterator.hasNext()) {
			Customer customer = customerIterator.next();
			if (customerCount == 0) {
				break;
			}
			products.clear();

			String customerName = supermarket.getCustomerName(customer);
			Bill bill = new Bill();
			while (!customer.getBacket().isEmpty()) {
				Product product = customer.getBacket().get();
				if (product.isForAdult() && customer.getType() == CustomerType.Child) {
					System.out.println("Предотвращена попытка " + customerName + " купить " + product.getName());
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
			System.out.print(customerName);
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
			--customerCount;
		}
	}
}
