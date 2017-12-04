package ru.relz.javacore2017.supermarket;

import ru.relz.javacore2017.model.cash_desk.NamedCashDesk;
import ru.relz.javacore2017.model.customer.Customer;
import ru.relz.javacore2017.model.customer.CustomerType;
import ru.relz.javacore2017.model.product.Product;

import java.util.Iterator;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class SupermarketWork implements SupermarketWorkInterface {
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
				int productMaxAmount = randomProduct.getAmount();
				if (productMaxAmount == 0) {
					continue;
				}
				int productAmount = randomProduct.getRandomProductAmount(productMaxAmount);
				Product wantedProduct = supermarket.getProduct(customer, randomProduct.getId(), productAmount);
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
						customer.getBacket().remove(productIterator);
						supermarket.giveProductBack(product, customer);
					});
					supermarket.removeCustomer(customerIterator, customer);
				} else {
					// May be return back some products
					customer.getBacket().forEachProduct((Iterator<Product> productIterator) -> {
						Product product = productIterator.next();
						if (getRandomNumber(0, 4) == 0) {
							customer.getBacket().remove(productIterator);
							supermarket.giveProductBack(product, customer);
						}
					});
				}
			}
			if (!customer.isInQueue() && !customer.getBacket().isEmpty()) {
				// Join to cash desk
				NamedCashDesk bestCashDesk = supermarket.getBestCashDesk();
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
				customer.getBacket().remove(productIterator);
				supermarket.giveProductBack(product, customer);
			});
			supermarket.removeCustomer(customerIterator, customer);
		});
		supermarket.printReport();
	}
}
