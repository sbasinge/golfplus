package com.basinc.golfminus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.solder.core.Veto;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findRoleByName", query = "select r from Role r where r.name = :name") }
)
@Veto
public class Role extends BaseEntity {

	public static String ADMIN_ROLE_NAME = "Admin";
	public static String CLUBADMIN_ROLE_NAME = "ClubAdmin";
	public static String MEMBER_ROLE_NAME = "Member";
	public static String GUEST_ROLE_NAME = "Guest";
	public static String TEETIME_ROLE_NAME = "TeeTimeChair";
	
	@Column(unique=true)
	private String name;
	
	public Role() {};
	public Role(String name) {this.setName(name);}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	};
}
