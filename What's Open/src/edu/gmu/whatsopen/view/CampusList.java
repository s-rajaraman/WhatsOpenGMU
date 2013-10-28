package edu.gmu.whatsopen.view;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CampusList extends ListActivity {
	public static String [] classes = {"Fairfax","Arlington","Prince Williams"};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setListAdapter(new ArrayAdapter<String>(CampusList.this, 
				android.R.layout.simple_list_item_1, classes));	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent("android.intent.action.STORELIST");
		startActivity(i);
	}

}
