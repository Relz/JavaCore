package main.java.ru.relz.javacore2017;

class Discount {
	Discount(int percentage) {
		_percentage = percentage;
	}

	private int _percentage = 0;
	int getPercentage() {
		return _percentage;
	}

	void setPercentage(int percentage) {
		_percentage = percentage;
	}
}
