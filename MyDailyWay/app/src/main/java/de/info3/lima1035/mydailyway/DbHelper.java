package de.info3.lima1035.mydailyway;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "user_db";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private static DbHelper instance;

    private DbHelper(Context context){

        super(context, DB_NAME, null,DB_VERSION);
    }

    public static DbHelper getInstance(Context context){
        if (DbHelper.instance == null){
            DbHelper.instance = new DbHelper(context);
        }
        return DbHelper.instance;
    }



    //?????????????????location oder Tracking ??????????????????
    public void saveTracking(Tracking track){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valuesTrack = new ContentValues();

        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_DATE, track.getDate());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_DURATION, track.getDuration());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_LENGTH, track.getLength());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_TRAFFIC, track.getTraffic());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_NAME, track.getName());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_LOC, track.getLocation());
        valuesTrack.put(DbContract.TrackTable.COLUMN_NAME_WAY_PURPOSE, track.getWayPurpose());


        db.insert(DbContract.TrackTable.TABLE_NAME,null,valuesTrack);

        db.close();

    }

    //?????????????????location oder Tracking ??????????????????
    public void geTrackData(){

        String selectQuery = "SELECT * FROM "+ DbContract.TrackTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                //cursor zeigt auf einen Eintrag in der Ergebnisliste


                String date = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_DATE));
                String duration = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_DURATION));
                String length = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_LENGTH));
                String traffic = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_TRAFFIC));
                String name = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_NAME));
                String location = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_LOC));
                String wayPurpose = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_NAME_WAY_PURPOSE));

               // double lat = Double.parseDouble(latitude);
               // double lon = Double.parseDouble(longitude);


               /* Tracking track = new Tracking();

                track.setDate(date);
                track.setDuration(duration);
                track.setDuration(length);
                track.setTraffic(traffic);
                track.setName(name);
                track.setLocation(location);
                track.setWayPurpose(wayPurpose);



                LocationData.getInstance().save(track);*/

            }while (cursor.moveToNext());


        }
        cursor.close();
        db.close();
    }

}
