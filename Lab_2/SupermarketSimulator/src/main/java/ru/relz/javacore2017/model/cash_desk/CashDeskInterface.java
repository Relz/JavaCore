package ru.relz.javacore2017.model.cash_desk;

import ru.relz.javacore2017.model.customer.Customer;

import java.util.Iterator;

public interface CashDeskInterface extends LoggingCashDesk, Openable, Closeable {
	int getQueueSize();
	void addCustomerToQueue(Customer customer);
	void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer);
	void processQueue(int customerCount);
}
