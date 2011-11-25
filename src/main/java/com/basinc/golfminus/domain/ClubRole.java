package com.basinc.golfminus.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.jboss.solder.core.Veto;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findClubRoleByNameAndClub", query = "select cr from ClubRole cr where cr.role.name = :roleName and cr.club.name = :clubName") }
)
@Veto
public class ClubRole extends BaseEntity {

	@OneToOne(cascade={CascadeType.ALL})
	private Role role;
	
	@OneToOne
	private Club club;
	
	public ClubRole() {};
	public ClubRole(Club club, Role role) {
		setClub(club);
		setRole(role);
	}
	
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
