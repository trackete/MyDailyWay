package de.info3.lima1035.mydailyway;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private static DbHelper instance;

    private DbHelper(Context context){

        super(context, "location_db", null,1);
    }

    public static DbHelper getInstance(Context context){
        if (DbHelper.instance == null){
            DbHelper.instance = new DbHelper(context);
        }
        return DbHelper.instance;
    }



    //?????????????????location oder Tracking ??????????????????
    public void saveTracking(Tracking location){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valuesLocation = new ContentValues();
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_DATE, location.getDate());
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_DURATION, location.getDuration());
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_TRAFFIC, location.getTraffic());
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_NAME, location.getName());
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_LAT, location.getLatitude());
        valuesLocation.put(DbContract.LocationTable.COLUMN_NAME_LONG, location.getLongitude());


        db.insert(DbContract.LocationTable.TABLE_NAME,null,valuesLocation);

        db.close();

    }

    //?????????????????location oder Tracking ??????????????????
    public void insertLocation(){

        String selectQuery = "SELECT * FROM "+ DbContract.LocationTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                //cursor zeigt auf einen Eintrag in der Ergebnisliste


                String date = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_DATE));
                String duration = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_DURATION));
                String traffic = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_TRAFFIC));
                String name = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_NAME));
                String latitude = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_LAT));
                String longitude = cursor.getString(cursor.getColumnIndex(DbContract.LocationTable.COLUMN_NAME_LONG));


                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);


                Tracking location = new Tracking();

                location.setDate(date);
                location.setDuration(duration);
                location.setTraffic(traffic);
                location.setName(name);



                LocationData.getInstance().save(location);

            }while (cursor.moveToNext());


        }
        cursor.close();
        db.close();
    }

}
