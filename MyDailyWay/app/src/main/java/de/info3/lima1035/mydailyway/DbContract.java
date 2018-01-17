package de.info3.lima1035.mydailyway;


import android.provider.BaseColumns;

public class DbContract {

    private DbContract (){

    }

    public static class LocationTable implements BaseColumns {

        public static final String TABLE_NAME = "location_table";
        public static final String COLUMN_NAME_DATE = "location_date";
        public static final String COLUMN_NAME_DURATION = "location_duration";
        public static final String COLUMN_NAME_TRAFFIC = "location_traffic";
        public static final String COLUMN_NAME_NAME = "location_name";
        public static final String COLUMN_NAME_LAT = "location_latitude";
        public static final String COLUMN_NAME_LONG = "location_longitude";



    }

    public static final String SQL_CREATE_LOCATION_TABLE =
            "CREATE TABLE " + LocationTable.TABLE_NAME + "(" +
                    LocationTable._ID + " INTEGER PRIMARY KEY," +
                    LocationTable.COLUMN_NAME_DATE + " Text," +
                    LocationTable.COLUMN_NAME_DURATION + " Text," +
                    LocationTable.COLUMN_NAME_TRAFFIC + " Text," +
                    LocationTable.COLUMN_NAME_NAME + " Text" +
                    LocationTable.COLUMN_NAME_LAT + " Text" +
                    LocationTable.COLUMN_NAME_LONG + " Text)";
}
