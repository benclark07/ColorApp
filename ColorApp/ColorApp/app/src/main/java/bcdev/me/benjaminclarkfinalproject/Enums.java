package bcdev.me.benjaminclarkfinalproject;

public class Enums {
    public enum SortingOrder{
        HSV(0, " ORDER BY Hue ASC, Saturation DESC, Value DESC"),
        HVS(1, " ORDER BY Hue ASC, Value DESC, Saturation DESC"),
        SHV(2, " ORDER BY Saturation DESC, Hue ASC, Value DESC"),
        SVH(3, " ORDER BY Saturation DESC, Value DESC, Hue ASC"),
        VHS(4, " ORDER BY Value DESC, Hue ASC, Saturation DESC"),
        VSH(5, " ORDER BY Value DESC, Saturation DESC, Hue ASC");

        SortingOrder(int key, String value){
            this.key = key;
            this.value = value;
        }

        private final int key;
        private final String value;


        public int key() { return this.key; }
        public String value(){
            return this.value;
        }
    }
}