package main.java.ru.relz.javacore2017.Payment;

public class Bill extends ProductSummator{
	private Discount discount = new Discount(0);
	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@Override
	public double calculateTotalAmount() {
		double totalAmountWithoutDiscount = super.calculateTotalAmount();

		return totalAmountWithoutDiscount - totalAmountWithoutDiscount * discount.getPercentage() / 100;
	}
}
