import java.util.SplittableRandom;

public class Buyer {
    SplittableRandom random = new SplittableRandom();
    private String buyerType;
    private String vehicleType;
    private double buyingChance;

    Buyer() {
        vehicleType = new String[]{"Performance", "Car", "Pickup"}[random.nextInt(3)];
        buyerType = new String[]{"Just Looking", "Wants One", "Needs One"}[random.nextInt(3)];
        switch (buyerType) { // calculate base chance to buy based on buyerType
            case "Just Looking":
                buyingChance = 10.0; // 10%
                break;
            case "Wants One":
                buyingChance = 40.0; // 40%
                break;
            case "Needs One":
                buyingChance = 70.0; // 70%
                break;
            default:
                buyingChance = 0; // should never be reached
                break;
        }
    }

    public void report() {
        System.out.println("\nVehicleType:  " + vehicleType);
        System.out.println("BuyerType:    " + buyerType);
        System.out.println("ChanceToBuy:  " + buyingChance);
    }
}
