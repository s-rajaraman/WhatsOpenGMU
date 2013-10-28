package edu.gmu.whatsopen.store;


public class Store {
	private int id;
	private String location;
	private Schedule schedule;
	private String storeName;

	public Store(int id, String location, Schedule schedule,String storeName){
		this.id=id;
		this.location = location;
		this.schedule = schedule;
		this.storeName = storeName;
	}

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

}
