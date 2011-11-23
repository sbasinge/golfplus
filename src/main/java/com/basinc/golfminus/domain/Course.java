package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.seam.solder.core.Veto;

@Entity
@Veto
@JsonIgnoreProperties({ "facility" })
public class Course extends BaseEntity {

	@Column
	@NotNull
	private String name;

//	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID", nullable = true)
	private Facility facility;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<TeeSet> teeSets = new ArrayList<TeeSet>();
	
	public Course() {};
	public Course(String name) { this.name=name;};
	public Course(String name, TeeSet tees) { 
		this(name);
		addTeeSet(tees);
	};
	
	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<TeeSet> getTeeSets() {
		return teeSets;
	}
	public void setTeeSets(List<TeeSet> teeSets) {
		this.teeSets = teeSets;
	}
	
	public void addTeeSet(TeeSet teeSet) {
		teeSet.setCourse(this);
		teeSets.add(teeSet);
	}
}
