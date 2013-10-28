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

		//JsonParser jp = new JsonParser(this);

		Time now = new Time();
		now.setToNow();
		String currentTime ="\n\nWeekday "+convertWeekday(now.weekDay);
		currentTime+="Hour now:"+ now.hour;
		currentTime += " Minute now:"+ now.minute;

		Time opening = new Time();
		opening.hour = 7;
		opening.weekDay = now.weekDay;
		Time closing = new Time();
		closing.hour = 4;
		closing.weekDay = now.weekDay+1;

		if(opening.weekDay!=closing.weekDay){
			if(opening.weekDay==now.weekDay){
				if(getMinutes(opening)<=getMinutes(now)){
					currentTime += "The current time is past or equal to opening time";
				}
				else{
					currentTime += "The current time is before the opening time";
				}
			}
			else if(closing.weekDay==now.weekDay){
				if(getMinutes(closing)>getMinutes(now)){
					currentTime += "The current time is before closing time";
				}
				else{
					currentTime += "The current time is after closing time";
				}
			}
		}


		updateTV(tv,currentTime);
	}

	public void updateTV(TextView tv,String x){
		String current = tv.getText().toString()+x;
		tv.setText(current);
	}
	private int convertWeekday(int androidWeekday){
		if(androidWeekday==0){
			return 6;
		}
		return androidWeekday-1;
	}
	private int getMinutes(Time a){
		return (a.hour*60) + a.minute;
	}

}
