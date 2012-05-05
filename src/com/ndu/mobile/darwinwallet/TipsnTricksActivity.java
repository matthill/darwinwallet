package com.ndu.mobile.darwinwallet;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class TipsnTricksActivity extends ListActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.tips_and_tricks);
        

		String[] tips_text = getResources().getStringArray(R.array.tips_text);
		
//		
//        for (int i = 0; i < tips_text.length; i++)
//        {
//        	data.put("Tip #" + String.valueOf(i + 1), tips_text[i]);
//        }
        
		List<String> tipsList = new ArrayList<String>(tips_text.length);
		for (String tip : tips_text)
			tipsList.add(tip);
		
		TipsArrayAdapter adapter = new TipsArrayAdapter(this, R.layout.tip,
				R.id.tipstricks, tipsList);
        // Bind to our new adapter.
        setListAdapter(adapter);
		
		
        //TableLayout table = (TableLayout) findViewById(R.id.tblTipsTricks);
//        
//        LinearLayout tipLayout = (LinearLayout) findViewById(R.id.layoutTips);
//        
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        
//        for (int i = 0; i < tips_text.length; i++)
//        {
//        	View row = inflater.inflate(R.layout.tip, null);
//        	
//        	((TextView) row.findViewById(R.id.lblTipId)).setText("Tip #" + String.valueOf(i + 1));
//        	((TextView) row.findViewById(R.id.lblTipText)).setText(tips_text[i]);
//        	
//        	tipLayout.addView(row);
//
//        }
        
//        table.setShrinkAllColumns(true);
//        table.setStretchAllColumns(true);
//        table.requestLayout();
    }
}
