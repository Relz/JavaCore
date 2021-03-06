package ru.relz.javacore2017.model.product;

import java.util.HashMap;

public enum ProductType {
	Packed(1),
	Bulk(2);

	private final static HashMap<Integer, ProductType> integersToProductType = new HashMap<>() {{
		put(Packed.code, Packed);
		put(Bulk.code, Bulk);
	}};
	private final int code;

	ProductType(final int code) {
		this.code = code;
	}
	public static ProductType createFromInteger(int code) {
		return integersToProductType.get(code);
	}
}
