package de.info3.lima1035.mydailyway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private TextView mTextView;
    LocationListViewAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        List<Tracking> list =LocationData.getInstance().getLocationList();
        Adapter = new LocationListViewAdapter(this,LocationData.getInstance().getLocationList());
        ListView listView = (ListView) findViewById(R.id.id_list_view);
        listView.setAdapter(Adapter);

        // Enables Always-on

    }
}
