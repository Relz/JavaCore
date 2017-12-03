package main.java.ru.relz.javacore2017.model.customer.holder;

public class Holder implements HolderInterface {
	private double value = 0;

	public Holder() {}
	public Holder(double value) {
		this.value = value;
	}

	public void increase(double value) {
		this.value += value;
	}

	public void decrease(double value) {
		this.value -= value;
	}

	public double getValue() {
		return value;
	}

	public boolean more(double value) {
		return this.value >= value;
	}
}
