package de.info3.lima1035.mydailyway;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SaveActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // length müssen wir noch berechnen
    //duration rechnung lässt app abstürzen
    public String date = "";
    public String duration = "";
    public String length = "";
    public String trackName = "";
    public String traffic = "";
    public String wayPurpose = "";
    public String location = "";

    public static boolean chooseW;
    public static boolean chooseB;
    public static boolean chooseC;
    public static boolean chooseBu;
    public static boolean chooseT;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_save_drawer);

        EditText name = (EditText) findViewById(R.id.editText_trackName);
        trackName = name.getText().toString();

        //hinzufügen editText_track_name + abspeichern in trackName (schon vorhandener String
        Button buttonSave= (Button) findViewById(R.id.button_save);
        Button buttonCancel= (Button) findViewById(R.id.button_cancel);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        duration = getIntent().getExtras().getString("KEY_DURATION");
        TextView textDuration= (TextView) findViewById(R.id.textView_duration_show);
        textDuration.setText(duration);

        date = getIntent().getExtras().getString("KEY_DATE");
        TextView textDate = (TextView) findViewById(R.id.textView_date_show);
        textDate.setText(date);

        length = getIntent().getExtras().getString("KEY_LENGTH");
        TextView textLength = (TextView) findViewById(R.id.textView_length_show);
        textLength.setText(length);


        Spinner wayPurposeSpinner;
        wayPurposeSpinner = (Spinner) findViewById(R.id.spinner_way_purpuse);
        List<String> list = new ArrayList<String>();
        list.add("Arbeit");
        list.add("Ausbildung");
        list.add("Einkauf");
        list.add("Heimweg");
        list.add("Freizeit");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wayPurposeSpinner.setAdapter(dataAdapter);

        wayPurpose = wayPurposeSpinner.getSelectedItem().toString();





        final FloatingActionButton walkSave = (FloatingActionButton) findViewById(R.id.floatingActionButton_walk_save);
        final FloatingActionButton bikeSave = (FloatingActionButton) findViewById(R.id.floatingActionButtonBikeSave);
        final FloatingActionButton carSave = (FloatingActionButton) findViewById(R.id.floatingActionButtonCarSave);
        final FloatingActionButton busSave = (FloatingActionButton) findViewById(R.id.floatingActionButtonBusSave);
        final  FloatingActionButton trainSave = (FloatingActionButton) findViewById(R.id.floatingActionButtonTrainSave);
        final FloatingActionButton walkSaveGR = (FloatingActionButton) findViewById(R.id.floatingActionButtonWalkSaveGR);
        final  FloatingActionButton bikeSaveGR = (FloatingActionButton) findViewById(R.id.floatingActionButtonBikeSaveGR);
        final FloatingActionButton carSaveGR = (FloatingActionButton) findViewById(R.id.floatingActionButtonCarSaveGR);
        final FloatingActionButton busSaveGR = (FloatingActionButton) findViewById(R.id.floatingActionButtonBusSaveGR);
        final FloatingActionButton trainSaveGR = (FloatingActionButton) findViewById(R.id.floatingActionButtonTrainSaveGR);



        walkSave.hide();
        bikeSave.hide();
        carSave.hide();
        busSave.hide();
        trainSave.hide();
        walkSaveGR.hide();
        bikeSaveGR.hide();
        carSaveGR.hide();
        busSaveGR.hide();
        trainSaveGR.hide();

        chooseW = MainActivity.chooseWalk;
        chooseB = MainActivity.chooseBike;
        chooseC = MainActivity.chooseCar;
        chooseBu = MainActivity.chooseBus;
        chooseT = MainActivity.chooseTrain;



    //die Strings 1-31 müssen noch mit den jeweiligen Verkehrsmitteln im String benannt werden  (Bsp. im 1. if)


        if (chooseW == true && chooseB == true && chooseC == true && chooseBu == true &&chooseT == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();

            traffic = "Fuß, Fahrrad, Auto, Bus, Bahn";
        }
        else if (chooseW == true&&chooseB == true && chooseBu == true && chooseT == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            carSave.hide();
            carSaveGR.show();

            traffic = "Fuß, Fahrrad, Bus, Bahn";
        }
        else if (chooseW == true && chooseB == true && chooseC == true && chooseBu == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSaveGR.show();
            trainSave.hide();

            traffic = "Fuß, Fahrrad, Auto, Bus";

        }
        else if (chooseW == true && chooseB == true && chooseC == true && chooseT== true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            busSaveGR.show();
            busSave.hide();

            traffic = "Fuß, Fahrrad, Auto, Bahn";
        }
        else if(chooseW == true&& chooseC == true&& chooseBu == true && chooseT == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            bikeSave.hide();
            bikeSaveGR.show();

            traffic = "Fuß, Auto, Bus, Bahn";
        }
        else if (chooseW == true&& chooseC == true&& chooseT == true ){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            bikeSave.hide();
            busSave.hide();
            bikeSaveGR.show();
            busSaveGR.show();

            traffic = "Fuß, Auto, Bahn";
        }
       else if (chooseW == true&& chooseB == true&& chooseT == true ){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            carSave.hide();
            busSave.hide();
            bikeSaveGR.show();
            busSaveGR.show();
            traffic = "Fuß, Fahrrad, Bahn";
        }
       else if (chooseW == true&& chooseB == true&& chooseBu == true ){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
           busSave.show();
           chooseBu = false;
           busSaveGR.hide();

           carSave.hide();
            trainSave.hide();
            carSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Fahrrad, Bus";
       }
        else if(chooseW == true&& chooseC == true&& chooseBu == true ){
           walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
           carSave.show();
            chooseC = false;
            carSaveGR.hide();
           busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            bikeSave.hide();
            trainSave.hide();
            bikeSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Auto, Bus";
        }


       else if(chooseW == true&& chooseBu == true&& chooseT == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            bikeSave.hide();
            carSave.hide();
            bikeSaveGR.show();
            carSaveGR.show();
            traffic = "Fuß, Bus, Bahn";
        }
        else if (chooseW == true && chooseB == true && chooseC == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.hide();
            trainSave.hide();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Fahrrad, Auto";
        }
        else  if (chooseW == true && chooseB == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
           carSave.hide();
           busSave.hide();
           trainSave.hide();
            carSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Fahrrad";
        }
        else if(chooseW == true&& chooseC == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();

            bikeSave.hide();
            busSave.hide();
            trainSave.hide();
            bikeSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Auto";}

        else if(chooseW == true&& chooseBu == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            bikeSave.hide();
            carSave.hide();
            trainSave.hide();
            bikeSaveGR.show();
            carSaveGR.show();
            trainSaveGR.show();
            traffic = "Fuß, Bus";}
        else if(chooseW == true&& chooseT == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
           bikeSave.hide();
           carSave.hide();
           busSave.hide();
            bikeSaveGR.show();
            carSaveGR.show();
            busSaveGR.show();
            traffic = "Fuß, Bahn";}
        else if(chooseW == true){
            walkSave.show();
            chooseW = false;
            walkSaveGR.hide();
            bikeSaveGR.show();
            carSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            bikeSave.hide();
            carSave.hide();
            busSave.hide();
            trainSave.hide();
            traffic = "Fuß";
        }

        else if(chooseB == true&& chooseC == true&& chooseBu == true&&chooseT == true){
           bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
           carSave.show();
            chooseC = false;
            carSaveGR.hide();
           busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            walkSaveGR.show();
            traffic = "Fahrrad, Auto, Bus, Bahn";}
        else if (chooseB == true&& chooseC == true&&chooseT == true){
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            walkSaveGR.show();
            busSave.hide();
            busSaveGR.show();
            traffic = "Fahrrad, Auto, Bahn";}

        else  if(chooseB == true&& chooseBu == true&&chooseT == true){
           bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
           busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            carSave.hide();
            walkSaveGR.show();
            carSaveGR.show();
            traffic = "Fahrrad, Bus, Bahn";}
        else if(chooseB == true&& chooseC == true&& chooseBu == true){
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
        busSave.show();
        chooseBu = false;
        busSaveGR.hide();

            walkSave.hide();
            trainSave.hide();
            walkSaveGR.show();
            trainSaveGR.show();
            traffic = "Fahrrad, Auto, Bus,";}
        else if(chooseB == true&&chooseT == true){
           bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
          walkSave.hide();
          carSave.hide();
          busSave.hide();
            walkSaveGR.show();
            carSaveGR.show();
            busSaveGR.show();
            traffic = "Fahrrad, Bahn";}

        else if(chooseB == true&& chooseBu == true){
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            walkSave.hide();
            carSave.hide();
            trainSave.hide();
            walkSaveGR.show();
            carSaveGR.show();
            trainSaveGR.show();
            traffic = "Fahrrad, Bus";}
        else if(chooseB == true&& chooseC == true){
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            walkSave.hide();
            busSave.hide();
            trainSave.hide();
            walkSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Fahrrad, Auto";}
        else if(chooseB == true){
            bikeSave.show();
            chooseB = false;
            bikeSaveGR.hide();
            walkSave.hide();
            carSave.hide();
            busSave.hide();
            trainSave.hide();
            walkSaveGR.show();
            carSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Fahrrad";}


        else if (chooseC == true&&chooseBu == true&&chooseT == true) {
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            bikeSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            traffic = "Auto, Bus, Bahn";}
        else if (chooseC == true&&chooseBu == true){
            carSave.show();
            chooseC = false;
            carSaveGR.hide();
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
           walkSave.hide();
           bikeSave.hide();
           trainSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            trainSaveGR.show();
            traffic = "Auto, Bus";}
        else if (chooseC == true&&chooseT == true) {
           carSave.show();
            chooseC = false;
            carSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
           walkSave.hide();
           bikeSave.hide();
           busSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            busSaveGR.show();
            traffic = "Auto, Bahn";
        }
        else if (chooseC == true){
           carSave.show();
            chooseC = false;
            carSaveGR.hide();
           walkSave.hide();
           bikeSave.hide();
           busSave.hide();
           trainSave.hide();

            walkSaveGR.show();
            bikeSaveGR.show();
            busSaveGR.show();
            trainSaveGR.show();
            traffic = "Auto";}

        else if (chooseBu == true&&chooseT == true) {
            busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            bikeSave.hide();
            carSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            carSaveGR.show();
            traffic = "Bus, Bahn";}
        else if (chooseBu == true) {
           busSave.show();
            chooseBu = false;
            busSaveGR.hide();
            walkSave.hide();
            bikeSave.hide();
            carSave.hide();
            trainSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            carSaveGR.show();
            trainSaveGR.show();
            traffic = "Bus";}

        else if (chooseT == true){
            trainSave.show();
            chooseT = false;
            trainSaveGR.hide();
            walkSave.hide();
            bikeSave.hide();
            carSave.hide();
            busSave.hide();
            walkSaveGR.show();
            bikeSaveGR.show();
            carSaveGR.show();
            busSaveGR.show();
            traffic = "Bahn";}

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Tracking track = new Tracking();
                track.setDate(date);
                track.setDuration(duration);
                track.setName(trackName);
                track.setTraffic(traffic);
                track.setWayPurpose(wayPurpose);
                track.setLocation(location);
                track.setLength(length);

                Toast.makeText(SaveActivity.this, "Track gespeichert", Toast.LENGTH_LONG).show();
                DbHelper.getInstance(SaveActivity.this).saveTracking(track);




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
