import java.util.Scanner;
import java.time.LocalTime;

public class BuyerRequest implements SysOut{
    Simulator buyerFNCD;
    FNCD currentFNCD;
    Staff attendingStaff;
    Scanner readUser = new Scanner(System.in);
    Vehicle userVehicle;
    public Announcer announcer;
    public Logger logger;
    public Tracker tracker;
    private int numDays = 31;

    public void choosingFNCD() {
        outP("Choose new FNCD location by typing option number", announcer, numDays);
        outP("1. North FNCD Location", announcer, numDays);
        outP("2. South FNCD Location", announcer, numDays);
        int FNCDLocation = readUser.nextInt();
        if (FNCDLocation == 1) {
            for(FNCD fncd : buyerFNCD.FNCDTracker){
                if (fncd.name == "North") {
                    currentFNCD = fncd;
                    outP("You chose North FNCD", announcer, numDays);
                }
            }
        }
        else {
            for(FNCD fncd : buyerFNCD.FNCDTracker){
                if (fncd.name == "South") {
                    currentFNCD = fncd;
                    outP("You chose South FNCD", announcer, numDays);
                }
            }
        }
    }

    public void AskingName() {
        outP("Asking Salesperson for their name", announcer, numDays);
        outP("Their name is " + attendingStaff.name, announcer, numDays);
    }

    public void AskingTime() {
        outP("Asking what time it is", announcer, numDays);
        outP("It is " + LocalTime.now() , announcer, numDays);
    }

    public void newSalesPerson() {
        outP("Asking for new Salesperson", announcer, numDays);
    }

    public void AskingInventory() {
        outP("Asking for Inventory", announcer, numDays);
        for(Vehicle v : currentFNCD.inventory) {
            outP(v.name, announcer, numDays);
        }
    }

    public void AskingDetails() {
        int i = 1;
        outP("Enter the number of the car you would like to look at", announcer, numDays);
        for(Vehicle v : currentFNCD.inventory) {
            outP(i + v.name, announcer, numDays);
        }
        int vehicleDetails = readUser.nextInt() -1;
        Vehicle chosenVehicle = currentFNCD.inventory.get(vehicleDetails);
        outP("\nType:        " + this.getClass(), announcer, numDays);
        outP("Name:        " + chosenVehicle.name, announcer, numDays);
        outP("Condition:   " + chosenVehicle.condition, announcer, numDays);
        outP("Cleanliness: " + chosenVehicle.cleanliness, announcer, numDays);
        outP("SalesPrice:  " + chosenVehicle.price, announcer, numDays);
    }

    public void BuyingVehicle() {
        int i = 1;
        outP("Enter the number of the car you would like to buy", announcer, numDays);
        for(Vehicle v : currentFNCD.inventory) {
            outP(i + v.name, announcer, numDays);
        }
        int vehicleDetails = readUser.nextInt() -1;
        Vehicle chosenVehicle = currentFNCD.inventory.get(vehicleDetails);
    }

    public void endInteractions() {
    }
}
