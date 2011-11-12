package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.jboss.seam.solder.core.Veto;

import com.basinc.golfminus.enums.TournamentType;

@Entity
@Veto
public class Tournament extends BaseEntity {

	private String name;

	@Enumerated(EnumType.STRING)
	private TournamentType type;
	
	@OneToMany(mappedBy="tournament")
	@OrderBy("date ASC")
	private List<TeeTime> teeTimes = new ArrayList<TeeTime>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TeeTime> getTeeTimes() {
		return teeTimes;
	}
	public void setTeeTimes(List<TeeTime> teeTimes) {
		this.teeTimes = teeTimes;
	}
	
	public void addTeeTime(TeeTime teeTime) {
		teeTime.setTournament(this);
		getTeeTimes().add(teeTime);
	}
	
	public int getNumTeeTimes() {
		return getTeeTimes().size();
	}
	
	public boolean hasScores() {
		boolean retVal = false;
		for (TeeTime teetime : getTeeTimes()) {
			if (teetime.hasScores()) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	public TournamentType getType() {
		return type;
	}
	public void setType(TournamentType type) {
		this.type = type;
	}
	
}
