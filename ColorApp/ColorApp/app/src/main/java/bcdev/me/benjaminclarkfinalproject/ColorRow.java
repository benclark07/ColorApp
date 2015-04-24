package bcdev.me.benjaminclarkfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ColorRow extends Fragment {
    private static final String NAME = "name";
    private static final String MIN_HUE = "minHue";
    private static final String MAX_HUE = "maxHue";
    private static final String SATURATION = "saturation";
    private static final String VALUE = "value";
    private static final String LEVEL = "level";

    private OnFragmentInteractionListener mListener;

    private String name;
    private int minHue;
    private int maxHue;
    private int level;
    private double saturation;
    private double value;

    public ColorRow() {}

    //region Fragment life-cycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(name != null){
                name = getArguments().getString(NAME);
                level = getArguments().getInt(LEVEL);
            }

            minHue = getArguments().getInt(MIN_HUE);
            maxHue = getArguments().getInt(MAX_HUE);
            saturation = getArguments().getDouble(SATURATION);
            value = getArguments().getDouble(VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_row, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

    //region Accessor methods
    public void setLevel(int level){
        this.level = level;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMinHue(int minHue){
        this.minHue = minHue;
    }
    public void setMaxHue(int maxHue){
        this.maxHue = maxHue;
    }
    public void setSaturation(double saturation){
        this.saturation = saturation;
    }
    public void setValue(double value){
        this.value = value;
    }
    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }
    public int getMinHue(){
        return minHue;
    }
    public int getMaxHue(){
        return maxHue;
    }
    public double getSaturation(){
        return saturation;
    }
    public double getValue(){
        return value;
    }
    //endregion

    //region Helper methods

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(ColorRow colorRow);
    }
    //endregion
}
