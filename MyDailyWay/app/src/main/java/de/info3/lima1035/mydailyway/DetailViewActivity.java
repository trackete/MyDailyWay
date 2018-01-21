package de.info3.lima1035.mydailyway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        int position = getIntent().getExtras().getInt("KEY_ID");
//Hier müssen die Details angezeigt werden
        //auslesen aus Datenbankliste -> angleichen an tracking

       /* List<User> userList = UserData.getInstance().getUserList();

        User user = userList.get(position);

        TextView textViewName = (TextView) findViewById(R.id.textView5);
        TextView textViewGeburtstag = (TextView) findViewById(R.id.textView6);
        TextView textViewGeschlecht = (TextView) findViewById(R.id.textView9);

        textViewName.setText(user.getName());
        textViewGeburtstag.setText(user.getGeburtstag());
        textViewGeschlecht.setText(user.getGeschlecht());*/
    }
}
