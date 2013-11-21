package edu.gmu.whatsopen.store;


public class Store {
	private int id;
	private String location;
	private Schedule schedule;
	private String storeName;
	private Coordinate storeCoordinate;

	public Store(int id, String location, Schedule schedule,String storeName, Coordinate coordinate){
		this.id=id;
		this.location = location;
		this.schedule = schedule;
		this.storeName = storeName;
		this.storeCoordinate = coordinate;
	}

	@Override
	public String toString(){
		String result = "Store Name "+storeName;
		result+=" Location "+location;
		result+=" Id "+id;
		result+="\nTimings - "+schedule.isOpen()+ "\n"+schedule.toString()+"\n\n";
		return result;
	}

	public boolean isOpen(){
		return schedule.isOpen();
	}
	public int getId(){
		return id;
	}
	public String getLocation(){
		return location;
	}
	public String getStoreName(){
		return storeName;
	}
	public Schedule getSchedule(){
		return schedule;
	}
	public String getTodaysTimings(){
		return null;
	}
	
	public Coordinate getCoordinate(){
		return storeCoordinate;
	}

}
