import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;

// Quick little buyer class that can take care of its name and initialization
public class Buyer implements SysOut{
    String name;
    Enums.BuyerType type;
    Enums.VehicleType preference;
    FNCD currentFNCD;
    Staff attendingStaff;
    public ArrayList<FNCD> FNCDList;
    public Announcer announcer;
    private int numDays = 31;
    private Scanner readUser = new Scanner(System.in);

    static List<String> names = Arrays.asList("Luke","Leia","Han","Chewy");
    static Namer namer = new Namer(names);
    Buyer(FNCD Fncd) {
        // even chances for these randoms - could weight them if needed
        preference = Utility.randomEnum(Enums.VehicleType.class);
        type = Utility.randomEnum(Enums.BuyerType.class);
        name = namer.getNext();
        announcer = Fncd.announcer;
        currentFNCD = Fncd;
        FNCDList = new ArrayList<>();
    }

    /*
     * 
     * 
     * Part of the implementation of Command pattern for commands that can be executed
     * 
     * 
     */

    public void choosingFNCD() {
        outP("Choose new FNCD location by typing option number", announcer, numDays);
        outP("1. North FNCD Location", announcer, numDays);
        outP("2. South FNCD Location", announcer, numDays);
        int FNCDLocation = readUser.nextInt(); //Decide between FNCD using the number
        if (FNCDLocation == 1) {
            currentFNCD = FNCDList.get(FNCDLocation -1);
            ArrayList<Staff> FNCDstaff = Staff.getStaffByType(currentFNCD.staff, Enums.StaffType.Salesperson); //Switches some variables around out of necessity or for more convienience
            attendingStaff = FNCDstaff.get(0);
            announcer = currentFNCD.announcer;
            outP("You chose North FNCD", announcer, numDays);
        }
        else {
            currentFNCD = FNCDList.get(FNCDLocation -1 );
            ArrayList<Staff> FNCDstaff = Staff.getStaffByType(currentFNCD.staff, Enums.StaffType.Salesperson); //Same as comment above
            attendingStaff = FNCDstaff.get(0);
            announcer = currentFNCD.announcer;
            outP("You chose South FNCD", announcer, numDays);
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
        ArrayList<Staff> salesPersons = Staff.getStaffByType(currentFNCD.staff, Enums.StaffType.Salesperson);
        for (Staff s : salesPersons) {
            if (s.name == attendingStaff.name){ //Skips over salesperson if they're the same person
            }
            else {
                attendingStaff = s;
                outP("Your new sales person is " + attendingStaff.name, announcer, numDays); //Gets new Salesperson
                break;
            }
        }
    }

    public void AskingInventory() {
        int i = 1;
        outP("Asking for Inventory", announcer, numDays); //Loops through entire inventory
        for(Vehicle v : currentFNCD.inventory) {
            outP(i + ". " + v.name, announcer, numDays);
            i = i + 1;
        }
    }

    public void AskingDetails() {
        int i = 1;
        outP("Enter the number of the car you would like to look at", announcer, numDays); //Loops through entire inventory
        for(Vehicle v : currentFNCD.inventory) {
            outP(i + ". " + v.name, announcer, numDays);
            i = i + 1;
        }
        int vehicleDetails = readUser.nextInt() -1; //Get specific vehicles details by typing in number next to vehicle you want
        Vehicle chosenVehicle = currentFNCD.inventory.get(vehicleDetails); 

        //Prints information
        outP("\nType:        " + chosenVehicle.type.toString(), announcer, numDays);
        outP("Name:        " + chosenVehicle.name, announcer, numDays);
        outP("Condition:   " + chosenVehicle.condition, announcer, numDays);
        outP("Cleanliness: " + chosenVehicle.cleanliness, announcer, numDays);
        outP("SalesPrice:  " + chosenVehicle.price, announcer, numDays);
    }

    public void BuyingVehicle() {
        int i = 1;
        outP("Enter the number of the car you would like to buy", announcer, numDays);
        for(Vehicle v : currentFNCD.inventory) {
            outP(i + ". " + v.name, announcer, numDays);
            i = i + 1;
        }
        int vehicleDetails = readUser.nextInt() -1;
        Vehicle chosenVehicle = currentFNCD.inventory.get(vehicleDetails);
        outP("Salesperson "+attendingStaff.name+" gets a bonus of "+Utility.asDollar(chosenVehicle.sale_bonus)+"!", announcer, numDays);
        outP("You bought " + chosenVehicle.cleanliness+" "+chosenVehicle.condition+" "+chosenVehicle.name+" for base price of "+Utility.asDollar(chosenVehicle.price()), announcer, numDays);
        attendingStaff.bonusEarned += chosenVehicle.sale_bonus;
        attendingStaff.staffEarned += chosenVehicle.sale_bonus;
        Vehicle v1 = chosenVehicle;
        int choice = 0;
        while (choice != 5) {
            outP("Would you like to add any addons to the vehicle", announcer, numDays);
            outP("1. Warranty", announcer, numDays);
            outP("2. Undercoating", announcer, numDays);
            outP("3. AAA", announcer, numDays);
            outP("4. Radio", announcer, numDays);
            outP("5. None", announcer, numDays);
            choice = readUser.nextInt();
            if (choice == 1){
                v1 = new WarrantyDecorator(v1);
                outP("Bought warranty add on", announcer, numDays);
            }
            else if (choice == 2){
                v1 = new UndercoatingDecorator(v1);
                outP("Bought undercoating add on", announcer, numDays);
            }
            else if (choice == 3){
                v1 = new AAADecorator(v1);
                outP("Bought AAA add on", announcer, numDays);
            }
            else if (choice == 4){
                v1 = new SatRadioDecorator(v1);
                outP("Bought radio add on", announcer, numDays);
            }
            else if (choice == 5) {
                outP("No add on", announcer, numDays);
            }
            else {
                outP("Enter a valid choice", announcer, numDays);
            }
        }
        chosenVehicle.setPrice(v1.price());
        currentFNCD.moneyIn(chosenVehicle.price);
        currentFNCD.inventory.remove(chosenVehicle);
    }

    public void endInteractions() {
    }
}
