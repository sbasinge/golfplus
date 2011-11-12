package com.basinc.golfminus.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-04T07:46:21.288-0400")
@StaticMetamodel(Facility.class)
public class Facility_ extends BaseEntity_ {
	public static volatile SingularAttribute<Facility, String> name;
	public static volatile SingularAttribute<Facility, Address> address;
	public static volatile SingularAttribute<Facility, String> phone;
	public static volatile ListAttribute<Facility, Course> courses;
}
