package de.info3.lima1035.mydailyway;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marku on 17.01.2018.
 */

public class LocationListViewAdapter extends BaseAdapter {


    private List<Tracking> locationList;
    private LayoutInflater layoutInflater;



    @Override
    public int getCount() {
        int length = locationList.size();

        return length;
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.location_list_item, null);
            TextView textView =(TextView) view.findViewById(R.id.textView_username);
            textView.setText(locationList.get(position).getName());
        }

        ((TextView) view.findViewById(android.R.id.text1)).setText((Integer) getItem(position));
        return view;
    }
public LocationListViewAdapter (Context context, List<Tracking> locationList) {
        this.locationList = locationList;
        this.layoutInflater = LayoutInflater.from(context);

}


}
