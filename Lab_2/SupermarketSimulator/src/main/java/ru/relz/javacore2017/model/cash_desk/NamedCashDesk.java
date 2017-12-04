package ru.relz.javacore2017.model.cash_desk;

import ru.relz.javacore2017.model.customer.*;
import ru.relz.javacore2017.payment.*;
import ru.relz.javacore2017.model.product.*;
import ru.relz.javacore2017.supermarket.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class NamedCashDesk implements CashDesk, LoggingCashDesk {
	private final String name;
	private final Supermarket supermarket;
	private final List<Customer> queue = new ArrayList<>();

	public NamedCashDesk(Supermarket supermarket, String name) {
		this.name = name;
		this.supermarket = supermarket;
	}

	private Reporter reporter;
	public void setReporter(Reporter value) {
		reporter = value;
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
		System.out.printf("Касса %s закрылась\n", name);
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
		System.out.printf("%s встал в очередь на кассу %s\n", customer.getName(), name);
	}

	public void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer) {
		customerIterator.remove();
		customer.setInQueue(false);
		System.out.printf("%s покинул кассу %s\n", customer.getName(), name);
	}

	public void processQueue(int customerCount) {
		if (!opened) {
			return;
		}
		forEachNCustomers(customerCount, (Iterator<Customer> customerIterator) -> {
			Customer customer = customerIterator.next();

			Bill bill = new Bill();
			customer.getBacket().forEachProduct((Iterator<Product> productIterator) -> {
				Product product = customer.getBacket().remove(productIterator);
				if (product.isForAdult() && customer.getType() == CustomerType.Child) {
					System.out.printf("Предотвращена попытка %s купить %s\n", customer.getName(), product.getName());
					supermarket.giveProductBack(product, customer);
				} else {
					bill.add(product);
				}
			});

			if (!bill.getProducts().isEmpty()) {
				System.out.printf("Сумма покупки: %.2f", bill.calculateTotalAmount());
				bill.setDiscount(supermarket.getDiscount(customer.getType()));
				double totalAmount = bill.calculateTotalAmount();
				if (bill.getDiscount().getPercentage() != 0) {
					System.out.printf(", со скидкой: %.2f", totalAmount);
				}
				System.out.println();

				double totalBonuses = bill.calculateTotalBonuses();
				PaymentMethod paymentMethod = customer.getDesiredPaymentMethod(totalAmount);
				System.out.print(customer.getName());
				if (!customer.pay(bill)) {
					System.out.printf(" не в состоянии оплатить покупку (%.2f)\n", totalAmount);
					bill.getProducts().values().forEach((Product product) ->
							supermarket.giveProductBack(product, customer));
				} else {
					System.out.printf(" оплатил покупку (%.2f)", totalAmount);
					switch (paymentMethod) {
						case Cash:
							System.out.printf(" наличными и получил %.2f бонусов\n", totalBonuses);
							break;
						case Card:
							System.out.printf(" картой и получил %.2f бонусов\n", totalBonuses);
							break;
						case Bonuses:
							System.out.printf(" бонусами\n");
							break;
					}
					if (reporter != null) {
						bill.getProducts().values().forEach((Product product) -> reporter.addProductSelling(product));
					}
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
