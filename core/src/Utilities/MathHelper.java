package Utilities;


import java.util.Random;

public class MathHelper {
    public static int randInt(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }
}
