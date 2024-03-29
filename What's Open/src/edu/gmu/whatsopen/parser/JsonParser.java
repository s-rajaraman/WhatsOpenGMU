package edu.gmu.whatsopen.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.gmu.whatsopen.store.Coordinate;
import edu.gmu.whatsopen.store.Schedule;
import edu.gmu.whatsopen.store.Store;
import edu.gmu.whatsopen.store.Timings;

import android.content.Context;
import android.text.format.Time;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;


public class JsonParser {

	public static String dining = "dining";
	public static String id = "id";
	public static String location = "location";
	public static String name = "name";
	public static String special_schedules= "special_schedules";
	public static String main_schedules= "main_schedule";
	public static String open_times = "open_times";
	public static String start_day = "start_day";
	public static String end_day = "end_day";
	public static String start_time = "start_time";
	public static String end_time = "end_time";
	public static String latitude = "lat";
	public static String longtitude = "lng";
	


	private ArrayList<Store> stores;

	// constructor
	public JsonParser(Context c) {
		stores = new ArrayList<Store>();
		try {
			InputStream in = c.getAssets().open("jsonDiningFairfax");
			JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
			readJsonStream(reader);
			sortListByAvailability();

		} catch (IOException e) {
			Log.e("JsonParser.constructor 1", "Failed888888888888888888888888");
			e.printStackTrace();
		}
	} 

	public String[] getStoreNames(){
		String[]store = new String[stores.size()];
		int x = 0;
		for(Store e: stores){
			store[x] = e.getStoreName();
			x++;
		}
		return store;
	}
	
	public ArrayList<Store>getStores(){
		return stores;
	}
	//this is where the json is being read
	private void readJsonStream(JsonReader reader) throws IOException {
		stores = new ArrayList<Store>();
		try {
			reader.beginObject();
			String name = reader.nextName();
			//if the tag is dining, then get the add the array of stores
			if(name.equals(dining)){
				reader.beginArray();
				while (reader.hasNext()) {
					stores.add(readStore(reader));
				}
				reader.endArray();
			}
			reader.endObject();
		}
		finally {
			reader.close();
		}

	}

	private Store readStore(JsonReader reader) {
		int id =-1;
		String location = null;
		Schedule schedule = null;
		String storeName = null;
		double lat = 0;
		double lng = 0;

		try {
			reader.beginObject();

			while (reader.hasNext()) {
				String name = reader.nextName();

				if(name.equals(JsonParser.id) && reader.peek()!=JsonToken.NULL){
					id = reader.nextInt();
				}
				else if(name.equals(JsonParser.location)&& reader.peek()!=JsonToken.NULL){
					location = reader.nextString();
				}
				else if(name.equals(JsonParser.main_schedules)){
					schedule = readSchedule(reader);
				}
				else if(name.equals(JsonParser.name)&& reader.peek()!=JsonToken.NULL){
					storeName = reader.nextString();
				}
				else if(name.equals(JsonParser.latitude)){
					lat = reader.nextDouble();
				}
				else if(name.equals(JsonParser.longtitude)){
					lng = reader.nextDouble();
				}
				else{
					reader.skipValue();
				}
			}
			reader.endObject();
			Log.e("Coordinate", "Lat "+lat+" Lng"+lng);
		} catch (IOException e) {
			Log.e("JsonParser.readStore()", "Failed");
			e.printStackTrace();
		}
		
		return new Store(id,location,schedule,storeName,new Coordinate(lat,lng));
	}

	private Schedule readSchedule(JsonReader reader) {
		ArrayList<Timings> timings = new ArrayList<Timings>();
		try{
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if(name.equals(JsonParser.open_times)){
					timings = readOpenTimes(reader);
				}
				else{
					reader.skipValue();
				}
			}
			reader.endObject();
		}catch(Exception e){
			Log.e("JsonParser.readSchedule()", "Failed");
			e.printStackTrace();
		}
		return new Schedule(timings);
	}

	private ArrayList<Timings> readOpenTimes(JsonReader reader) {
		ArrayList<Timings> timings = new ArrayList<Timings>();
		try{   
			reader.beginArray();
			while (reader.hasNext()) {
				timings.add(readTiming(reader));
			}
			reader.endArray();
			return timings;
		}catch(Exception e){
			Log.e("JsonParser.readOpenTimes()", "Failed");
		}
		return timings;

	}

	private Timings readTiming(JsonReader reader){
		Time start = new Time();
		Time end = new Time();

		try{
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();

				if(name.equals(JsonParser.start_day)){
					start.weekDay = reader.nextInt();
				}
				else if(name.equals(JsonParser.end_day)){
					end.weekDay = reader.nextInt();
				}
				else if(name.equals(JsonParser.start_time)&& reader.peek()!=JsonToken.NULL){
					setTime(start, reader.nextString());
				}
				else if(name.equals(JsonParser.end_time)&& reader.peek()!=JsonToken.NULL){
					setTime(end, reader.nextString());
				}
				else{
					reader.skipValue();
				}
			}
			reader.endObject();

		}catch(Exception e){
			Log.e("JsonParser.readTiming()", "Failed");
			e.printStackTrace();
		}
		return new Timings(start,end);
	}

	public void sortListByAvailability(){
		Collections.sort(stores, new Comparator<Store>() {
			@Override
			public int compare(Store A, Store B) {
				if(A.isOpen()==false && B.isOpen()==true){
					return 1;
				}
				else if(A.isOpen()==true && B.isOpen()==false){
					return -1;
				}
				return A.getStoreName().compareTo(B.getStoreName());
			}

		});
	}

	private void setTime(Time time, String nextString) {
		time.hour = Integer.parseInt(nextString.substring(0, 2));
		time.minute = Integer.parseInt(nextString.substring(3, 5));
	}
}