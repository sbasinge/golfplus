package com.basinc.golfminus.enums;

public enum TournamentType {
	BESTBALL("Best Ball"),
	GROSS_SCORE("Gross Score"),
	NET_SCORE("Net Score"),
	SCRAMBLE("Scramble");
	
	private String label;
	
	private TournamentType(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
