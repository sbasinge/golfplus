package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.solder.core.Veto;

import com.basinc.golfminus.enums.TeeType;

/**
 * TeeSet generated by hbm2java
 */
@Entity
@Veto
@NamedQueries(value = { 
		@NamedQuery(name = "findTeeSetByCourseAndTeeType", query = "select ts from TeeSet ts where ts.course.name = :courseName and ts.teeType = :teeType") }
)
public class TeeSet extends BaseEntity implements Comparable<TeeSet> {

	@JsonIgnore
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", nullable = true)
	private Course course;
	
//	@JsonIgnore
//	@XmlTransient
	@Enumerated(EnumType.STRING)
	private TeeType teeType;
	
	@Column
	private int par = 72;
	
	@Column
	private double courseRating;

	@Column
	private int slopeRating;

	@JsonIgnore
	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true,  fetch = FetchType.LAZY, mappedBy = "teeSet")
	private List<Score> scores = new ArrayList<Score>();
	
	public TeeSet() {
	}

	public TeeSet(TeeType teeType) {
		this.setTeeType(teeType);
	}
	public TeeSet(TeeType teeType,
			double courseRating, int slopeRating) {
		this.setTeeType(teeType);
		this.courseRating = courseRating;
		this.slopeRating = slopeRating;
	}

	public TeeSet(Course course, TeeType teeType,
			double courseRating, int slopeRating) {
		this(teeType,courseRating,slopeRating);
		this.course = course;
		course.addTeeSet(this);
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	public double getCourseRating() {
		return this.courseRating;
	}

	public void setCourseRating(double courseRating) {
		this.courseRating = courseRating;
	}

	public int getSlopeRating() {
		return this.slopeRating;
	}

	public void setSlopeRating(int slopeRating) {
		this.slopeRating = slopeRating;
	}

	public TeeType getTeeType() {
		return teeType;
	}

	public void setTeeType(TeeType teeType) {
		this.teeType = teeType;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public void addScore(Score score) {
		score.setTeeSet(this);
		scores.add(score);
	}
	
	@Transient
	public String getName() {
		return this.getCourse().getName()+" "+this.getTeeType();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeeSet other = (TeeSet) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(TeeSet arg0) {
		return this.getTeeType().getOrder().compareTo(arg0.getTeeType().getOrder());
	}

	public int getPar() {
		return par;
	}

	public void setPar(int par) {
		this.par = par;
	}
}
