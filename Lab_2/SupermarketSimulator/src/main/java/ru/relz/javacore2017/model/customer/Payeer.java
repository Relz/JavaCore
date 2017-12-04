package ru.relz.javacore2017.model.customer;

import ru.relz.javacore2017.payment.Bill;

public interface Payeer {
	boolean pay(Bill bill);
}
