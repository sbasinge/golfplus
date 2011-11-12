package com.basinc.golfminus.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


//@Entity
@Embeddable
public class GeoLocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double lat;
	double lng;
	
	public GeoLocation() {};
	public GeoLocation(double lat, double lng) {this.lat=lat; this.lng = lng;};
	
	
	@Temporal(TemporalType.DATE)
	Date lastGeoLookup;

	public Date getLastGeoLookup() {
		return lastGeoLookup;
	}

	public void setLastGeoLookup(Date lastGeoLookup) {
		this.lastGeoLookup = lastGeoLookup;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
}
