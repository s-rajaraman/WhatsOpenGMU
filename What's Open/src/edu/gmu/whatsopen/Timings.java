package edu.gmu.whatsopen;

import android.text.format.Time;

public class Timings {
	Time startTime;
	Time endTime;

	//"07:00:00"
	// 01234567
	public Timings(String start, String end){
		startTime.hour = Integer.parseInt(start.substring(0, 2));
		startTime.minute = Integer.parseInt(start.substring(3, 5));
		endTime.hour = Integer.parseInt(end.substring(0, 2));
		endTime.minute = Integer.parseInt(end.substring(3, 5));
	}
	public Timings(Time start, Time end){
		startTime = start;
		endTime = end;
	}

	public boolean isOpen(){
		Time now = new Time();
		now.setToNow();
		now.weekDay = convertWeekday(now.weekDay);
		if(startTime.weekDay==endTime.weekDay){
			return getMinutes(now)>=getMinutes(startTime) &&
					getMinutes(now)<getMinutes(endTime);
		}
		else{
			if(startTime.weekDay==now.weekDay && getMinutes(startTime)<=getMinutes(now)){	
				return true;
			}
			else if(endTime.weekDay==now.weekDay && getMinutes(endTime)>getMinutes(now)){
				return true;
			}}
		return false;
	}

	public String toString(){
		return "Start day "+startTime.weekDay+" Start time: " + startTime.hour+":"+startTime.minute
				+"End day "+endTime.weekDay+"End Time: "+endTime.hour+":"+endTime.minute;
	}

	private int getMinutes(Time a){
		return (a.hour*60) + a.minute;
	}
	
	private int convertWeekday(int androidWeekday){
		if(androidWeekday==0){
			return 6;
		}
		return androidWeekday-1;
	}

}
