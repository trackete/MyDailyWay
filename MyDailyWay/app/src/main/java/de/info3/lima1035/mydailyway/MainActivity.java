package de.info3.lima1035.mydailyway;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{

    //Markus Linnartz: Aktuelle Auswahl des Verkehrsmittels: 1=Fußgänger; 2=Fahrrad; 3=Bus; 4=Zug; 5=Auto//
    public static int chooseTraffic = 4;
    public boolean track = false;
    private GoogleMap mMap;
   // ArrayList ListLongitude;
    //ArrayList ListLatitude;

    // Julia Fassbinder und Seline Winkelmann: Definieren von Parametern
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;
    public static final String TAG = MainActivity.class.getSimpleName();
   // private final int MY_LOCATION_REQUEST_CODE = 123;
  //  public double longitude;   //Längengrad
  //  public double latitude;     //Breitengrad
  //  public Location location;
  //  private Location Location;
   // String REQUESTING_LOCATION_UPDATES_KEY;
  //  private MapView map;

    String provider = LocationManager.GPS_PROVIDER;
    List<Barcode.GeoPoint> geoPointArray = new ArrayList<Barcode.GeoPoint>();

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private UiSettings uiSettings;
    private Location mCurrentLocation;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates;
    private boolean mTracking = false;
    private TrackHandler mTrackHandler;
  //  FloatingActionButton buttonModeIcon;

    private FragmentManager fm = getSupportFragmentManager();

    //on Create:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final FloatingActionButton startTracking = (FloatingActionButton) findViewById(R.id.button_start_tracking);
        final FloatingActionButton stopTracking = (FloatingActionButton) findViewById(R.id.button_stop_tracking);
        final FloatingActionButton chooseTrafficWalk = (FloatingActionButton) findViewById(R.id.button_choose_traffic_walk);
        final FloatingActionButton chooseTrafficBike = (FloatingActionButton) findViewById(R.id.button_choose_traffic_bike);
        final FloatingActionButton chooseTrafficTrain = (FloatingActionButton) findViewById(R.id.button_choose_traffic_train);
        final FloatingActionButton chooseTrafficBus = (FloatingActionButton) findViewById(R.id.button_choose_traffic_bus);
        final FloatingActionButton chooseTrafficCar = (FloatingActionButton) findViewById(R.id.button_choose_traffic_car);
        final FloatingActionButton closeChooseTraffic = (FloatingActionButton) findViewById(R.id.button_close_choose_traffic);
        final FloatingActionButton walk = (FloatingActionButton) findViewById(R.id.button_walk);
        final FloatingActionButton bike = (FloatingActionButton) findViewById(R.id.button_bike);
        final FloatingActionButton bus = (FloatingActionButton) findViewById(R.id.button_bus);
        final FloatingActionButton train = (FloatingActionButton) findViewById(R.id.button_train);
        final FloatingActionButton car = (FloatingActionButton) findViewById(R.id.button_car);

        //Markus Linnartz: Verstecken der Buttons die erst später angezeigt werden//
        stopTracking.hide();
        closeChooseTraffic.hide();
        chooseTrafficBike.hide();
        chooseTrafficBus.hide();
        chooseTrafficWalk.hide();
        chooseTrafficCar.hide();
        walk.hide();
        bike.hide();
        bus.hide();
        train.hide();
        car.hide();


        //Markus Linnartz: Bei Click des Play-Buttons (Starten des Trackings)//
        startTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking.hide();
                stopTracking.show();
                track = true;
                mTracking = true;
                mMap.clear();

            }
        });

        //Markus Linnartz: Bei Click des Stop-Button (Stoppen des Trackings)//
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopTracking.hide();
                startTracking.show();
                track = false;
                mTracking = false;
                mTrackHandler.stopDraw();

                Intent intentSave = new Intent(MainActivity.this,SaveActivity.class);
                // detailIntent.putExtra(KEY, position);
                startActivity(intentSave);



                // Alles folgende kommt in die Speicherabfrage
                Calendar calendar = Calendar.getInstance();
                String Date = calendar.toString();

                Tracking tracking = new Tracking();

                tracking.setDate(Date);
                tracking.setName("Name"); //Nutzer noch nach Name fragen
                tracking.setLocation("Location");
                // tracking.setDuration(); -> Was ist das?

            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons (Fußgängersymbol)//
        chooseTrafficWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTrafficWalk.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();
            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons (Fahrradsymbol)/
        chooseTrafficBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTrafficBike.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();
            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons (Bussymbol)/
        chooseTrafficBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTrafficBus.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();
            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons (Zugsymbol)/
        chooseTrafficTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTrafficTrain.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();
            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons (Autosymbol)/
        chooseTrafficCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTrafficCar.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();
            }
        });

        //Markus Linnartz: Bei Click des Close-Choose-Traffic-Buttons (X-Symbol)//
        closeChooseTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeChooseTraffic.hide();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
                if (chooseTraffic == 1) {
                    chooseTrafficWalk.show();
                } else if (chooseTraffic == 2) {
                    chooseTrafficBike.show();
                } else if (chooseTraffic == 3) {
                    chooseTrafficBus.show();
                } else if (chooseTraffic == 4) {
                    chooseTrafficTrain.show();
                } else if (chooseTraffic == 5) {
                    chooseTrafficCar.show();
                }
            }
        });

        // Markus Linnartz: Bei Click des Taffic-Auswahl-Buttons (Fusßgänger)//
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic = 1;
                closeChooseTraffic.hide();
                chooseTrafficWalk.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });

        // Markus Linnartz: Bei Click des Taffic-Auswahl-Buttons (Fahrrad)//
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic = 2;
                closeChooseTraffic.hide();
                chooseTrafficBike.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });

        // Markus Linnartz: Bei Click des Taffic-Auswahl-Buttons (Bus)//
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic = 3;
                closeChooseTraffic.hide();
                chooseTrafficBus.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });

        // Markus Linnartz: Bei Click des Taffic-Auswahl-Buttons (Zug)//
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic = 4;
                closeChooseTraffic.hide();
                chooseTrafficTrain.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });

        // Markus Linnartz: Bei Click des Taffic-Auswahl-Buttons (Auto)//
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic = 5;
                closeChooseTraffic.hide();
                chooseTrafficCar.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (isGooglePlayServicesAvailable(this)) {

            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            finish();

        }

        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGpsConfirmationDialog();
        }

        createLocationRequest();


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                .zoom(17)
                                .bearing(0)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        if (mTracking) {
                            mTrackHandler.draw(location);
                        }
                    }

                }
            }
        };
        startLocationUpdates();

    }

    @Override
    protected void onStart() {
        super.onStart();
        moveToLastLocation();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //moveToLastLocation();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Asking for Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Start Location updates
                    Log.d(TAG, "Permission Granted");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Log.d(TAG, "Permission denied");
                    // Show an explanation to the user *asynchronously*
                    AlertDialog.Builder ADbuilder = new AlertDialog.Builder(this);
                    ADbuilder.setMessage("This permission is important for the App to function properly.")
                            .setTitle("Important permission required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                                }
                            });
                    ADbuilder.create();
                    ADbuilder.show();
                }
            }
        }
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        uiSettings = mMap.getUiSettings();

        //setMyLocationEnabled only works, if permission is given

        uiSettings.setMyLocationButtonEnabled(true);

        mTrackHandler = new TrackHandler(this, mMap);




        LatLng karlsruhe = new LatLng(49.008085, 8.403756);
        mMap.addMarker(new MarkerOptions().position(karlsruhe).title("Marker in Karlsruhe"));


    }

    private void moveToLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17)
                            .bearing(0)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            }
        });
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                getMainLooper());
        mRequestingLocationUpdates = true;
    }

    private void showGpsConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setMessage(R.string.gpsConfirmation_message)
                .setCancelable(false)
                .setPositiveButton(R.string.gpsConfirmation_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.gpsConfirmation_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    //David Adam: Nav-Menü
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_change_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_tracks) {
            Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(listIntent);

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_impressum) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

