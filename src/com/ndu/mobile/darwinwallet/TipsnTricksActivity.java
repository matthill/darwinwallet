package com.ndu.mobile.darwinwallet;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class TipsnTricksActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips_and_tricks);
        
		String[] tips_text = getResources().getStringArray(R.array.tips_text);
		List<String> tipsList = new ArrayList<String>(tips_text.length);
		for (String tip : tips_text) tipsList.add(tip);
				TipsArrayAdapter adapter = new TipsArrayAdapter(this, R.layout.tip, R.id.tipstricks, tipsList);
		// Bind to our new adapter.
		setListAdapter(adapter);
	}
}