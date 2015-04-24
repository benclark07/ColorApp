package bcdev.me.benjaminclarkfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private MainActivity mCustomListView = null;
    private ArrayList<ColorRow> mCustomListViewValuesArr;
    private ListView mList;
    private CustomAdapter mAdapter;
    private Resources mRes;
    private ColorApp mColorApp;
    private ColorsDataSource datasource;

    private Button mSortingOrderButton;
    private Button mConfigureSwatchesButton;
    private TextView mSortingOrderTextView;
    private TextView mConfigureSwatchesTextView;

    //region Life-cycle and Activity methods
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        datasource = new ColorsDataSource(this);

        int dbRowCount = 0;
        try {
            dbRowCount = datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(dbRowCount <= 0){
            populateDB();
        }

        mColorApp = (ColorApp)getApplication();
        mColorApp.setLevel(0);
        mColorApp.setMinHue(0);
        mColorApp.setMaxHue(360);
        mColorApp.setSaturation((float) 1.0);
        mColorApp.setValue((float) 1.0);

        mCustomListView = this;
        mRes = getResources();

        mSortingOrderButton = (Button)findViewById(R.id.sortingOrder);
        mSortingOrderButton.setVisibility(View.GONE);

        mSortingOrderTextView = (TextView)findViewById(R.id.sortingOrderTextView);
        mSortingOrderTextView.setVisibility(View.GONE);

        mConfigureSwatchesButton = (Button)findViewById(R.id.configureColorSwatchesButton);
        mConfigureSwatchesTextView = (TextView)findViewById(R.id.configureColorSwatchesTextView);

        mConfigureSwatchesTextView.setText(getString(R.string.rowsPrefix) + " " + mColorApp.getSwatchCount(mColorApp.getLevel()) + "\nCentral Hue: " + mColorApp.getCentralHue());

        setListData(mColorApp.getSwatchCount(0));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    //endregion

    //region UI Handlers
    public void onClickConfigureColorSwatches(View view){
        handleProgressDialog();
    }

    public void onClickConfigureSortingOrder(View view){
        AlertDialog levelDialog;

        final String[] items = getSortingOrderValues();

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.sortingOrderDialogText));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        mColorApp.setSortingOrder(Enums.SortingOrder.HSV.key());
                        break;
                    case 1:
                        mColorApp.setSortingOrder(Enums.SortingOrder.HVS.key());
                        break;
                    case 2:
                        mColorApp.setSortingOrder(Enums.SortingOrder.SHV.key());
                        break;
                    case 3:
                        mColorApp.setSortingOrder(Enums.SortingOrder.SVH.key());
                        break;
                    case 4:
                        mColorApp.setSortingOrder(Enums.SortingOrder.VHS.key());
                        break;
                    case 5:
                        mColorApp.setSortingOrder(Enums.SortingOrder.VSH.key());
                        break;
                    default:
                        mColorApp.setSortingOrder(Enums.SortingOrder.HSV.key());
                        break;
                }

                ArrayList<Color> colors = datasource.getColorsInRange(mColorApp.getMinHue(), mColorApp.getMaxHue(), mColorApp.getSaturation(), mColorApp.getValue(), getSortingOrderValueForInt(mColorApp.getSortingOrder()));

                mColorApp.setColors(colors);
                mColorApp.setLevel(3);

                mSortingOrderButton.setVisibility(View.VISIBLE);
                mSortingOrderTextView.setVisibility(View.VISIBLE);
                mSortingOrderTextView.setText(getString(R.string.orderTextPrefix) + " " + getSortingOrderStringForInt(mColorApp.getSortingOrder()));

                setListData(colors.size());

                dialog.dismiss();
            }
        });

        levelDialog = builder.create();
        levelDialog.show();
    }

    public void onItemClick(int mPosition)
    {
        ColorRow tempRow = mCustomListViewValuesArr.get(mPosition);

        switch (mColorApp.getLevel()){
            case 0 :
                int rowMinHue_level0 = tempRow.getMinHue();
                int rowMaxHue_level0 = tempRow.getMaxHue();

                mColorApp.setMinHue(rowMinHue_level0);
                mColorApp.setMaxHue(rowMaxHue_level0);
                mColorApp.setLevel(1);

                mConfigureSwatchesButton.setVisibility(View.VISIBLE);
                mConfigureSwatchesTextView.setVisibility(View.VISIBLE);
                mConfigureSwatchesTextView.setText(getString(R.string.rowsPrefix) + " " + mColorApp.getSwatchCount(1));

                setListData(10);
                break;
            case 1 :
                float rowSaturation_level1 = (float)tempRow.getSaturation();

                mColorApp.setSaturation(rowSaturation_level1);
                mColorApp.setLevel(2);

                mConfigureSwatchesTextView.setText(getString(R.string.rowsPrefix) + " " + mColorApp.getSwatchCount(2));

                setListData(10);
                break;

            case 2 :
                int rowMinHue_level2 = tempRow.getMinHue();
                int rowMaxHue_level2 = tempRow.getMaxHue();
                float rowSaturation_level2 = (float)tempRow.getSaturation();
                float rowValue_level2 = (float)tempRow.getValue();

                ArrayList<Color> colors = datasource.getColorsInRange(rowMinHue_level2, rowMaxHue_level2, rowSaturation_level2, rowValue_level2, getSortingOrderValueForInt(mColorApp.getSortingOrder()));

                mColorApp.setColors(colors);
                mColorApp.setLevel(3);

                mSortingOrderButton.setVisibility(View.VISIBLE);
                mSortingOrderTextView.setVisibility(View.VISIBLE);
                mSortingOrderTextView.setText(getString(R.string.orderTextPrefix) + " " + getSortingOrderStringForInt(mColorApp.getSortingOrder()));

                mConfigureSwatchesButton.setVisibility(View.GONE);
                mConfigureSwatchesTextView.setVisibility(View.GONE);

                setListData(colors.size());
                break;
            default:
                break;
        }
    }
    //endregion

    //region Helper methods
    private void handleProgressDialog(){
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog, null);

        final ColorApp colorApp = (ColorApp)getApplication();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final SeekBar rowCountSB = (SeekBar)v.findViewById(R.id.seekBarSV);
        final TextView countTextView = (TextView)v.findViewById(R.id.rowCountTextView);

        final SeekBar hueSB = (SeekBar)v.findViewById(R.id.seekBarHue);
        final TextView hueTV = (TextView)v.findViewById(R.id.hueTextView);
        final TextView hueLegend = (TextView)v.findViewById(R.id.centralHue_Legend);

        if(colorApp.getLevel() != 0){
            hueSB.setVisibility(View.GONE);
            hueTV.setVisibility(View.GONE);
            hueLegend.setVisibility(View.GONE);
        }

        int level = colorApp.getLevel();
        int swatchCount = colorApp.getSwatchCount(level);
        int centralHue = colorApp.getCentralHue();

        countTextView.setText(String.valueOf(swatchCount));
        hueTV.setText(String.valueOf(centralHue));

        float[] hsv = {(float)centralHue, (float)1.0, (float)1.0};
        int hsvToColorInt = android.graphics.Color.HSVToColor(hsv);
        Drawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {hsvToColorInt, hsvToColorInt });
        hueTV.setBackground(drawable);

        builder.setPositiveButton(getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int rowCountConfirmed = Integer.parseInt(countTextView.getText().toString());

                        colorApp.setSwatchCount(colorApp.getLevel(), rowCountConfirmed);

                        if(colorApp.getLevel() == 0){
                            int centralHueConfirmed = Integer.parseInt(hueTV.getText().toString());
                            colorApp.setCentralHue(centralHueConfirmed);
                        }

                        mConfigureSwatchesTextView.setText(getString(R.string.rowsPrefix) + " " + mColorApp.getSwatchCount(mColorApp.getLevel()) + "\nCentral Hue: " + mColorApp.getCentralHue());


                        setListData(rowCountConfirmed);

                        dialog.dismiss();
                    }
                });

        rowCountSB.setMax(colorApp.getLevel() == 0 ? 36 : 256);
        rowCountSB.setProgress(colorApp.getSwatchCount(colorApp.getLevel()));
        rowCountSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                countTextView.setText(String.valueOf(progress));
            }
        });

        if(colorApp.getLevel() == 0){
            hueSB.setMax(360);
            hueSB.setProgress(colorApp.getCentralHue());
            hueSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    hueTV.setText(String.valueOf(progress));
                    colorApp.setCentralHue(progress);

                    float[] hsv = {(float)progress, (float)1.0, (float)1.0};
                    int hsvToColorInt = android.graphics.Color.HSVToColor(hsv);
                    Drawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {hsvToColorInt, hsvToColorInt });
                    hueTV.setBackground(drawable);
                }
            });
        }

        builder.setView(v);
        builder.create();
        builder.show();
    }
    private String getSortingOrderStringForInt(int enumInt){
        String[] orderArray = getSortingOrderValues();

        return orderArray[enumInt];
    }
    private String getSortingOrderValueForInt(int enumInt){
        String sortingOrderText = "";
        switch (enumInt){
            case 0:
                sortingOrderText = Enums.SortingOrder.HSV.value();
                break;
            case 1:
                sortingOrderText = Enums.SortingOrder.HVS.value();
                break;
            case 2:
                sortingOrderText = Enums.SortingOrder.SHV.value();
                break;
            case 3:
                sortingOrderText = Enums.SortingOrder.SVH.value();
                break;
            case 4:
                sortingOrderText = Enums.SortingOrder.VHS.value();
                break;
            case 5:
                sortingOrderText = Enums.SortingOrder.VSH.value();
                break;
            default:
                break;
        }

        return sortingOrderText;
    }
    private String[] getSortingOrderValues(){
        return new String[]{getString(R.string.hsv), getString(R.string.hvs), getString(R.string.shv), getString(R.string.svh), getString(R.string.vhs), getString(R.string.vsh)};
    }
    private void populateDB(){
        ArrayList<Color> colors = new ColorBank(this).getColors();

        for(int a = 0; a < colors.size(); a++){
            datasource.createColor(colors.get(a));
        }
    }
    public void setListData(int numRows){
        mCustomListViewValuesArr = new ArrayList<>();

        int minHue = mColorApp.getMinHue();
        int maxHue = mColorApp.getMaxHue();
        int centralHue = mColorApp.getCentralHue();

        for (int i = 0; i < numRows; i++) {
            final ColorRow row = new ColorRow();

            switch (mColorApp.getLevel()){
                case 0 :
                    int sectionDegree = (mColorApp.getMaxHue() - mColorApp.getMinHue())/numRows;
                    int initialEstimate = (centralHue + (i*sectionDegree) - (sectionDegree/2));
                    minHue = initialEstimate < 0 ? initialEstimate + 360 : initialEstimate;
                    minHue = minHue > 360 ? minHue - 360 : minHue;

                    initialEstimate = (minHue + sectionDegree);
                    maxHue = initialEstimate > 360 ? initialEstimate - 360 : initialEstimate;
                    maxHue = maxHue < 0 ? maxHue + 360 : maxHue;

                    row.setMinHue(minHue);
                    row.setMaxHue(maxHue);
                    row.setSaturation(1.0);
                    row.setValue(1.0);
                    break;
                case 1 :
                    float sectionSaturationDifference = (float)(1.0/(float)numRows);
                    row.setMinHue(minHue);
                    row.setMaxHue(maxHue);
                    row.setSaturation(1.0-sectionSaturationDifference*i);
                    row.setValue(1.0);
                    break;

                case 2 :
                    float sectionValueDifference = (float)(1.0/(float)numRows);
                    row.setMinHue(minHue);
                    row.setMaxHue(maxHue);
                    row.setSaturation(mColorApp.getSaturation());
                    row.setValue(1.0-sectionValueDifference*i);
                    break;
                case 3 :
                    Color tempValues = mColorApp.getColors().get(i);
                    String name = tempValues.getName();
                    int hue = tempValues.getHue();
                    float saturation = tempValues.getSaturation();
                    float value = tempValues.getValue();

                    row.setName(name);
                    row.setMinHue(hue);
                    row.setMaxHue(hue);
                    row.setSaturation(saturation);
                    row.setValue(value);
                    row.setLevel(3);
                    break;
            }

            mCustomListViewValuesArr.add( row );
        }

        mList = ( ListView )findViewById( R.id.listview );
        mAdapter = new CustomAdapter( mCustomListView, mCustomListViewValuesArr, mRes );
        mList.setAdapter( mAdapter );
    }
    //endregion
}
