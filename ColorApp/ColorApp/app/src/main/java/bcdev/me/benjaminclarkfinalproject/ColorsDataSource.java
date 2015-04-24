package bcdev.me.benjaminclarkfinalproject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ColorsDataSource extends ContentProvider{
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                                    MySQLiteHelper.COLUMN_NAME,
                                    MySQLiteHelper.COLUMN_HUE,
                                    MySQLiteHelper.COLUMN_SATURATION,
                                    MySQLiteHelper.COLUMN_VALUE };

    public ColorsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public int open() throws SQLException {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_COLORS, null);

        return cursor.getCount();
    }

    public void close() {
        dbHelper.close();
    }

    public Color createColor(Color color) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, color.getName());
        values.put(MySQLiteHelper.COLUMN_HUE, color.getHue());
        values.put(MySQLiteHelper.COLUMN_SATURATION, color.getSaturation());
        values.put(MySQLiteHelper.COLUMN_VALUE, color.getValue());

        long insertId = database.insert(MySQLiteHelper.TABLE_COLORS, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COLORS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            Color newColor = cursorToColor(cursor);
            cursor.close();
            return newColor;
        }

        return null;
    }

    public void deleteColor(Color color) {
        long id = color.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COLORS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Color> getAllColors() {
        List<Color> colors = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_COLORS, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Color color = cursorToColor(cursor);
            colors.add(color);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return colors;
    }

    public ArrayList<Color> getColorsInRange(int minHue, int maxHue, float saturation, float value, String orderByCondition){
        ArrayList<Color> colors = new ArrayList<>();

        String whereCondition;
        if(minHue >= maxHue){
            whereCondition = " where ( " + MySQLiteHelper.COLUMN_HUE + " > " + (minHue - 2) + " or " + MySQLiteHelper.COLUMN_HUE + " < " + (maxHue + 2) + " ) and "
                    + MySQLiteHelper.COLUMN_SATURATION + " > " + (saturation - (float).10) + " and " + MySQLiteHelper.COLUMN_SATURATION + " < " + (saturation + (float).10) + " and "
                    + MySQLiteHelper.COLUMN_VALUE + " > " + (value - (float).10) + " and " + MySQLiteHelper.COLUMN_VALUE + " < " + (value + (float).10);

        }else{
            whereCondition = " where " + MySQLiteHelper.COLUMN_HUE + " > " + (minHue - 2) + " and " + MySQLiteHelper.COLUMN_HUE + " < " + (maxHue + 2) + " and "
                    + MySQLiteHelper.COLUMN_SATURATION + " > " + (saturation - (float).10) + " and " + MySQLiteHelper.COLUMN_SATURATION + " < " + (saturation + (float).10) + " and "
                    + MySQLiteHelper.COLUMN_VALUE + " > " + (value - (float).10) + " and " + MySQLiteHelper.COLUMN_VALUE + " < " + (value + (float).10);
        }

        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_COLORS + whereCondition + orderByCondition, null);

        if(cursor != null){
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Color color = cursorToColor(cursor);
                colors.add(color);
                cursor.moveToNext();
            }
        }

        return colors;
    }

    private Color cursorToColor(Cursor cursor) {
        Color color = new Color();
        color.setID(cursor.getInt(0));
        color.setName(cursor.getString(1));
        color.setHue(Integer.parseInt(cursor.getString(2)));
        color.setSaturation(Float.parseFloat(cursor.getString(3)));
        color.setValue(Float.parseFloat(cursor.getString(4)));
        return color;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
