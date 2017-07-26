package com.death.paidfree;

/**
 * Created by deathcode on 22/07/17.
 */

public class MathUtils {

    private MathUtils() { }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}