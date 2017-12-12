package ru.relz.javacore2017.model.customer;

import ru.relz.javacore2017.payment.Bill;

interface Payer {
	boolean pay(Bill bill);
}
