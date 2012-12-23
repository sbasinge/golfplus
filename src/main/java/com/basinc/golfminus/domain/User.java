package com.basinc.golfminus.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.jboss.solder.core.Veto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Veto
public class User implements Serializable {
    private static final long serialVersionUID = -602733026033932730L;
    
    @Transient
    private static Logger log = LoggerFactory.getLogger(User.class);
    
    @Id
    @NotNull
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\w*$", message = "not a valid username")
    private String username;
    
    @NotNull
    @Size(min = 5, max = 15)
    private String password;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    
    @NotNull
    @Email
    private String email;

//    @Pattern(regexp="^(1?(-?\\d{3})-?)?(\\d{3})(-?\\d{4})$")
    @Column(nullable=true)
    private String phone;

	@JsonIgnore
	@XmlTransient
    @ManyToMany
    private List<Club> clubs = new ArrayList<Club>();

	@JsonIgnore
	@XmlTransient
    @OneToMany(mappedBy="user")
    private List<MembershipRequest> membershipRequests = new ArrayList<MembershipRequest>();

	@JsonIgnore
	@XmlTransient
    @ManyToMany
    private List<ClubRole> clubRoles = new ArrayList<ClubRole>();
    
	@JsonIgnore
	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@OrderBy("date desc")
    private List<Score> scores = new ArrayList<Score>();

