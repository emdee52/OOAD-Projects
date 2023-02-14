import java.util.SplittableRandom;

public class Buyer {
    SplittableRandom random = new SplittableRandom();
    private String buyingType;
    private String vehicleType;

    Buyer() {
        vehicleType = new String[]{"Performance", "Car", "Pickup"}[random.nextInt(3)];
        buyingType = new String[]{"Just Looking", "Wants One", "Needs One"}[random.nextInt(3)];
    }

    public void report() {
        System.out.println("VehicleType: " + vehicleType);
        System.out.println("BuyingType:  " + buyingType);
    }
}
