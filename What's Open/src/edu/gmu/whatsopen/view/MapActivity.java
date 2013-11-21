package edu.gmu.whatsopen.view;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.gmu.whatsopen.R;
import edu.gmu.whatsopen.parser.JsonParser;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	GoogleMap map;
	LocationClient mLocationClient;
	Location userLocation;
	LatLng storeLocation;
	String storeName;
	String storeTiming;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_test);
		mLocationClient = new LocationClient(this, this, this);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setIndoorEnabled(true);
		map.setBuildingsEnabled(true);
		map.setMyLocationEnabled(true);
		InitStore();
	}
	
	public void InitStore(){
		Intent i = getIntent();
		double lat = i.getDoubleExtra(JsonParser.latitude, 0);
		double lng = i.getDoubleExtra(JsonParser.longtitude, 0);
		storeName = i.getStringExtra(JsonParser.name);
		storeTiming = i.getStringExtra(JsonParser.main_schedules);
		storeLocation = new LatLng(lat,lng);
	}

	private void plot(){
		userLocation = mLocationClient.getLastLocation();
		if(userLocation!=null && storeLocation!=null){
			LatLng userLatLng = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
			
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			builder.include(userLatLng)
			.include(storeLocation);
			LatLngBounds bounds = builder.build();
			map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,75));
			
			map.addMarker(new MarkerOptions()
			.title(storeName)
			.snippet(storeTiming)
			.position(storeLocation));
		}
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.e("connection", " Connection was failed");
	}
	
	@Override
	public void onConnected(Bundle arg0) {
		Log.e("connection", "Signal was connected");
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		plot();
	}

	@Override
	public void onDisconnected() {
		Log.e("connection", "Signal was disconnected");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		mLocationClient.disconnect();
		super.onStop();
	}


	//TODO: get the lat and long of all stores
	//TODO: add it to the JSON
	//TODO: showAll Map
	//TODO: fix the snippet to show only the timings of that day


}
