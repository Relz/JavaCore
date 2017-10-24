package main.java.ru.relz.javacore2017.CashDesk;

import main.java.ru.relz.javacore2017.Customer.Customer;

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
