package bcdev.me.benjaminclarkfinalproject;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class ColorApp extends Application{
    private static final String PREFS_NAME = "ColorAppData";
    private static final String MIN_HUE = "minHue";
    private static final String MAX_HUE = "maxHue";
    private static final String CENTRAL_HUE = "centralHue";
    private static final String SATURATION = "saturation";
    private static final String VALUE = "value";
    private static final String LEVEL = "level";
    private static final String LEVEL0_SC = "level1";
    private static final String LEVEL1_SC = "level2";
    private static final String LEVEL2_SC = "level3";
    private static final String SORTING_ORDER = "sortingOrder";

    private ArrayList<Color> colors;

    private int sortingOrder;
    private int level;
    private int level0SwatchCount;
    private int level1SwatchCount;
    private int level2SwatchCount;
    private int minHue;
    private int maxHue;
    private int centralHue;
    private float saturation;
    private float value;

    //region Accessor methods
    public void setColors(ArrayList<Color> colors){
        this.colors = colors;
    }
    public void setLevel(int level){
        this.level = level;

        setInSharedPreferences(LEVEL, level);
    }
    public void setMinHue(int minHue){
        this.minHue = minHue;

        setInSharedPreferences(MIN_HUE, minHue);
    }
    public void setMaxHue(int maxHue){
        this.maxHue = maxHue;

        setInSharedPreferences(MAX_HUE, maxHue);
    }
    public void setCentralHue(int centralHue){
        this.centralHue = centralHue;

        setInSharedPreferences(CENTRAL_HUE, centralHue);
    }
    public void setSaturation(float saturation){
        this.saturation = saturation;

        setInSharedPreferences(SATURATION, saturation);
    }
    public void setValue(float value){
        this.value = value;

        setInSharedPreferences(VALUE, value);
    }
    public void setSortingOrder(int sortingOrder){
        this.sortingOrder = sortingOrder;

        setInSharedPreferences(SORTING_ORDER, sortingOrder);
    }
    public void setSwatchCount(int levelInput, int count){
        switch (levelInput){
            case 0:
                this.level0SwatchCount = count;

                setInSharedPreferences(LEVEL0_SC, level0SwatchCount);
                break;
            case 1:
                this.level1SwatchCount = count;

                setInSharedPreferences(LEVEL1_SC, level1SwatchCount);
                break;
            case 2:
                this.level2SwatchCount = count;

                setInSharedPreferences(LEVEL2_SC, level2SwatchCount);
                break;
        }
    }

    public ArrayList<Color> getColors(){
        return colors;
    }
    public int getLevel(){
        return level;
    }
    public int getMinHue(){
        minHue = getFromSharedPreferences(MIN_HUE, 0);

        return minHue;
    }
    public int getMaxHue(){
        maxHue = getFromSharedPreferences(MAX_HUE, 0);

        return maxHue;
    }
    public int getCentralHue(){
        centralHue = getFromSharedPreferences(CENTRAL_HUE, 0);

        return centralHue;
    }
    public float getSaturation(){
        saturation = getFromSharedPreferences(SATURATION, (float)0.0);

        return saturation;
    }
    public float getValue(){
        value = getFromSharedPreferences(VALUE, (float)0.0);

        return value;
    }
    public int getSortingOrder(){
        sortingOrder = getFromSharedPreferences(SORTING_ORDER, Enums.SortingOrder.HSV.key());

        return sortingOrder;
    }
    public int getSwatchCount(int levelInput){
        switch (levelInput){
            case 0:
                level0SwatchCount = getFromSharedPreferences(LEVEL0_SC, 12);

                return level0SwatchCount;
            case 1:
                level1SwatchCount = getFromSharedPreferences(LEVEL1_SC, 10);

                return level1SwatchCount;
            case 2:
                level2SwatchCount = getFromSharedPreferences(LEVEL2_SC, 10);

                return level2SwatchCount;
            default:
                return 10;
        }
    }
    //endregion

    //region Helper methods
    private void setInSharedPreferences(String key, int value){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(key, value);
        editor.commit();
    }
    private void setInSharedPreferences(String key, float value){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putFloat(key, value);
        editor.commit();
    }
    private int getFromSharedPreferences(String key, int defaultValue){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int value = settings.getInt(key, defaultValue);

        return value;
    }
    private float getFromSharedPreferences(String key, float defaultValue){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        float value = settings.getFloat(key, defaultValue);

        return value;
    }
    //endregion
}
