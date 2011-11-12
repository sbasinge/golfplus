package com.basinc.golfminus.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-28T09:54:52.875-0400")
@StaticMetamodel(TeeTime.class)
public class TeeTime_ extends BaseEntity_ {
	public static volatile SingularAttribute<TeeTime, Date> date;
	public static volatile SingularAttribute<TeeTime, Course> course;
	public static volatile SingularAttribute<TeeTime, TeeSet> teeSet;
	public static volatile ListAttribute<TeeTime, TeeTimeParticipant> participants;
	public static volatile SingularAttribute<TeeTime, Tournament> tournament;
	public static volatile SingularAttribute<TeeTime, Boolean> notificationOn;
	public static volatile SingularAttribute<TeeTime, Integer> numPlayers;
	public static volatile SingularAttribute<TeeTime, User> organizer;
}
