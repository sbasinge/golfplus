package com.basinc.golfminus.domain;

import com.basinc.golfminus.enums.TeeType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-22T10:56:42.429-0500")
@StaticMetamodel(TeeSet.class)
public class TeeSet_ extends BaseEntity_ {
	public static volatile SingularAttribute<TeeSet, Course> course;
	public static volatile SingularAttribute<TeeSet, TeeType> teeType;
	public static volatile SingularAttribute<TeeSet, Integer> par;
	public static volatile SingularAttribute<TeeSet, Double> courseRating;
	public static volatile SingularAttribute<TeeSet, Integer> slopeRating;
	public static volatile ListAttribute<TeeSet, Score> scores;
}
