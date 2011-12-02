package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-12-02T15:02:57.942-0500")
@StaticMetamodel(Course.class)
public class Course_ extends BaseEntity_ {
	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, Facility> facility;
	public static volatile ListAttribute<Course, TeeSet> teeSets;
}
