package com.easymorse.list;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

import com.thoughtworks.xstream.XStream;

public class TestView extends ListActivity {
	
	private NewsListAdp listAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.setListAdapter(getAdapter());
	}
	
	private NewsListAdp getAdapter(){
		if(this.listAdapter==null){
			listAdapter=new NewsListAdp(this, getList());
		}
		return listAdapter;
	}

	@SuppressWarnings("unchecked")
	private List<NewsInfo> getList() {
		try {
			XStream xStream = new XStream();
			xStream.alias("item", NewsInfo.class);
			return (List<NewsInfo>) xStream.fromXML(this.getAssets().open("list.xml"));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}