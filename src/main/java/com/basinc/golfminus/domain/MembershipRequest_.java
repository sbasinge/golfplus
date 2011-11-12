package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-06T17:24:26.192-0400")
@StaticMetamodel(MembershipRequest.class)
public class MembershipRequest_ extends BaseEntity_ {
	public static volatile SingularAttribute<MembershipRequest, User> user;
	public static volatile SingularAttribute<MembershipRequest, Club> club;
}
