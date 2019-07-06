package de.maxhenkel.tools;

public class MathTools {

    public static boolean isInBounds(float number, float bound, float tolerance) {
        if (number > bound - tolerance && number < bound + tolerance) {
            return true;
        }
        return false;
    }

    public static float subtractToZero(float num, float sub) {
        float erg;
        if (num < 0) {
            erg = num + sub;
            if (erg > 0) {
                erg = 0;
            }
        } else {
            erg = num - sub;
            if (erg < 0) {
                erg = 0;
            }
        }

        return erg;
    }

    public static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

    public static float round(float value, int scale) {
        return (float) (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale));
    }

}
