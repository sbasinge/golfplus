package com.basinc.golfminus.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-12-17T09:24:38.551-0500")
@StaticMetamodel(Handicap.class)
public class Handicap_ extends BaseEntity_ {
	public static volatile SingularAttribute<Handicap, User> user;
	public static volatile SingularAttribute<Handicap, BigDecimal> handicap;
	public static volatile SingularAttribute<Handicap, Date> date;
}
