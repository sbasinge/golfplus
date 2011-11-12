package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-07T19:35:20.716-0500")
@StaticMetamodel(NotificationPreference.class)
public class NotificationPreference_ extends BaseEntity_ {
	public static volatile SingularAttribute<NotificationPreference, Boolean> notifyByEmail;
	public static volatile SingularAttribute<NotificationPreference, Boolean> notifyByText;
	public static volatile SingularAttribute<NotificationPreference, Boolean> notifyOnSite;
}
