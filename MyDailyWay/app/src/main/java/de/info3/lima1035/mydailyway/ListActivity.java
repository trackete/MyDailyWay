package de.info3.lima1035.mydailyway;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView mTextView;
    LocationListViewAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_aktivitiy_drawer);
        LocationListViewAdapter locationListViewAdapter;



        DbHelper.getInstance(ListActivity.this).geTrackData();

        locationListViewAdapter = new LocationListViewAdapter(ListActivity.this,LocationData.getInstance().getLocationList());
        ListView listView = (ListView) findViewById(R.id.id_list_view);

        listView.setAdapter(locationListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this, DetailViewActivity.class);
                intent.putExtra("KEY_ID",position);
                startActivity(intent);

            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<Tracking> list =LocationData.getInstance().getLocationList();
        Adapter = new LocationListViewAdapter(this,LocationData.getInstance().getLocationList());
        ListView listView123 = (ListView) findViewById(R.id.id_list_view);
        listView123.setAdapter(Adapter);

        // Enables Always-on

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentMain = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intentMain);
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
}
