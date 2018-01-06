package de.info3.lima1035.mydailyway;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Markus Linnartz: Aktuelle Auswahl des Verkehrsmittels: 1=Fußgänger; 2=Fahrrad; 3=Bus; 4=Zug; 5=Auto//
    public static int chooseTraffic = 4;
    private GoogleMap GoogleMap;

    // Julia Fassbinder und Seline Winkelmann: Definieren von Parametern
    private final int MY_PERMISSION_RWQUEST_FINE_LOCATION = 123;
    private final String TAG = "TAG";
    public int longitude;   //Längengrad
    public int latitude;    //Breitengrad
    private FusedLocationProviderClient mFusedLocationClient;
    boolean mRequestingLocationUpdates;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    String REQUESTING_LOCATION_UPDATES_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
            }
        });

        //Markus Linnartz: Bei Click des Stop-Button (Stoppen des Trackings)//
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking.hide();
                startTracking.show();
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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                }
            }
        });


        //Seline Winkelmann: GoogleMaps implementiern (Code:AIzaSyCxPveUpfQr6cvTTdwYjbnkRyeieIJsmmY)
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap = googleMap;


                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(latitude, longitude);
                GoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                GoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateValuesFromBundle(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            mRequestingLocationUpdates = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_UPDATES_KEY);
        }

    }

    //David: Anfrage zu Positions Update
    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

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
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    //David: Standort Updates anhalten
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

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

    @Override
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
    }

}