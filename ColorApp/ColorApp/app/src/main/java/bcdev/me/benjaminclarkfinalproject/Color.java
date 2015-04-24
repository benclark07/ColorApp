package bcdev.me.benjaminclarkfinalproject;

public class Color {
    private int id;
    private String name;
    private int hue;
    private float saturation;
    private float value;

    public Color() {}

    public Color(String name, int hue, float saturation, float value){
        this.name = name;
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    //region Accesssors
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getHue(){
        return hue;
    }
    public float getSaturation(){
        return saturation;
    }
    public float getValue(){
        return value;
    }
    public void setID(int ID){
        this.id = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setHue(int hue){
        this.hue = hue;
    }
    public void setSaturation(float saturation){
        this.saturation = saturation;
    }
    public void setValue(float value){
        this.value = value;
    }
    //endregion
}
