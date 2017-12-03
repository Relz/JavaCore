package main.java.ru.relz.javacore2017.cash_desk;

import main.java.ru.relz.javacore2017.model.customer.Customer;

import java.util.Iterator;

public interface CashDesk {
	int getQueueSize();
	boolean isOpened();
	void open();
	void close();
	void addCustomerToQueue(Customer customer);
	void removeCustomerFromQueue(Iterator<Customer> customerIterator, Customer customer);
	void processQueue(int customerCount);
}
