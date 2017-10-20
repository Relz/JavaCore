package main.java.ru.relz.javacore2017;

import java.util.Iterator;

class SupermarketWork implements SupermarketWorkInterface {

	@Override
	public void onEachTimeUnit(Supermarket supermarket) {
		for (int i = 0; i < getRandomNumber(0, 5); ++i) {
			supermarket.addCustomer(new Customer(CustomerType.getRandom()));
		}
		supermarket.forEachCustomer((Iterator<Customer> customerIterator) -> {
			Customer customer = customerIterator.next();

			for (int i = 0; i < getRandomNumber(0, 12); ++i) {
				int randomProductIndex = getRandomNumber(0, supermarket.getProducts().size() - 1);
				Product randomProduct = supermarket.getProducts().get(randomProductIndex);
				int productId = randomProduct.getId();
				int productMaxAmount = randomProduct.getAmount();
				int productAmount = getRandomProductAmount(randomProduct, productMaxAmount);
				Product wantedProduct = supermarket.getProduct(customer, productId, productAmount);
				if (wantedProduct != null) {
					customer.getBacket().add(wantedProduct);
				}
			}
			if (getRandomNumber(0, 1) == 0) {
				if (getRandomNumber(0, 1) == 0) {
					int randomNumber = getRandomNumber(0, 2);
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
				} else {

				}
			}
		});

	}

	@Override
	public void onFinished() {

	}

	private static int getRandomNumber(int from, int to) {
		return (int) (Math.random() * to) + from;
	}

	private static int getRandomProductAmount(Product product, int productMaxAmount) {
		switch (product.getType()) {
			case Packed:
				return getRandomNumber(1, 10);
			case Bulk:
				return getRandomNumber(100, 10000);
			default:
				return 1;
		}
	}
}
