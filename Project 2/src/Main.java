import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;

public class Main {
    public static void main(String[] args) {
        Dealership FNCD = new Dealership();
        for(int i = 0; i < 30; i++) {
            FNCD.open();
        }
    }
}
