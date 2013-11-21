package edu.gmu.whatsopen.view;

import edu.gmu.whatsopen.R;
import edu.gmu.whatsopen.parser.JsonParser;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

public class JSONTesterActivity extends Activity{
	TextView tv;
	JsonParser jp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		tv = (TextView) this.findViewById(R.id.tvResult);
		
	}

	public void updateTV(TextView tv,String x){
		String current = tv.getText().toString()+x;
		tv.setText(current);
		
	}

}
