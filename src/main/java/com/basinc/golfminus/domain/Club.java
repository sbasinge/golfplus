package com.basinc.golfminus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.solder.core.Veto;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findClubByName", query = "select c from Club c where c.name = :name") }
)
@Veto
@JsonIgnoreProperties({ "members" })
public class Club extends BaseEntity {

	@Column(unique=true)
	@NotNull
	private String name;

	private String websiteUrl;
	private String rssFeedUrl;

	public Club() {};
	public Club(String name) {this.name=name;}
	
    @ManyToMany(mappedBy="clubs")
    private List<User> members = new ArrayList<User>();

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="club")
    private List<MembershipRequest> membershipRequests = new ArrayList<MembershipRequest>();

//    @OneToMany(cascade={CascadeType.ALL})
//    private List<ClubRole> clubRoles = new ArrayList<ClubRole>();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<MembershipRequest> getMembershipRequests() {
		return membershipRequests;
	}
	public void setMembershipRequests(List<MembershipRequest> membershipRequests) {
		this.membershipRequests = membershipRequests;
	}
	public void addMemberShipRequest(MembershipRequest membershipRequest) {
		getMembershipRequests().add(membershipRequest);
		membershipRequest.getUser().getMembershipRequests().add(membershipRequest);
	}
	public void removeMemberShipRequest(MembershipRequest membershipRequest) {
		getMembershipRequests().remove(membershipRequest);
		membershipRequest.getUser().getMembershipRequests().remove(membershipRequest);
	}
//	public List<ClubRole> getClubRoles() {
//		return clubRoles;
//	}
//	public void setClubRoles(List<ClubRole> clubRoles) {
//		this.clubRoles = clubRoles;
//	}

	public void addClubRole(Role role) {
		ClubRole clubRole = new ClubRole();
		clubRole.setClub(this);
		clubRole.setRole(role);
//		getClubRoles().add(clubRole);
	}
	
	public void addMember(User user) {
		if (user != null) {
			members.add(user);
			user.getClubs().add(this);
		}
	}
//	@Override
//	public String toString() {
//		return "Club [name=" + name + "]";
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Club other = (Club) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public String getRssFeedUrl() {
		return rssFeedUrl;
	}
	public void setRssFeedUrl(String rssFeedUrl) {
		this.rssFeedUrl = rssFeedUrl;
	}

	public List<User> locateMembersWithRole(String roleName) {
		List<User> retVal = new ArrayList<User>();
		for (User user : getMembers()) {
			if (user.hasClubRole(roleName)) {
				retVal.add(user);
			}
		}
		return retVal;
	}
}
