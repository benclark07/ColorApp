package bcdev.me.benjaminclarkfinalproject;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ColorTable {

    // Database table
    public static final String TABLE_COLORS = MySQLiteHelper.TABLE_COLORS;
    public static final String COLUMN_ID = MySQLiteHelper.COLUMN_ID;
    public static final String COLUMN_NAME = MySQLiteHelper.COLUMN_NAME;
    public static final String COLUMN_HUE = MySQLiteHelper.COLUMN_HUE;
    public static final String COLUMN_SATURATION = MySQLiteHelper.COLUMN_SATURATION;
    public static final String COLUMN_VALUE = MySQLiteHelper.COLUMN_VALUE;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_COLORS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_HUE + " int not null, "
            + COLUMN_SATURATION + " decimal not null, "
            + COLUMN_VALUE + " decimal not null);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ColorTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        onCreate(database);
    }
}


