package friends.helper;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;

public enum Colours {
    RED(255, 0, 0),
    BLUE(0, 0, 255),
    GREEN(0, 255, 0),
    NONE(0, 0, 0);

    Colours(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    private final int r;
    private final int g;
    private final int b;

    public int R()  { return r;}
    public int G()  { return g;}
    public int B()  { return b;}

    public Colours next() {
        switch(this) {
            case RED: return BLUE;
            case BLUE: return RED;
        }
        return BLUE;
    }

    public static Colours fromSensor(ColorSensor sensor) {
        float[] hsv = new float[3];
        Color.RGBToHSV(
                sensor.red(),
                sensor.blue(),
                sensor.green(),
                hsv
        );
        return fromHsv(hsv);
    }

    public static Colours fromHsv(float[] hsv) {
        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];

        if(sat < 0.3 || val < 0.2) {
            return NONE;
        }

        if ((hue >= 0 && hue < 30) || (hue >= 330 && hue < 360)) {
            return RED;
        } else if (hue >= 180 && hue <= 270) {
            return BLUE;
        } else {
            return NONE;
        }
    }
}
