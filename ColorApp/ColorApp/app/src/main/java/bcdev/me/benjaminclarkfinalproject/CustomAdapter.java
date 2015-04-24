package bcdev.me.benjaminclarkfinalproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// bastardization of : http://androidexample.com/How_To_Create_A_Custom_Listview_-_Android_Example/index.php?view=article_discription&aid=67&aaid=92
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    //region Class variables
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    private ColorRow tempRow = null;
    private Resources res;
    //endregion

    //region Constuctor
    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    //endregion

    //region Helper methods
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    //endregion

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView colorRowTextView;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            rowView = inflater.inflate(R.layout.fragment_color_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.colorRowTextView = (TextView) rowView.findViewById(R.id.color_row_text_view);

            /************  Set holder with LayoutInflater ************/
            rowView.setTag( holder );
        }
        else
            holder=(ViewHolder)rowView.getTag();

        if(data.size()<=0)
        {
            holder.colorRowTextView.setText("No Data");

        }
        else
        {
            final ColorApp colorApp = (ColorApp)holder.colorRowTextView.getContext().getApplicationContext();

            tempRow = ( ColorRow ) data.get( position );

            float[] hsvMin = {(float)tempRow.getMinHue(), (float)tempRow.getSaturation(), (float)tempRow.getValue()};
            float[] hsvMax = {(float)tempRow.getMaxHue(), (float)tempRow.getSaturation(), (float)tempRow.getValue()};

            int minColor = Color.HSVToColor(hsvMin);
            int maxColor = Color.HSVToColor(hsvMax);

            GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] {minColor, maxColor });

            if(colorApp.getLevel()!=3){
                switch (colorApp.getSwatchCount(0)){
                    case 1:
                        float[] hsvMid_1_0 = {(tempRow.getMinHue()+60) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_1_1 = {(tempRow.getMinHue()+120) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_1_2 = {(tempRow.getMinHue()+180) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_1_3 = {(tempRow.getMinHue()+240) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_1_4 = {(tempRow.getMinHue()+300) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};

                        int midColor_1_0 = Color.HSVToColor(hsvMid_1_0);
                        int midColor_1_1 = Color.HSVToColor(hsvMid_1_1);
                        int midColor_1_2 = Color.HSVToColor(hsvMid_1_2);
                        int midColor_1_3 = Color.HSVToColor(hsvMid_1_3);
                        int midColor_1_4 = Color.HSVToColor(hsvMid_1_4);

                        drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                                new int[] {minColor, midColor_1_0, midColor_1_1, midColor_1_2, midColor_1_3, midColor_1_4, maxColor });
                        break;
                    case 2:
                        float[] hsvMid_2_0 = {(tempRow.getMinHue()+60) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_2_1 = {(tempRow.getMinHue()+120) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};
                        float[] hsvMid_2_2 = {(tempRow.getMinHue()+180) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};

                        int midColor_2_0 = Color.HSVToColor(hsvMid_2_0);
                        int midColor_2_1 = Color.HSVToColor(hsvMid_2_1);
                        int midColor_2_2 = Color.HSVToColor(hsvMid_2_2);

                        drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                                new int[] {minColor, midColor_2_0, midColor_2_1, midColor_2_2, maxColor });
                        break;
                    case 3:
                        float[] hsvMid_3_0 = {(tempRow.getMinHue()+60) % 360, (float)tempRow.getSaturation(), (float)tempRow.getValue()};

                        int midColor_3_0 = Color.HSVToColor(hsvMid_3_0);

                        drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                                new int[] {minColor, midColor_3_0, maxColor });
                        break;
                    default:
                        break;
                }
            }

            rowView.setBackground(drawable);

            if(tempRow.getLevel() == 3){
                String text = "Name: " + tempRow.getName();
                text += "\nHue: " + tempRow.getMinHue();
                text += "\nSaturation: " + (double)Math.round(tempRow.getSaturation() * 100000) / 100000;
                text += "\nValue: " + (double)Math.round(tempRow.getValue() * 100000) / 100000;
                holder.colorRowTextView.setText(text);
            }

            rowView.setOnClickListener(new OnItemClickListener( position ));
        }

        return rowView;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MainActivity sct = (MainActivity)activity;

            sct.onItemClick(mPosition);
        }
    }
}