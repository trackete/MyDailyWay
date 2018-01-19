package de.info3.lima1035.mydailyway;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public String date = "";
    public String duration = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_save_drawer);
        Button buttonSave= (Button) findViewById(R.id.button_save);
        Button buttonCancel= (Button) findViewById(R.id.button_cancel);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        duration = getIntent().getExtras().getString(MainActivity.KEY_DURATION);
        TextView textDauration= (TextView) findViewById(R.id.textView_duration);
        textDauration.setText(duration);

        date = getIntent().getExtras().getString(MainActivity.KEY_DATE);
        TextView textDate = (TextView) findViewById(R.id.textView_date_show);
        textDate.setText(date);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tracking tracking = new Tracking();
                tracking.setDate(date);
                tracking.setLocation("Location");


                Intent intentListView = new Intent(SaveActivity.this, ListActivity.class);
                startActivity(intentListView);
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(SaveActivity.this, MainActivity.class);
                startActivity(intentMain);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
            Intent listIntent = new Intent(SaveActivity.this, ListActivity.class);
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
