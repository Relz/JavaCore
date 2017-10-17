package main.java.ru.relz.javacore2017;

public enum ProductType {
	None,
	Packed,
	Bulk;

	static ProductType toProductType(int value) {
		if (value == 1) {
			return ProductType.Packed;
		} else if (value == 2) {
			return ProductType.Bulk;
		} else {
			return ProductType.None;
		}
	}
}
