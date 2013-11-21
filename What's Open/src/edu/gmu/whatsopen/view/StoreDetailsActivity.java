package edu.gmu.whatsopen.view;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import edu.gmu.whatsopen.R;
import edu.gmu.whatsopen.parser.JsonParser;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoreDetailsActivity extends Activity implements OnClickListener {
	TextView tvTitle, tvLocation, tvTimings;

	Button map;

	/**
	 * Initializes TextViews with store name, location, timings
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.store_details);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvLocation = (TextView)findViewById(R.id.tvLocation);
		tvTimings = (TextView)findViewById(R.id.tvTimings);
		map = (Button)findViewById(R.id.bMap);
		map.setOnClickListener(this);


		Intent intent = getIntent();
		String name = intent.getStringExtra(JsonParser.name);

		if(name!=null){
			tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
			tvTitle.setTextSize(20);
			tvTitle.setText(name);
		}

		if(intent.getStringExtra(JsonParser.location)!=null){
			tvLocation.setTextSize(15);
			tvLocation.setText(intent.getStringExtra(JsonParser.location));
		}

		if(intent.getStringExtra(JsonParser.main_schedules) != null){
			tvTimings.setPadding(0, 10, 0, 0);
			tvTimings.setTextSize(15);
			tvTimings.setText(intent.getStringExtra(JsonParser.main_schedules));
		}

	}

	@Override
	public void onClick(View v) {
		if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)==ConnectionResult.SUCCESS){
			Intent i = new Intent("android.intent.action.MAP_ACTIVITY");
			Log.e("edu.gmu.whatsopen", "Starting Activity");
			Intent current = getIntent();
			double lat = current.getDoubleExtra(JsonParser.latitude, 0);
			double lng = current.getDoubleExtra(JsonParser.longtitude, 0);
			String storeName = current.getStringExtra(JsonParser.name);
			String storeHours = current.getStringExtra(JsonParser.main_schedules);
			i.putExtra(JsonParser.name,storeName);
			i.putExtra(JsonParser.main_schedules, storeHours);
			i.putExtra(JsonParser.latitude, lat);
			i.putExtra(JsonParser.longtitude, lng);
			startActivity(i);
			}
		else{
			Toast.makeText(this, "Google Play Services are not found", Toast.LENGTH_LONG).show();
		}
	}
}