package com.basinc.golfminus.domain;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.jboss.solder.core.Veto;

import com.basinc.golfminus.enums.State;

@Entity
@Veto
public class Address extends BaseEntity {
	private static final long serialVersionUID = -6546490341498624723L;
	

	String addressLine1;
	String addressLine2;
	String city;
	String zipCode;

	@Enumerated(EnumType.STRING)
	State state;
	
	@Embedded
	GeoLocation geoLocation;

	public Address() {
	}

	public Address(String addressLine1, String city, State state, String zip, double lat, double lng) {
		this.addressLine1 = addressLine1;
		this.city = city;
		this.state = state;
		this.geoLocation = new GeoLocation();
		this.geoLocation.lat = lat;
		this.geoLocation.lng = lng;
		this.geoLocation.lastGeoLookup = Calendar.getInstance().getTime();
		this.zipCode = zip;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

//	@Override
//	public String toString() {
//		return new ToStringBuilder(this).appendSuper(super.toString())
//				.append("id", this.getId())
//				.append("addressLine1", this.getAddressLine1())
//				.append("addressLine2", this.getAddressLine2())
//				.append("city", this.getCity())
//				.append("state", this.getState())
//				.append("zip", this.getZipCode())
//				.toString();
//	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation location) {
		this.geoLocation = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
