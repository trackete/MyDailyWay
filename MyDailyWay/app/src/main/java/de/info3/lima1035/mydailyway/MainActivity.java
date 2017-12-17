package de.info3.lima1035.mydailyway;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Markus Linnartz: Einfügen der FloatingActionsButtons//
        final FloatingActionButton startTracking = (FloatingActionButton) findViewById(R.id.button_start_tracking);
        final FloatingActionButton stopTracking = (FloatingActionButton) findViewById(R.id.button_stop_tracking);
        final FloatingActionButton chooseTraffic = (FloatingActionButton) findViewById(R.id.button_choose_traffic);
        final FloatingActionButton closeChooseTraffic = (FloatingActionButton) findViewById(R.id.button_close_choose_traffic);
        final FloatingActionButton walk = (FloatingActionButton) findViewById(R.id.button_walk);
        final FloatingActionButton bike = (FloatingActionButton) findViewById(R.id.button_bike);
        final FloatingActionButton bus = (FloatingActionButton) findViewById(R.id.button_bus);
        final FloatingActionButton train = (FloatingActionButton) findViewById(R.id.button_train);
        final FloatingActionButton car = (FloatingActionButton) findViewById(R.id.button_car);


        //Markus Linnartz: Verstecken der Buttons die erst später angezeigt werden//
        stopTracking.hide();
        closeChooseTraffic.hide();
        walk.hide();
        bike.hide();
        bus.hide();
        train.hide();
        car.hide();


        //Markus Linnartz: Bei Click des Play-Buttons//
        startTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking.hide();
                stopTracking.show();
                // hier muss das Tracking rein //
            }
        });

        //Markus Linnartz: Bei Click des Stop-Button/
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking.hide();
                startTracking.show();
                // hier muss das abspeichern vom Tracking rein //
            }
        });

        //Markus Linnartz: Bei Click des Choose-Traffic-Buttons/
        chooseTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTraffic.hide();
                closeChooseTraffic.show();
                walk.show();
                bike.show();
                bus.show();
                train.show();
                car.show();

                // hier muss das auswählen vom Verkehrsmittel rein //
            }
        });

        //Markus Linnartz: Bei Click des Close-Choose-Traffic-Buttons//
        closeChooseTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeChooseTraffic.hide();
                chooseTraffic.show();
                walk.hide();
                bike.hide();
                bus.hide();
                train.hide();
                car.hide();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
             this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery)  {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
