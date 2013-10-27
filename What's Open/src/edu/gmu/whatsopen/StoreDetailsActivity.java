package edu.gmu.whatsopen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class StoreDetailsActivity extends Activity {
	TextView tvTitle, tvLocation, tvTimings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.store_details);
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvLocation = (TextView)findViewById(R.id.tvLocation);
		tvTimings = (TextView)findViewById(R.id.tvTimings);

		Intent intent = getIntent();
		String name = intent.getStringExtra(JsonDiningParser.name);

		if(name!=null){
			tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
			tvTitle.setTextSize(20);
			tvTitle.setText(name);
		}

		if(intent.getStringExtra(JsonDiningParser.location)!=null){
			tvLocation.setTextSize(15);
			tvLocation.setText(intent.getStringExtra(JsonDiningParser.location));
		}

		if(intent.getStringExtra(JsonDiningParser.main_schedules) != null){
			tvTimings.setPadding(0, 10, 0, 0);
			tvTimings.setTextSize(15);
			tvTimings.setText(intent.getStringExtra(JsonDiningParser.main_schedules));
		}

	}


}
