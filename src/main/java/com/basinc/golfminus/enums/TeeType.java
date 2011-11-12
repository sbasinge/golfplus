package com.basinc.golfminus.enums;

public enum TeeType {
	BLUE(20),
	WHITE(40),
	RED(100);
	
	private Integer order;
	
	private TeeType(int order) {
		setOrder(order);
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
