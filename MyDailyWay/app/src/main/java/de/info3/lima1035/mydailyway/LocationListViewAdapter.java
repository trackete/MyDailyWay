package de.info3.lima1035.mydailyway;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by marku on 17.01.2018.
 */

public class LocationListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Location> locationList;

    public LocationListViewAdapter (Context context,List<Location> listLocation) {
        locationList = listLocation;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
