package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-07T18:33:02.560-0500")
@StaticMetamodel(Club.class)
public class Club_ extends BaseEntity_ {
	public static volatile SingularAttribute<Club, String> name;
	public static volatile SingularAttribute<Club, String> websiteUrl;
	public static volatile SingularAttribute<Club, String> rssFeedUrl;
	public static volatile ListAttribute<Club, User> members;
	public static volatile ListAttribute<Club, MembershipRequest> membershipRequests;
}