	@JsonIgnore
	@XmlTransient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="user")
    @OrderBy("date DESC")
    private List<Handicap> handicapHistory = new ArrayList<Handicap>();

	@JsonIgnore
	@XmlTransient
	@OneToOne(cascade=CascadeType.ALL)
	private NotificationPreference newMemberShipNotificationType = new NotificationPreference();

	@JsonIgnore
	@XmlTransient
	@OneToOne(cascade=CascadeType.ALL)
	private NotificationPreference handicapCalculateNotificationType = new NotificationPreference();

	@JsonIgnore
	@XmlTransient
	@OneToOne(cascade=CascadeType.ALL)
	private NotificationPreference newTeeTimeNotificationType = new NotificationPreference();

	@JsonIgnore
	@XmlTransient
	@OneToOne(cascade=CascadeType.ALL)
	private NotificationPreference teeTimeFullNotificationType = new NotificationPreference();

    public User() {
    	newMemberShipNotificationType.setNotifyOnSite(true);
    	handicapCalculateNotificationType.setNotifyOnSite(true);
    	newTeeTimeNotificationType.setNotifyOnSite(true);
    	teeTimeFullNotificationType.setNotifyOnSite(true);
    }

    public User(final String name, final String username, final String email) {
    	this();
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public User(final String name, final String username, final String email, final String password) {
        this(name, username, email);
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Transient
    public String getEmailWithName() {
        return name + " <" + email + ">";
    }

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	public void addMembership(Club club) {
		if (club != null) {
			club.addMember(this);
			clubs.add(club);
		}
	}
	
	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public void addScore(Score score) {
		score.setUser(this);
		scores.add(score);
	}
	
	public int determineNumberOfScoresToUse(List<Score> eligibleScores) {
		//get up to last 20 scores
		// if num scores 5 or 6 use lowest differential
		//     7-8 use lowest 2
		//     9-10 use 3
		//     11-12 use 4
		//     13-14 use 5
		//     15-16 use 6
		//     17 use 7
		//     18 use 8
		//     19 use 9
		//     20 use 10
		// average the differentials
		// multiply by .96
		// truncate after 1 decimal place
		int numElligibleScores = eligibleScores.size();
		int scoresToUse = 0;
		if (numElligibleScores < 5) {
			//cannot calc handicap
		} else if (numElligibleScores >= 5 && numElligibleScores < 7) {
			scoresToUse = 1;
		} else if (numElligibleScores >= 7 && numElligibleScores < 9) {
			scoresToUse = 2;
		} else if (numElligibleScores >= 9 && numElligibleScores < 11) {
			scoresToUse = 3;
		} else if (numElligibleScores >= 11 && numElligibleScores < 13) {
			scoresToUse = 4;
		} else if (numElligibleScores >= 13 && numElligibleScores < 15) {
			scoresToUse = 5;
		} else if (numElligibleScores >= 15 && numElligibleScores < 17) {
			scoresToUse = 6;
		} else if (numElligibleScores == 17) {
			scoresToUse = 7;
		} else if (numElligibleScores == 18) {
			scoresToUse = 8;
		} else if (numElligibleScores == 19) {
			scoresToUse = 9;
		} else if (numElligibleScores == 20) {
			scoresToUse = 10;
		}
		log.info("Found {} scores to use.",scoresToUse);
		return scoresToUse;
	}

	public void sortEligibleScoresByDifferential(List<Score> eligibleScores) {
		//sort by differential asc, lowest first
		Collections.sort(eligibleScores, new Comparator<Score>() {
		    public int compare(Score o1, Score o2) {
		    	double score1Diff = o1.getDifferential();
		    	double score2Diff = o2.getDifferential();
		    	int result = score1Diff > score2Diff ? 1 : (score1Diff < score2Diff ? -1 : 0); 
		        return result;
		    }});
	}

	public boolean calculateHandicap() {
		log.info("Calculating handicap for {}",this.username);
		clearAllCounters();
		boolean handicapCalculated = false;
		BigDecimal handicap = null;
		List<Score> eligibleScores = getLast20Scores();
		int scoresToUse = determineNumberOfScoresToUse(eligibleScores);
		if (scoresToUse > 0) {
			sortEligibleScoresByDifferential(eligibleScores);
			List<Score> bestDifferentials = eligibleScores.subList(0, scoresToUse);
			double totalDifferential = 0;
			for (Score score : bestDifferentials) {
				totalDifferential += score.getDifferential();
				score.setCounter(true);
			}
			handicap = new BigDecimal((totalDifferential / scoresToUse)*0.96).setScale(1,BigDecimal.ROUND_DOWN);
			Handicap newHandicap = new Handicap(handicap, Calendar.getInstance().getTime());
			newHandicap.setUser(this);
//			newHandicap.save();
			handicapHistory.add(newHandicap);
//			this.save();
			handicapCalculated = true;
		}
		return handicapCalculated;
	}

	private void clearAllCounters() {
		for (Score score : getScores()) {
			if (score.isCounter())
				score.setCounter(false);
		}
	}

	public BigDecimal getHandicap() {
		return getHandicapHistory().size()>0?getHandicapHistory().get(0).getHandicap():null;
	}

	public List<MembershipRequest> getMembershipRequests() {
		return membershipRequests;
	}

	public void setMembershipRequests(List<MembershipRequest> membershipRequests) {
		this.membershipRequests = membershipRequests;
	}

	public List<ClubRole> getClubRoles() {
		return clubRoles;
	}

	public void setClubRoles(List<ClubRole> clubRoles) {
		this.clubRoles = clubRoles;
	}

	public void addClubRole(ClubRole clubRole) {
		getClubRoles().add(clubRole);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Transient
	public List<Score> getLast20Scores() {
		List<Score> sortedDescScores = getScores();
		Collections.sort(sortedDescScores, new Comparator<Score>() {
		    public int compare(Score o1, Score o2) {
		        return o2.getDate().compareTo(o1.getDate());
		    }});
		List<Score> temp = sortedDescScores.subList(0, Math.min(20, sortedDescScores.size()));
		
		//now sort by date asc
		Collections.sort(temp, new Comparator<Score>() {
		    public int compare(Score o1, Score o2) {
		        return o1.getDate().compareTo(o2.getDate());
		    }});
		return temp;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Handicap> getHandicapHistory() {
		return handicapHistory;
	}

	public void setHandicapHistory(List<Handicap> handicapHistory) {
		this.handicapHistory = handicapHistory;
	}
	
	@Transient
	public String getLast20ScoreRowClasses() {
		String retVal = "";
		for (Score score : getLast20Scores()) {
			if (score.isCounter())
				retVal += "counter,";
			else
				retVal += ",";
		}
		retVal += ",";
		log.warn("Returning rowClasses {}",retVal);
		return retVal;
	}

//	public List<TeeTime> getTeeTimes() {
//		return teeTimes;
//	}
//
//	public void setTeeTimes(List<TeeTime> teeTimes) {
//		this.teeTimes = teeTimes;
//	}

	public NotificationPreference getNewMemberShipNotificationType() {
		return newMemberShipNotificationType;
	}

	public void setNewMemberShipNotificationType(NotificationPreference newMemberShipNotificationType) {
		this.newMemberShipNotificationType = newMemberShipNotificationType;
	}

	public NotificationPreference getHandicapCalculateNotificationType() {
		return handicapCalculateNotificationType;
	}

	public void setHandicapCalculateNotificationType(NotificationPreference handicapCalculateNotificationType) {
		this.handicapCalculateNotificationType = handicapCalculateNotificationType;
	}

	public NotificationPreference getNewTeeTimeNotificationType() {
		return newTeeTimeNotificationType;
	}

	public void setNewTeeTimeNotificationType(NotificationPreference newTeeTimeNotificationType) {
		this.newTeeTimeNotificationType = newTeeTimeNotificationType;
	}

	public NotificationPreference getTeeTimeFullNotificationType() {
		return teeTimeFullNotificationType;
	}

	public void setTeeTimeFullNotificationType(NotificationPreference teeTimeFullNotificationType) {
		this.teeTimeFullNotificationType = teeTimeFullNotificationType;
	}
	
	public boolean hasClubRole(String roleName) {
		boolean retVal = false;
		if (roleName != null) {
			for (ClubRole clubRole: getClubRoles()) {
				if (roleName.equalsIgnoreCase(clubRole.getRole().getName())) {
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}
	
	public Score getLastScore() {
		return getScores().size() > 0 ? getScores().get(0) : new Score();
	}
}
