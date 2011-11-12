package com.basinc.golfminus.domain;

import com.basinc.golfminus.enums.State;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-06T17:22:39.738-0400")
@StaticMetamodel(Address.class)
public class Address_ extends BaseEntity_ {
	public static volatile SingularAttribute<Address, String> addressLine1;
	public static volatile SingularAttribute<Address, String> addressLine2;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> zipCode;
	public static volatile SingularAttribute<Address, State> state;
	public static volatile SingularAttribute<Address, GeoLocation> geoLocation;
}
