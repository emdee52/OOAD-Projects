
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public abstract class Staff implements SysOut {
    String name;
    double salary;  // daily salary
    double salaryEarned;
    double bonusEarned;
    Enums.StaffType type;
    int daysWorked;
    static double staffEarned = 0;
    Staff() {
        salaryEarned = 0;
        bonusEarned = 0;
        daysWorked = 0;
    }

    // utility for getting Staff by Type
    // You could do this with getClass instead of Type, but I use the enum
    // because it's clearer to me - less Java-y
    static ArrayList<Staff> getStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        ArrayList<Staff> subclassInstances = new ArrayList<>();
        for (Staff s : staffList) {
            if (s.type == t) subclassInstances.add(s);
        }
        return subclassInstances;
    }

    //Utility for finding out how many of a Staff type there are
    static int howManyStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        int n = 0;
        for (Staff s: staffList) {
            if (s.type == t) n++;
        }
        return n;
    }

    public Staff promote(Staff intern, Enums.StaffType t) { //promote an intern;
        String currentName = intern.name; //Get current values that need to be transferred
        int daysWorked = intern.daysWorked;
        double currentBonus = intern.bonusEarned;
        double currentSalaryEarned = intern.salaryEarned;

        if (t == Enums.StaffType.Mechanic) {         //Mechanic Position
            intern = new Mechanic();                //Transfer Position
        }
        else if (t == Enums.StaffType.Salesperson) {    //Sales Person Position
            intern = new Salesperson();
        }  
        else {
            System.err.println("Error: Invalid Position");
        }

        intern.name = currentName;  //Transfer all values that way intern stats arent reset
        intern.daysWorked = daysWorked;
        intern.bonusEarned = currentBonus;
        intern.salaryEarned = currentSalaryEarned;

        return intern;
    }
}

class Intern extends Staff{
    static List<String> names = Arrays.asList("Fred","Ethel","Lucy","Desi");
    static Namer namer = new Namer(names);
    private WashBehavior washType;

    Intern(WashBehavior w) {
        super();
        type = Enums.StaffType.Intern;
        name = namer.getNext();  // every new intern gets a new name
        salary = 60; // daily salary
        washType = w;
    }

    /*
     * Commands below were created in the implmentation of the Wash Strategy Pattern
     */
    public void setWashType (WashBehavior w) {
        this.washType = w;
    }

    // How an intern washes cars
    public void washVehicles(ArrayList<Vehicle> vList, Announcer announcer, int day, Intern i) {
        washType.wash(vList, announcer, day, this);
    }
}

class Mechanic extends Staff {
    static List<String> names = Arrays.asList("James", "Scotty", "Spock", "Uhura");
    static Namer namer = new Namer(names);
    Mechanic() {
        super();
        type = Enums.StaffType.Mechanic;
        name = namer.getNext();  // every new mechanic gets a new name
        salary = 120; // daily salary
    }

    // how Mechanics repair Vehicles - not as complicated as the Wash thing above
    void repairVehicles(ArrayList<Vehicle> vList, Announcer announcer, int day) {
        int fixCount = 0;
        Enums.Condition startAs;
        // I'm just grabbing the first Vehicle I find - would be easy to randomly pick one
        for (Vehicle v: vList) {
            if (v.condition != Enums.Condition.LikeNew) {
                startAs = v.condition;
                if (v.cleanliness == Enums.Cleanliness.Clean) v.cleanliness = Enums.Cleanliness.Dirty;
                if (v.cleanliness == Enums.Cleanliness.Sparkling) v.cleanliness = Enums.Cleanliness.Clean;
                double chance = Utility.rnd();
                if (chance < .8) {
                    fixCount += 1;
                    if (v.condition == Enums.Condition.Used) {
                        v.condition = Enums.Condition.LikeNew;
                        v.price = v.price * 1.25;  // 25% increase for Used to Like New
                    }
                    if (v.condition == Enums.Condition.Broken) {
                        v.condition = Enums.Condition.Used;
                        v.price = v.price * 1.5;  // 50% increase for Broken to Used
                    }
                    bonusEarned += v.repair_bonus;
                    outP("Mechanic "+name+" got a bonus of "+Utility.asDollar(v.repair_bonus)+"!", announcer, day);
                    outP("Mechanic "+name+" fixed "+v.name+" "+startAs+" to "+v.condition, announcer, day);
                }
                else {
                    fixCount += 1;   // I'm saying a failed repair still took up a fix attempt
                    outP("Mechanic "+name+" did not fix the "+v.condition+" "+v.name, announcer, day);
                }
            }
            if (fixCount==2) break;
        }
    }
}
class Salesperson extends Staff {
    static List<String> names = Arrays.asList("Rachel","Monica","Phoebe","Chandler","Ross","Joey");
    static Namer namer = new Namer(names);
    Salesperson() {
        super();
        type = Enums.StaffType.Salesperson;
        name = namer.getNext();  // every new salesperson gets a new name
        salary = 90; // daily salary
    }

