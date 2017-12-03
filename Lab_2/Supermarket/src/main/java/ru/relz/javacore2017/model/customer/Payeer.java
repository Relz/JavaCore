package main.java.ru.relz.javacore2017.model.customer;

import main.java.ru.relz.javacore2017.payment.Bill;

public interface Payeer {
	boolean pay(Bill bill);
}
