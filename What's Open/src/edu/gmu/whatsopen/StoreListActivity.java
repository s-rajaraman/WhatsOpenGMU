package edu.gmu.whatsopen;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.*;

public class StoreListActivity extends ListActivity {

	JsonDiningParser jp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jp = new JsonDiningParser(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initList();

	}

	private void initList() {
		jp.sortListByAvailability();
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				this,android.R.layout.simple_list_item_1, jp.getStoreNames()){

			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {

				View view = super.getView(position, convertView, parent);
				TextView textView=(TextView) view.findViewById(android.R.id.text1);

				if(jp.stores.get(position).schedule.isOpen()){
					textView.setTextColor(Color.rgb(0, 153, 0));
				}
				else{
					textView.setTextColor(Color.RED);
				}
				return view;
			}
		};
		this.setListAdapter(adapter);
		setLastPosition();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent("android.intent.action.STOREDETAILS");
		Store currentStore = jp.stores.get(position);

		String name = currentStore.storeName;
		int currentStoreid = currentStore.id;
		String timings = currentStore.schedule.toString();
		String location = currentStore.location;

		i.putExtra(JsonDiningParser.name, name);
		i.putExtra(JsonDiningParser.location, location);
		i.putExtra(JsonDiningParser.id, currentStoreid);
		i.putExtra(JsonDiningParser.main_schedules, timings);
		saveLastPosition();
		startActivity(i);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveLastPosition();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initList();
	}

	private void saveLastPosition(){
		int index = this.getListView().getFirstVisiblePosition();
		SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putInt("Last_Position", index);
		edit.commit();
	}
	
	private void setLastPosition(){
		int index = getPreferences(Context.MODE_PRIVATE).getInt("Last_Position", 0);
		this.getListView().setSelectionFromTop(index, 0);
	}
}
