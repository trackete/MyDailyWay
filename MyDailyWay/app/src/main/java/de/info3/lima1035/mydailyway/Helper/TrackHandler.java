package de.info3.lima1035.mydailyway.Helper;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import de.info3.lima1035.mydailyway.MainActivity;

/**
 * Created by rich1016 on 30.11.2017.
 */

public class TrackHandler {

    private GoogleMap mMap;
    private Location mLocation;
    private ArrayList<Location> mLocationList;
    private Context mContext;
    private Polyline polyline;

    public TrackHandler(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
        mLocationList = new ArrayList<>();

    }

    public void draw(Location location) {


        mLocationList.add(location);
        double lat;
        double lon;
        if (MainActivity.chooseTraffic == 1) {
            PolylineOptions options = new PolylineOptions().width(12).color(Color.BLUE);
            for (int i = 0; i < mLocationList.size(); i++) {
                Location tempLocation = mLocationList.get(i);

                lat = tempLocation.getLatitude();
                lon = tempLocation.getLongitude();
                LatLng latlng = new LatLng(lat, lon);
                options.add(latlng);
            }
            polyline = mMap.addPolyline(options);
        }

        else if (MainActivity.chooseTraffic == 2) {
                PolylineOptions options = new PolylineOptions().width(12).color(Color.GREEN);
                for (int i = 0; i < mLocationList.size(); i++) {
                    Location tempLocation = mLocationList.get(i);

                    lat = tempLocation.getLatitude();
                    lon = tempLocation.getLongitude();
                    LatLng latlng = new LatLng(lat, lon);
                    options.add(latlng);
                }
            polyline = mMap.addPolyline(options);

        }

        else if (MainActivity.chooseTraffic == 3) {
            PolylineOptions options = new PolylineOptions().width(12).color(Color.YELLOW);
            for (int i = 0; i < mLocationList.size(); i++) {
                Location tempLocation = mLocationList.get(i);

                lat = tempLocation.getLatitude();
                lon = tempLocation.getLongitude();
                LatLng latlng = new LatLng(lat, lon);
                options.add(latlng);
            }
            polyline = mMap.addPolyline(options);

        }

        else if (MainActivity.chooseTraffic == 4) {
            PolylineOptions options = new PolylineOptions().width(12).color(Color.RED);
            for (int i = 0; i < mLocationList.size(); i++) {
                Location tempLocation = mLocationList.get(i);

                lat = tempLocation.getLatitude();
                lon = tempLocation.getLongitude();
                LatLng latlng = new LatLng(lat, lon);
                options.add(latlng);
            }
            polyline = mMap.addPolyline(options);

        }

        else if (MainActivity.chooseTraffic == 5) {
            PolylineOptions options = new PolylineOptions().width(12).color(Color.DKGRAY);
            for (int i = 0; i < mLocationList.size(); i++) {
                Location tempLocation = mLocationList.get(i);

                lat = tempLocation.getLatitude();
                lon = tempLocation.getLongitude();
                LatLng latlng = new LatLng(lat, lon);
                options.add(latlng);
            }
            polyline = mMap.addPolyline(options);
        }



    }

    public void stopDraw(){
        mLocationList.clear();

    }
}
