package main.java.ru.relz.javacore2017.Product;

public enum ProductType {
	None,
	Packed,
	Bulk;

	public static ProductType toProductType(int value) {
		if (value == 1) {
			return ProductType.Packed;
		} else if (value == 2) {
			return ProductType.Bulk;
		} else {
			return ProductType.None;
		}
	}
}
