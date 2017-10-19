package main.java.ru.relz.javacore2017;

class SupermarketWork implements SupermarketWorkInterface {

	@Override
	public void onEachTimeUnit(Supermarket supermarket) {
		for (int i = 0; i < getRandomNumber(0, 5); ++i) {
			supermarket.addCustomer(new Customer(CustomerType.getRandom()));
		}
		supermarket.getCustomers().forEach((Customer customer) -> {
			for (int i = 0; i < getRandomNumber(0, 12); ++i) {
				int randomProductIndex = getRandomNumber(0, supermarket.getProducts().size() - 1);
				Product randomProduct = supermarket.getProducts().get(randomProductIndex);
				int productId = randomProduct.getId();
				int productMaxAmount = randomProduct.getAmount();
				int productAmount = getRandomProductAmount(randomProduct, productMaxAmount);
				Product wantedProduct = supermarket.getProduct(customer.getType(), productId, productAmount);
				if (wantedProduct != null) {
					customer.getBacket().add(wantedProduct);
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
