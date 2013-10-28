package edu.gmu.whatsopen.store;

import java.util.ArrayList;

import android.text.format.Time;

public class Schedule {
	ArrayList<Timings>times;

	public Schedule(ArrayList<Timings>times){
		this.times = times;
	}

	public ArrayList<Timings> getTimings(){
		return times;
	}

	public String toString(){
		String result = ""; 
		for(Timings t:times){
			result+=t.toString()+"\n";
		}
		return result;
	}

	public boolean isOpen(){
		Time temp = new Time();
		temp.setToNow();
		temp.weekDay = convertWeekday(temp.weekDay);
		if(times.size()<temp.weekDay+1){
			return false;
		}
		return times.get(temp.weekDay).isOpen();
	}

	/*Android weekday is 0-6, 0 being Sunday
		GMU JSON is 0-6, 0 being Monday
		Converts Android time to GMU Json time*/
	private int convertWeekday(int androidWeekday){
		if(androidWeekday==0){
			return 6;
		}
		return androidWeekday-1;
	}

}
