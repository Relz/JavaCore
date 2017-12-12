package ru.relz.javacore2017.model.cash_desk;

import ru.relz.javacore2017.supermarket.Supermarket;

public class NamedCashDesk extends CashDesk implements Named {
	public NamedCashDesk(Supermarket supermarket, String name) {
		super(supermarket);
		this.name = name;
	}

	private final String name;
	public String getName() {
		return name;
	}

	@Override
	protected void closeMessage() {
		System.out.printf("Касса %s закрылась\n", name);
	}

	@Override
	protected void addCustomerToQueueMessage() {
		System.out.printf("встал в очередь на кассу %s\n", name);
	}

	@Override
	protected void removeCustomerFromQueueMessage() {
		System.out.printf("покинул кассу %s\n", name);
	}
}
