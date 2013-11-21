package edu.gmu.whatsopen.store;

import android.text.format.Time;
/**
 *To hold a start and end time of a Store. 
 *@author Sriram
 */
public class Timings {
	Time startTime;
	Time endTime;
	static String [] weekdays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

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

	/**
	 * Based on current day and time, is the store
	 * open
	 * @author Sriram
	 */
	public boolean isOpen(){
		Time now = new Time();
		now.setToNow();
		now.weekDay = convertWeekday(now.weekDay);
		//Two different cases: 
		//1. A store opens and closes on the same day
		//2. A store opens and closes on different days ie (10 pm - 4 am)
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

	@Override
	public String toString(){
		if(startTime.hour==0&&0==endTime.hour){
			return weekdays[startTime.weekDay]+" - Closed";
		}
		else if(startTime.hour==0&&endTime.hour==23&&endTime.minute==59){
			return weekdays[startTime.weekDay]+" - Open day and night";
		}
		return weekdays[startTime.weekDay]+" - "+timeToString(startTime)
				+" - "+timeToString(endTime);
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

	public String timeToString(Time a){
		int hour = a.hour;
		int minute = a.minute;
		String minuteString = Integer.toString(minute);
		String timePeriod = " am";
		
		//Deals with hours and am/pm
		if(hour>12 && hour<23){
			timePeriod = " pm";
			hour-=12;
		}
		else if(hour==12){
			timePeriod = " pm";
		}
		else if(hour==23){
			hour=12;
		}
		else if(hour==0){
			hour = 12;
		}
		//Deals with minutes whether to add a zero or not
		if(minuteString.length()<2){
			minuteString+="0";
		}
		return hour+":"+minuteString+timePeriod;
	}

}
