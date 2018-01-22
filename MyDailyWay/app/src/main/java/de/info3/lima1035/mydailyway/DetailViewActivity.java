package de.info3.lima1035.mydailyway;

import android.*;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;

import java.util.List;

public class DetailViewActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
       // MapView mapView = (MapView) findViewById(R.id.mapView);

        int position = getIntent().getExtras().getInt("KEY_ID");

        List<Tracking> locationList = LocationData.getInstance().getLocationList();

        Tracking track = locationList.get(position);


        TextView name = (TextView) findViewById(R.id.textViewTrackNameDetailed);
        name.setText(track.getName());
        TextView date = (TextView) findViewById(R.id.textViewTrackDate);
        date.setText(track.getDate());
        TextView length = (TextView) findViewById(R.id.textViewTrackLength);
        length.setText(track.getLength());
        TextView duration = (TextView) findViewById(R.id.textViewTrackDuration);
        duration.setText(track.getDuration());
        TextView purpose = (TextView) findViewById(R.id.textViewTrackPurpose);
        purpose.setText(track.getWayPurpose());
        TextView traffic = (TextView) findViewById(R.id.textViewTrackTraffic);
        traffic.setText(track.getTraffic());









    }





}
