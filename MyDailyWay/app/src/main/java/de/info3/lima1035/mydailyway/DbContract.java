package de.info3.lima1035.mydailyway;


import android.provider.BaseColumns;

public class DbContract {

    private DbContract (){

    }

    public static class TrackTable implements BaseColumns {

        public static final String TABLE_NAME = "location_table";
        public static final String COLUMN_NAME_DATE = "location_date";
        public static final String COLUMN_NAME_DURATION = "location_duration";
        public static final String COLUMN_NAME_LENGTH = "location_length";
        public static final String COLUMN_NAME_TRAFFIC = "location_traffic";
        public static final String COLUMN_NAME_NAME = "location_name";
        public static final String COLUMN_NAME_LOC = "location_latitude";
        public static final String COLUMN_NAME_WAY_PURPOSE = "location_way_purpose";





    }

    public static final String SQL_CREATE_LOCATION_TABLE =
            "CREATE TABLE " + TrackTable.TABLE_NAME + " (" +
                    TrackTable._ID + " INTEGER PRIMARY KEY," +
                    TrackTable.COLUMN_NAME_DATE + " TEXT," +
                    TrackTable.COLUMN_NAME_DURATION + " TEXT," +
                    TrackTable.COLUMN_NAME_LENGTH + " TEXT," +
                    TrackTable.COLUMN_NAME_TRAFFIC + " TEXT," +
                    TrackTable.COLUMN_NAME_NAME + " TEXT," +
                    TrackTable.COLUMN_NAME_WAY_PURPOSE + " TEXT," +
                    TrackTable.COLUMN_NAME_LOC + " TEXT)";
}
