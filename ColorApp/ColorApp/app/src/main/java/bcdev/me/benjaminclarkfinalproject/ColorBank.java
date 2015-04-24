package bcdev.me.benjaminclarkfinalproject;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ColorBank {
    private static ArrayList<Color> colors = null;
    private Activity activity;

    public ColorBank(Activity activity){
        this.activity = activity;

        if(colors == null){
            setColorBank();
        }
    }

    public ArrayList<Color> getColors(){
        return colors;
    }

    private void setColorBank(){
        colors = new ArrayList();

        InputStream inputStream = null;
        try {
            inputStream = activity.getAssets().open("Colors.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(inputStream != null){
            CSVFile csvFile = new CSVFile(inputStream);
            List colorList = csvFile.read();

            for(int a = 0; a < colorList.size(); a++){
                String name = ((String[])colorList.get(a))[0];
                int degree = Integer.parseInt(((String[])colorList.get(a))[1]);
                double saturationPercentage = Double.parseDouble(((String[])colorList.get(a))[2]) / 100.0;
                double valuePercentage = Double.parseDouble(((String[])colorList.get(a))[3]) / 100.0;

                colors.add(new Color(name, degree, (float)saturationPercentage, (float)valuePercentage));
            }
        }
    }
}
