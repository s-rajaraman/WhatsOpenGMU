package edu.gmu.whatsopen;

public class Store {
	int id;
	String location;
	Schedule schedule;
	String storeName;
	
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


}
