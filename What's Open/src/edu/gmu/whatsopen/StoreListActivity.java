package edu.gmu.whatsopen;


import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.*;

public class StoreListActivity extends ListActivity {
	JSONParser jp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jp = new JSONParser(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initList();

	}

	private void initList() {
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

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent("android.intent.action.STOREDETAILS");
		Store currentStore = jp.stores.get(position);

		String name = currentStore.storeName;
		int currentStoreid = currentStore.id;
		String timings = currentStore.schedule.toString();
		String location = currentStore.location;

		i.putExtra(JSONParser.name, name);
		i.putExtra(JSONParser.location, location);
		i.putExtra(JSONParser.id, currentStoreid);
		i.putExtra(JSONParser.main_schedules, timings);
		startActivity(i);
	}
}
