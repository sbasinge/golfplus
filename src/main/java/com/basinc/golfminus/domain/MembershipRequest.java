package com.basinc.golfminus.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.jboss.solder.core.Veto;

@Entity
@Veto
public class MembershipRequest extends BaseEntity {

	private static final long serialVersionUID = 7778805947985040773L;

	public MembershipRequest() {};
	public MembershipRequest(User user, Club club) {
		setUser(user);
		setClub(club);
	}
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Club club;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	
}
