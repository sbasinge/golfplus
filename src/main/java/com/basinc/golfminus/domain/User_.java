package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-12-17T06:48:26.756-0500")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile ListAttribute<User, Club> clubs;
	public static volatile ListAttribute<User, MembershipRequest> membershipRequests;
	public static volatile ListAttribute<User, ClubRole> clubRoles;
	public static volatile ListAttribute<User, Score> scores;
	public static volatile ListAttribute<User, Handicap> handicapHistory;
	public static volatile SingularAttribute<User, NotificationPreference> newMemberShipNotificationType;
	public static volatile SingularAttribute<User, NotificationPreference> handicapCalculateNotificationType;
	public static volatile SingularAttribute<User, NotificationPreference> newTeeTimeNotificationType;
	public static volatile SingularAttribute<User, NotificationPreference> teeTimeFullNotificationType;
}
