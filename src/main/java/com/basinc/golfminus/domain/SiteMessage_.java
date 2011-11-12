package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-26T07:26:01.915-0400")
@StaticMetamodel(SiteMessage.class)
public class SiteMessage_ extends BaseEntity_ {
	public static volatile SingularAttribute<SiteMessage, User> user;
	public static volatile SingularAttribute<SiteMessage, Boolean> messageRead;
	public static volatile SingularAttribute<SiteMessage, String> messageText;
}
