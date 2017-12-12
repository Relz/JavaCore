package ru.relz.javacore2017.payment;

public class Discount {
	private final int percentage;

	public Discount(int percentage) {
		this.percentage = percentage;
	}
	public int getPercentage() {
		return percentage;
	}
}
