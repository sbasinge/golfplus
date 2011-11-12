package com.basinc.golfminus.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-06T17:22:39.800-0400")
@StaticMetamodel(GeoLocation.class)
public class GeoLocation_ {
	public static volatile SingularAttribute<GeoLocation, Double> lat;
	public static volatile SingularAttribute<GeoLocation, Double> lng;
	public static volatile SingularAttribute<GeoLocation, Date> lastGeoLookup;
}
