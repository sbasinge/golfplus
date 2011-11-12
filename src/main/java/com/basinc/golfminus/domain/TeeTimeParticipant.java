package com.basinc.golfminus.domain;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.seam.solder.core.Veto;

@Entity
@Veto
@JsonIgnoreProperties({ "teetime" })
public class TeeTimeParticipant extends BaseEntity {

	@OneToOne private TeeTime teetime;
	@OneToOne private User user;
	@OneToOne(cascade=CascadeType.ALL) private Score score;
	
	private BigDecimal courseIndex;
	private BigDecimal bestBallIndex;
	
	public TeeTimeParticipant() {};

	public TeeTimeParticipant(TeeTime teetime, User user) {
		setTeetime(teetime);
		setUser(user);
		calculateCourseIndex();
	};

	public void calculateCourseIndex() {
		if (getTeetime().getTeeSet() != null) {
			BigDecimal handicap = getUser().getHandicap();
			if (handicap != null) {
				BigDecimal temp = handicap.multiply(new BigDecimal(teetime.getTeeSet().getSlopeRating()));
				BigDecimal index = temp.divideToIntegralValue(new BigDecimal(113), MathContext.DECIMAL32);
				setCourseIndex(index);
				setBestBallIndex(index.multiply(new BigDecimal(0.9), MathContext.DECIMAL32));
			}
		}
	}

	public TeeTime getTeetime() {
		return teetime;
	}
	public void setTeetime(TeeTime teetime) {
		this.teetime = teetime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getCourseIndex() {
		return courseIndex;
	}

	public void setCourseIndex(BigDecimal courseIndex) {
		this.courseIndex = courseIndex;
	}

	public BigDecimal getBestBallIndex() {
		return bestBallIndex;
	}

	public void setBestBallIndex(BigDecimal bestBallIndex) {
		this.bestBallIndex = bestBallIndex;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	public void addScore(Integer grossScore, Integer netScore) {
		Score score = new Score();
		if (getScore() != null) {
			score = getScore();
		}
		score.setDate(getTeetime().getDate());
		score.setGrossScore(grossScore);
		score.setNetScore(netScore);
		score.setTeeTime(getTeetime());
		score.setTeeSet(getTeetime().getTeeSet());
		score.setUser(getUser());
		setScore(score);
	}
	
	public Integer getNetScore() {
		if (getScore()==null || getScore().getGrossScore()==null) 
			return null;
		int grossScore = getScore().getGrossScore().intValue();
		int courseIndex = getCourseIndex().intValue();
		int total = grossScore - courseIndex;
		return total;
	}
	
	public Integer getScoreToPar() {
		int par = getTeetime().getTeeSet().getPar();
		if (getNetScore() == null)
			return null;
		int total = getNetScore() - par;
		return total;
	}
}
