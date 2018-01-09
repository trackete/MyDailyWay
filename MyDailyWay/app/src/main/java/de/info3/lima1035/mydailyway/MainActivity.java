package de.info3.lima1035.mydailyway;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback, OnMapReadyCallback {

    //Markus Linnartz: Aktuelle Auswahl des Verkehrsmittels: 1=Fußgänger; 2=Fahrrad; 3=Bus; 4=Zug; 5=Auto//
    public int chooseTraffic = 4;
    public boolean track = false;
    private GoogleMap googleMap;
    ArrayList ListLongitude;
    ArrayList ListLatitude;

    // Julia Fassbinder und Seline Winkelmann: Definieren von Parametern
    private final int MY_PERMISSION_RWQUEST_FINE_LOCATION = 123;
    private final int MY_LOCATION_REQUEST_CODE = 123;
    private final String TAG = "TAG";
    public double longitude;   //Längengrad
    public double latitude;     //Breitengrad
    public Location location;
    private FusedLocationProviderClient mFusedLocationClient;
    boolean mRequestingLocationUpdates;
    private Location Location;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    String REQUESTING_LOCATION_UPDATES_KEY;
    private MapView map;
    String provider = LocationManager.GPS_PROVIDER;
    List<Barcode.GeoPoint> geoPointArray = new ArrayList<Barcode.GeoPoint>();


    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_RWQUEST_FINE_LOCATION: {
                // if request is cancelled, the result rays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //start Location updates

                    Log.d(TAG, "Permissions Granted");
                } else {
                    Log.d(TAG, "Permissons denied");
                    //Show an explantation to user *asynchronously*
                    AlertDialog.Builder ADbuilder = new AlertDialog.Builder(this);
                    ADbuilder.setMessage("This permission is important for the App to function properly.")
                            .setTitle("Important permission required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_RWQUEST_FINE_LOCATION);

                                }
                            });


                }
            }
        }
    }*/

    // Julia Fassbinder und Seline Winkelmann: Standortfreigabe anfordern
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    // Julia Fassbinder und Seline Winkelmann: Locationrequestintervall
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //David Adam: Toolbar und Drawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Markus Linnartz: Einfügen der FloatingActionsButtons//
        //Markus Linnartz: Jeweils ein Button pro Verkehrsmittel, wobei nur der Button mit dem Symbol des aktuell ausgewählten Verkehrsmittels unten links angezeigt wird)//
        //Markus Linnartz: Jeweils ein Button zur Auswahl der verschiedenen Verkehrsmittel die nach dem Choose-Button erscheinen) und ein Button der diese Auswahl schließt//
        //Markus Linnartz: Ein Start Tracking und ein Stop Tracking Button)//
        final FloatingActionButton startTracking = (FloatingActionButton) findViewById(R.id.button_start_tracking);
        final FloatingActionButton stopTracking = (FloatingActionButton) findViewById(R.id.button_stop_tracking);
        final FloatingActionButton chooseTrafficWalk = (FloatingActionButton) findViewById(R.id.button_choose_traffic_walk);
        final FloatingActionButton chooseTrafficBike = (FloatingActionButton) findViewById(R.id.button_choose_traffic_bike);
        final FloatingActionButton chooseTrafficBus = (FloatingActionButton) findViewById(R.id.button_choose_traffic_bus);
        final FloatingActionButton chooseTrafficTrain = (FloatingActionButton) findViewById(R.id.button_choose_traffic_train);
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
            }
        });

        //Markus Linnartz: Bei Click des Stop-Button (Stoppen des Trackings)//
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking.hide();
                startTracking.show();
                track = false;
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

        // Julia Fassbinder und Seline Winkelmann: Standort
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //&& ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        } else {

        }

        //Seline Winkelmann: GoogleMaps implementiern (Code:AIzaSyCxPveUpfQr6cvTTdwYjbnkRyeieIJsmmY)
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);


        // Julia Fassbinder und Seline Winkelmann: Standort
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        createLocationRequest();

        // Julia Fassbinder und Seline Winkelmann: Standort
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    //tracking

                    //wird getrackt? if schleife
                    // separate methode für linie
                    //polylineoptions und polyline
                    //loc in latlng umwandeln
                    //liste
                    //liste leeren wenn man sie nicht mehr braucht


                    //Tracking, funktioniert aber nicht
                  /*  if (track = true){
                        latitude = Location.getLatitude();
                        longitude = Location.getLongitude();
                        tracking(latitude, longitude);

                    }*/
                }
            }

            ;
        };

        startLocationUpdates();


    }

    // Julia Fassbinder und Seline Winkelmann: Location

    @Override
    protected void onStart() {
        super.onStart();
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

        // Julia Fassbinder und Seline Winkelmann: Location
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {


                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    float zoom = 20;

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

                }
            }
        });
    }

    /*
    Tracking funktioniert aber nicht
    //Markus Linnartz: Tracking
    public void tracking(double lati, double longi) {
        ListLongitude.add(longi);
        ListLatitude.add(lati);
        int size = ListLatitude.size();
        double lat = ListLatitude.indexOf(size);
        double lon = ListLongitude.indexOf(size);
        double latOld = ListLatitude.indexOf(size - 1);
        double lonOld = ListLongitude.indexOf(size - 1);

        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(lat, lon), new LatLng(latOld, lonOld))
                .width(5)
                .color(Color.GRAY));
        line.setVisible(true);
        */


        //Später mit Verkehrsmitelauswahl:
       /* if (chooseTraffic = 1){

            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lat, lon), new LatLng(lat, lon))
                    .width(5)
                    .color(Color.GRAY));
                           line.setVisible(true);
        }

        else if (chooseTraffic = 2){

            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lat, lon), new LatLng(lat, lon))
                    .width(5)
                    .color(Color.GREEN));
                            line.setVisible(true);
        }

        else if (chooseTraffic = 3){

            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lat, lon), new LatLng(lat, lon))
                    .width(5)
                    .color(Color.MAGENTA));
                            line.setVisible(true);
        }

        else if (chooseTraffic = 4){

            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lat, lon), new LatLng(lat, lon))
                    .width(5)
                    .color(Color.BLUE));
                            line.setVisible(true);
        }

        if (chooseTraffic = 5){

            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lat, lon), new LatLng(lat, lon))
                    .width(5)
                    .color(Color.RED));
                            line.setVisible(true);
        } }*/


    // Julia Fassbinder und Seline Winkelmann: Location Update stopp
    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    // Julia Fassbinder und Seline Winkelmann: Standort
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

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
        this.googleMap.setMyLocationEnabled(true);
    }




    // Julia Fassbinder und Seline Winkelmann: Locationupdate starten
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            mRequestingLocationUpdates = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_UPDATES_KEY);
        }
        updateValuesFromBundle(savedInstanceState);

    }




    //David: Anfrage zu Positions Update
    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        super.onSaveInstanceState(outState, outPersistentState);
    }
    // Julia Fassbinder und Seline Winkelmann: Locationupdates  starten
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        // Julia Fassbinder und Seline Winkelmann: Locations updates nachfragen
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);




    }




    //David: Standort Updates anhalten
    @Override
    protected void onPause() {
        super.onPause();

    }
    // Julia Fassbinder und Seline Winkelmann:
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
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

