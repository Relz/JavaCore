package main.java.ru.relz.javacore2017;

import java.util.Iterator;

import static main.java.ru.relz.javacore2017.RandomHelper.getRandomNumber;
import static main.java.ru.relz.javacore2017.RandomHelper.getRandomProductAmount;

class SupermarketWork implements SupermarketWorkInterface {
	@Override
	public void onEachTimeUnit(Supermarket supermarket) {
		for (int i = 0; i < getRandomNumber(0, Supermarket.TIME_UNIT_MINUTES); ++i) {
			supermarket.addCustomer(new Customer(CustomerType.getRandom(), getRandomNumber(0, 1000), getRandomNumber(0, 10000), getRandomNumber(0, 1000)));
		}
		supermarket.forEachCustomer((Iterator<Customer> customerIterator) -> {
			Customer customer = customerIterator.next();

			int wantedProductCount = getRandomNumber(0, 6);
			for (int i = 0; i < wantedProductCount; ++i) {
				int randomProductIndex = getRandomNumber(0, supermarket.getProducts().size() - 1);
				Product randomProduct = supermarket.getProducts().get(randomProductIndex);
				int productId = randomProduct.getId();
				int productMaxAmount = randomProduct.getAmount();
				int productAmount = getRandomProductAmount(randomProduct, productMaxAmount / 5);
				Product wantedProduct = supermarket.getProduct(customer, productId, productAmount);
				if (wantedProduct != null) {
					customer.getBacket().add(wantedProduct);
				}
			}
			if (getRandomNumber(0, 1) == 0) {
				int randomNumber = getRandomNumber(0, 6);
				if (randomNumber == 0) {
					// Try to get out without returning back products
					supermarket.removeCustomer(customerIterator, customer);
				} else if (randomNumber == 1) {
					// Return back products and get out
					customer.getBacket().forEachProduct((Iterator<Product> productIterator) -> {
						Product product = productIterator.next();
						customer.getBacket().get(productIterator);
						supermarket.returnProductBack(product, customer);
					});
					supermarket.removeCustomer(customerIterator, customer);
				} else {
					// May be return back some products
					customer.getBacket().forEachProduct((Iterator<Product> productIterator) -> {
						Product product = productIterator.next();
						if (getRandomNumber(0, 4) == 0) {
							customer.getBacket().get(productIterator);
							supermarket.returnProductBack(product, customer);
						}
					});
				}
			}
			if (!customer.isInQueue() && !customer.getBacket().isEmpty()) {
				// Join to cash desk
				CashDesk bestCashDesk = supermarket.getBestCashDesk();
				bestCashDesk.addCustomerToQueue(customer);
			}
		});
		supermarket.processCashDesks();
	}

	@Override
	public void onFinished(Supermarket supermarket) {
		supermarket.forEachCustomer((Iterator<Customer> customerIterator) -> {
			Customer customer = customerIterator.next();
			customer.getBacket().forEachProduct((Iterator<Product> productIterator) -> {
				Product product = productIterator.next();
				customer.getBacket().get(productIterator);
				supermarket.returnProductBack(product, customer);
			});
			supermarket.removeCustomer(customerIterator, customer);
		});
	}
}