    // Someone is asking this Salesperson to sell to this Buyer
    // We'll return any car we sell for the FNCD to keep track of (null if no sale)
    Vehicle sellVehicle(Buyer b, ArrayList<Vehicle> vList, Announcer announcer, int day) {
        // buyer type determines initial purchase chance
        double saleChance = .7; // needs one
        if (b.type == Enums.BuyerType.WantsOne) saleChance = .4;
        if (b.type == Enums.BuyerType.JustLooking) saleChance = .1;
        // find the most expensive vehicle of the type the buyer wants that isn't broken
        // sales chance +10% if Like New, + 10% if Sparkling
        // if no vehicles of type, find remaining most expensive vehicle and sell at -20%
        ArrayList<Vehicle> desiredList = Vehicle.getVehiclesByType(vList, b.preference);
        Vehicle v;
        v = getMostExpensiveNotBroken(desiredList);  // could be null
        if (v == null) {
            // no unbroken cars of preferred type
            saleChance -= .2;
            v = getMostExpensiveNotBroken(vList);  // could still be null
        }
        if (v == null) {
            outP("Salesperson "+name+" has no car for buyer "+b.name, announcer, day);
            return null;
        }
        else { //sell this car!
            if (v.condition == Enums.Condition.LikeNew) saleChance += .1;
            if (v.cleanliness == Enums.Cleanliness.Sparkling) saleChance += .1;
            saleChance += .1 * v.racesWon;
            double chance = Utility.rnd();
            if (chance<=saleChance) {  // sold!
                bonusEarned += v.sale_bonus;
                staffEarned += v.sale_bonus;
                outP("Buyer "+b.name+" is buying! Salesperson "+name+" gets a bonus of "+Utility.asDollar(v.sale_bonus)+"!", announcer, day);
                outP("Buyer "+b.name+" bought "+v.cleanliness+" "+v.condition+" "+v.name+" for base price of "+Utility.asDollar(v.price()), announcer, day);
                Vehicle v1 = v;
                double addOnChance = Utility.rnd();
                /*
                 * 
                 * Decorator Pattern being implemented into the Sales Person class
                 * 
                 */
                if (addOnChance <= .2){
                    v1 = new WarrantyDecorator(v1);
                }
                if (addOnChance <= .1){
                    v1 = new UndercoatingDecorator(v1);
                }
                if (addOnChance <= .05){
                    v1 = new AAADecorator(v1);
                }
                if (addOnChance <= .4){
                    v1 = new SatRadioDecorator(v1);
                }
                System.out.print(v1.addOn());
                //announcer.publishEvent(v1.addOn(), day);
                v.setPrice(v1.price());

                return v;
            }
            else {  // no sale!
                outP("Buyer "+b.name+" decided not to buy.", announcer, day);
                return null;
            }
        }
    }

    // Little helper for finding most expensive and not broken in a list of vehicles
    // Used twice by salespeople
    Vehicle getMostExpensiveNotBroken(ArrayList<Vehicle> vList) {
        double highPrice = 0;
        int selected = -1;
        for (int index=0;index<vList.size();++index) {
            Vehicle v = vList.get(index);
            if (v.price>highPrice) {
                if (v.condition != Enums.Condition.Broken) {
                    selected = index;
                    highPrice = v.price;
                }
            }
        }
        if (selected == -1) return null;
        else return vList.get(selected);
    }
}

class Driver extends Staff {
    static List<String> names = Arrays.asList("Billy", "Bob", "Joe", "Junior", "Kat", "Caitlyn");
    static Namer namer = new Namer(names);
    int racesWon;
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        name = namer.getNext();  // every new driver gets a new name
        salary = 100; // daily salary
        racesWon = 0;
    }
}