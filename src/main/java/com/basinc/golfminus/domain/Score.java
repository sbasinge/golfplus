package com.basinc.golfminus.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.solder.core.Veto;

import com.basinc.golfminus.util.Constants;

@Entity
@Veto
@JsonIgnoreProperties({ "user", "teeTime", "teeSet" })
public class Score extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEESET_ID", nullable = false)
	private TeeSet teeSet;
	
    @Temporal(TemporalType.DATE)
    @NotNull
	private Date date;

    @ManyToOne
	private User user;

    @ManyToOne
	private TeeTime teeTime;

    @Column
    @NotNull
	private Integer grossScore;

    @Column
    @NotNull
	private Integer netScore;
	
    private boolean counter = false;
    
	public Score() {};
	
	public Score(TeeSet teeSet, String dateStr, int grossScore, int netScore) throws ParseException {
		this();
		this.teeSet = teeSet;
		this.date = Constants.DATE_FORMAT.parse(dateStr);
		this.grossScore = grossScore;
		this.netScore = netScore;
		teeSet.addScore(this);
	}
	
	public Score(TeeSet teeSet, String dateStr, User user, int grossScore, int netScore) throws ParseException {
		this(teeSet, dateStr, grossScore, netScore);
		this.user = user;
		teeSet.addScore(this);
	}
	
	public Score(TeeTime teetime, User user) {
		setTeeSet(teetime.getTeeSet());
		setDate(teetime.getDate());
		setUser(user);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getGrossScore() {
		return grossScore;
	}
	public void setGrossScore(Integer grossScore) {
		this.grossScore = grossScore;
	}

	public Integer getNetScore() {
		return netScore;
	}
	public void setNetScore(Integer netScore) {
		this.netScore = netScore;
	}

	public TeeSet getTeeSet() {
		return teeSet;
	}

	public void setTeeSet(TeeSet teeSet) {
		this.teeSet = teeSet;
	}
	
	@Transient
	public double getDifferential() {
		BigDecimal temp = new BigDecimal(((getNetScore()-getTeeSet().getCourseRating())*113)/getTeeSet().getSlopeRating()).setScale(1,BigDecimal.ROUND_HALF_UP);
		return temp.doubleValue();
	}

	public boolean isCounter() {
		return counter;
	}

	public void setCounter(boolean counter) {
		this.counter = counter;
	}

	public TeeTime getTeeTime() {
		return teeTime;
	}

	public void setTeeTime(TeeTime teeTime) {
		this.teeTime = teeTime;
	}

	@Override
	public String toString() {
		return "Score [teeSet=" + teeSet + ", date=" + date + ", user=" + user + ", teeTime=" + teeTime + ", grossScore=" + grossScore
				+ ", netScore=" + netScore + ", counter=" + counter + "]";
	}
	
}
