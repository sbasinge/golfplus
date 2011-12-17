package com.basinc.golfminus.enums;


public enum State {
	AL("Alabama"),
	AK("Alaska"),
	AZ("Arizona"),
	AR("Arkansas"),
	CA("California"),
	CO("Colorado"),
	CT("Connecticut"),
	DE("Delaware"),
	FL("Florida"),
	GA("Georgia"),
	HI("Hawaii"),
	ID("Idaho"),
	IL("Illinois"),
	IN("Indiana"),
	IA("Iowa"),
	KS("Kansas"),
	KY("Kentucky"),
	LA("Louisiana"),
	ME("Maine"),
	MD("Maryland"),
	MA("Massachusetts"),
	MI("Michigan"),
	MN("Minnesota"),
	MS("Mississippi"),
	MO("Missouri"),
	MT("Montana"),
	NE("Nebraska"),
	NV("Nevada"),
	NH("New Hampshire"),
	NJ("New Jersey"),
	NM("New Mexico"),
	NY("New York"),
	NC("North Carolina"),
	ND("North Dakota"),
	OH("Ohio"),
	OK("Oklahoma"),
	OR("Oregon"),
	PA("Pennsylvania"),
	RI("Rhode Island"),
	SC("South Carolina"),
	TN("Tennessee"),
	TX("Texas"),
	UT("Utah"),
	VT("Vermont"),
	VA("Virginia"),
	WA("Washington"),
	WV("West Virginia"),
	WI("Wisconsin"),
	WY("Wyoming");
	
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
