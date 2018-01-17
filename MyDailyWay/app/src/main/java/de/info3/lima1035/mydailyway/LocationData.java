package de.info3.lima1035.mydailyway;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;


public class LocationData {


    private List<Location> locationList;

    private static LocationData instance;

    private LocationData(){

        locationList = new ArrayList<Location>();

    }

    public static LocationData getInstance(){
        if (LocationData.instance == null){
            LocationData.instance = new LocationData();
        }
        return LocationData.instance;
    }

    public void save(Tracking location){
   //     locationList.add(location);
    }

    public List<Location> getUserList() {
        return locationList;
    }

}
