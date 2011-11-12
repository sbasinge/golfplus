package com.basinc.golfminus.enums;


public enum State {
	OH("Ohio"),
	PA("Pennsylvania"),
	SC("South Carolina"),
	WV("West Virginia");
	
	private String name;
	
	private State(String name) {
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
//	@Override
//	public String toString() {
//		return new ToStringBuilder(this).appendSuper(super.toString())
//				.append("name", this.getName())
//				.toString();
//	}

}
