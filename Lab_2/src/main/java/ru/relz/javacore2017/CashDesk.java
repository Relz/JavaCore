package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CashDesk {
	private final String _name;
	private final Supermarket _supermarket;
	private final List<Customer> _queue = new ArrayList<>();
	private final List<Product> _products = new ArrayList<>();

	CashDesk(Supermarket supermarket, String name) {
		_name = name;
		_supermarket = supermarket;
	}

	int getQueueSize() {
		return _queue.size();
	}

	private boolean _opened = true;
	boolean isOpened() {
		return _opened;
	}

	void open() {
		_opened = true;
	}

	void close() {
		System.out.println("Касса " + _name + " закрылась");
		_opened = false;
		Iterator<Customer> customerIterator = _queue.iterator();
		while (customerIterator.hasNext()) {
			Customer customer = customerIterator.next();
			removeCustomerFromQueue(customerIterator, customer);
		}
		_queue.clear();
	}

	void addCustomerToQueue(Customer customer) {
		_queue.add(customer);
		customer.setInQueue(true);
		System.out.println(_supermarket.getCustomerName(customer) + " встал в очередь на кассу " + _name);
	}

	void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer) {
		customerIterator.remove();
		customer.setInQueue(false);
		System.out.println(_supermarket.getCustomerName(customer) + " покинул кассу " + _name);
	}

	void processQueue(int customerCount) {
		if (!_opened) {
			return;
		}
		Iterator<Customer> customerIterator = _queue.iterator();
		while (customerIterator.hasNext()) {
			Customer customer = customerIterator.next();
			if (customerCount == 0) {
				break;
			}
			_products.clear();

			String customerName = _supermarket.getCustomerName(customer);
			Bill bill = new Bill();
			while (!customer.getBacket().isEmpty()) {
				Product product = customer.getBacket().get();
				if (product.isForAdult() && customer.getType() == CustomerType.Child) {
					System.out.println("Предотвращена попытка " + customerName + " купить " + product.getName());
					_supermarket.returnProductBack(product, customer);
					continue;
				}
				bill.add(product);
				_products.add(product);
			}
			System.out.println("Сумма покупки: " + bill.getTotalAmount());
			Discount discount = new Discount(_supermarket.getDiscountPercentages().get(customer.getType()));
			bill.applyDiscount(discount);
			System.out.println("Сумма покупки со скидкой: " + bill.getTotalAmount());

			PaymentMethod paymentMethod = customer.getDesiredPaymentMethod(bill.getTotalAmount());
			switch (paymentMethod) {
				case Cash:
					customer.setCash(bill.pay(customer.getCash()));
					System.out.println(customerName + " оплатил покупку (" + bill.getTotalAmount() + ") наличными и получил " + bill.getTotalBonuses() + " бонусов");
					break;
				case Card:
					customer.setCardCash(bill.pay(customer.getCardCash()));
					System.out.println(customerName + " оплатил покупку (" + bill.getTotalAmount() + ") картой и получил " + bill.getTotalBonuses() + " бонусов");
					break;
				case Bonuses:
					customer.setBonusCount(bill.pay(customer.getBonusCount()));
					System.out.println(customerName + " оплатил покупку (" + bill.getTotalAmount() + ") бонусами");
					break;
				default:
					System.out.println(customerName + " не в состоянии оплатить покупку (" + bill.getTotalAmount() + ")");
					_products.forEach((Product product) -> _supermarket.returnProductBack(product, customer));
			}

			customerIterator.remove();
			_supermarket.removeCustomer(null, customer);
			--customerCount;
		}
	}
}
