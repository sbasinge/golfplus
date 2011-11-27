package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.solder.core.Veto;

@Entity
@Veto
@JsonIgnoreProperties({ "tournament" })
public class TeeTime extends BaseEntity {
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	@NotNull
	private Course course;
	
	@ManyToOne
	private TeeSet teeSet;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<TeeTimeParticipant> participants = new ArrayList<TeeTimeParticipant>();

//	@OneToMany(cascade=CascadeType.ALL,mappedBy="teeTime")
//	private List<Score> scores = new ArrayList<Score>();

    @ManyToOne
	private Tournament tournament;

	@Column
	private boolean notificationOn;
	
	private int numPlayers;
	
	@OneToOne
	private User organizer;
	
	public TeeTime() {}
	
	
	public TeeTime(Date date, TeeSet teeSet, Course course, List<User> players, boolean notificationOn, int numPlayers) {
		super();
		this.date = date;
		this.course = course;
		this.teeSet = teeSet;
		this.notificationOn = notificationOn;
		this.numPlayers = numPlayers;
		for (User user : players) {
			TeeTimeParticipant participant = new TeeTimeParticipant(this, user);
			getParticipants().add(participant);
			user.getTeeTimes().add(this);
		}

	}


	public boolean isNotificationOn() {
		return notificationOn;
	}
	public void setNotificationOn(boolean notificationOn) {
		this.notificationOn = notificationOn;
	}
	public TeeSet getTeeSet() {
		return teeSet;
	}
	public void setTeeSet(TeeSet teeSet) {
		this.teeSet = teeSet;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}


	public int getNumPlayers() {
		return numPlayers;
	}


	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void signUp(User user) {
		if (participants.size() < numPlayers) {
			participants.add(new TeeTimeParticipant(this,user));
			user.getTeeTimes().add(this);
		} else {
			throw new IllegalStateException("All available spots are taken.");
		}
	}
	
	@Transient
	public int getNumOpenSpots() {
		int retVal = 0;
		retVal = getNumPlayers() - participants.size();
		return retVal;
	}


//	public List<Score> getScores() {
//		return scores;
//	}
//
//	public void setScores(List<Score> scores) {
//		this.scores = scores;
//	}
	
//	public void addScore(Score score) {
//		score.setTeeTime(this);
//		getScores().add(score);
//	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public List<TeeTimeParticipant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<TeeTimeParticipant> participants) {
		this.participants = participants;
	}
	
	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}


	public Tournament getTournament() {
		return tournament;
	}


	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	public boolean hasScores() {
		boolean retVal = false;
		for (TeeTimeParticipant participant : getParticipants()) {
			if (participant.getScore() != null && participant.getScore().getGrossScore() != null) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}


	public boolean isUserSignedUp(User currentUser) {
		boolean retVal = false;
		for (TeeTimeParticipant participant : getParticipants()) {
			if (participant.getUser().equals(currentUser)) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	
	public boolean isInTheFuture() {
		boolean retVal = true;
//		if (getDate().after(Calendar.getInstance().getTime())) {
//			retVal = true;
//		}
		return retVal;
	}
	
	public void removeParticipant(User user) {
		TeeTimeParticipant participant = null;
		for (TeeTimeParticipant participant2 : getParticipants()) {
			if (participant2.getUser().equals(user)) {
				participant = participant2;
				break;
			}
		}
		if (participant != null) {
			TeeTime teetime = participant.getTeetime();
			user.getTeeTimes().remove(teetime);
			participants.remove(participant);
		}
	}
}
