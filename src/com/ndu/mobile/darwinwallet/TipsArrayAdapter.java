package com.ndu.mobile.darwinwallet;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TipsArrayAdapter extends ArrayAdapter<String>
{
	private LayoutInflater mInflater;
	private Context mContext;
	
	public TipsArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects)
	{
		super(context, resource, textViewResourceId, objects);
		
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		TextView tipId = null;
		TextView tipText = null;
		
		String rowData = getItem(position);
		if (null == convertView)
		{
			convertView = mInflater.inflate(R.layout.tip, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		tipId = holder.getTipId();
		tipId.setText(mContext.getString(R.string.tip_n, position + 1));
		tipText = holder.getTipText();
		tipText.setText(rowData);
		
		return convertView;
	}
	
	private class ViewHolder
	{
		private View mRow;
		private TextView tipId = null;
		private TextView tipText = null;
		
		public ViewHolder(View row)
		{
			mRow = row;
		}
		
		public TextView getTipId()
		{
			if (null == tipId)
			{
				tipId = (TextView) mRow.findViewById(R.id.lblTipId);
			}
			
			return tipId;
		}
		
		public TextView getTipText()
		{
			if (null == tipText)
			{
				tipText = (TextView) mRow.findViewById(R.id.lblTipText);
			}
			
			return tipText;
		}
		
	}
	
}