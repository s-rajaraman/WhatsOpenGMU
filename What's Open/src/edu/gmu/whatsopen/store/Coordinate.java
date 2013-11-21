package edu.gmu.whatsopen.store;

import android.util.Log;

public class Coordinate {
	private double lat;
	private double lng;

	public Coordinate(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	public double getLatitude(){
		return lat;
	}
	public double getLongitude(){
		return lng;
	}
	public void setLatitdue(double lat){
		this.lat = lat;
	}
	public void setLongitude(double lng){
		this.lng = lng;
	}

}
