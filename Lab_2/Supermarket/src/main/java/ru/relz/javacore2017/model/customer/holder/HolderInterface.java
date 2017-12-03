package main.java.ru.relz.javacore2017.model.customer.holder;

interface HolderInterface {
	/**
	 * Increases holder value with specified parameter value
	 *
	 * @param value value for increasing}
	 */
	void increase(double value);

	/**
	 * Decreases holder value with specified parameter value
	 *
	 * @param value value for decreasing}
	 */
	void decrease(double value);

	/**
	 * Returns value
	 *
	 * @return value
	 */
	double getValue();
}
