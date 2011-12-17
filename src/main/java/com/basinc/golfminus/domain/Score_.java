package com.basinc.golfminus.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-12-17T07:23:08.596-0500")
@StaticMetamodel(Score.class)
public class Score_ extends BaseEntity_ {
	public static volatile SingularAttribute<Score, TeeSet> teeSet;
	public static volatile SingularAttribute<Score, Date> date;
	public static volatile SingularAttribute<Score, User> user;
	public static volatile SingularAttribute<Score, TeeTime> teeTime;
	public static volatile SingularAttribute<Score, Integer> grossScore;
	public static volatile SingularAttribute<Score, Boolean> counter;
	public static volatile SingularAttribute<Score, Integer> adjustedScore;
}
