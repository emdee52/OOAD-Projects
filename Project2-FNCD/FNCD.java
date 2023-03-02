import java.util.ArrayList;

// This represents the FNCD business and things they would control
public class FNCD implements SysOut {
    ArrayList<Staff> staff;  // folks working
    ArrayList<Staff> departedStaff;   // folks that left
    ArrayList<Vehicle> inventory;   // vehicles at the FNCD
    ArrayList<Vehicle> soldVehicles;   // vehicles the buyers bought
    ArrayList<Vehicle> racingVehicles;
    private double budget;   // big money pile
    FNCD() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();
        budget = 100000;  // I changed this just to see additions to the budget happen
    }
    double getBudget() {
        return budget;    // I'm keeping this private to be on the safe side
    }
    void moneyIn(double cash) {  // Nothing special about taking money in yet
        budget += cash;
    }
    void moneyOut(double cash) {   // I check for budget overruns on every payout
        budget -= cash;
        if (budget<0) {
            budget += 250000;
            out("***Budget overrun*** Added $250K, budget now: " + Utility.asDollar(budget));
        }
    }

    // Here's where I process daily activities
    // I debated about moving the individual activities out to an Activity class
    // It would make the normal day less of a monster maybe, eh...

    void raceDay(Enums.DayOfWeek day) {   // Race Day
        normalDay(day); //Continue normal day operations

        out("Its Racing Time!");

        Enums.VehicleType raceType = Utility.randomEnum(Enums.VehicleType.class);

        while(raceType == Enums.VehicleType.Car || raceType == Enums.VehicleType.Electric){
            raceType = Utility.randomEnum(Enums.VehicleType.class);
        }

        ArrayList<Staff> drivers = Staff.getStaffByType(staff, Enums.StaffType.Driver);
        ArrayList<String> racingList = generateRacers(drivers);
        ArrayList<String> placement = new ArrayList<String>();
        ArrayList<Vehicle> raceCars = getRaceCars(raceType);

        for (int i = 0; i < 20; i++) {
            int raceplacer = Utility.rndFromRange(0, racingList.size() - 1);
            placement.add(racingList.get(raceplacer));
            racingList.remove(raceplacer);
            
            out("Place: " + (i + 1) + " " + placement.get(i));
            if (i < 3) {
                for(Staff s: drivers) {
                    Driver d = (Driver) s;
                    if (d.name == placement.get(i)) {
                        Vehicle carDriven = raceCars.get(Utility.rndFromRange(0, raceCars.size() -1));
                        carDriven.racesWon++;
                        d.racesWon++;
                        d.bonusEarned += carDriven.race_bonus;
                    }
                }
            }
            if(i >= 15){
                for(Staff s: drivers) {
                    Driver d = (Driver) s;
                    if (d.name == placement.get(i)) {
                        Vehicle carDriven = raceCars.get(Utility.rndFromRange(0, raceCars.size() -1));
                        carDriven.condition = Enums.Condition.Broken;
                    }
                }
            }
        }
    }

    ArrayList<Vehicle> getRaceCars(Enums.VehicleType vehicle) {
        ArrayList<Vehicle> AvailableCars = Vehicle.getVehiclesByType(inventory, vehicle);
        ArrayList<Vehicle> raceCars = new ArrayList<Vehicle>();

        for(int i = 0; i < AvailableCars.size(); i++) {
            if (AvailableCars.get(i).condition != Enums.Condition.Broken && raceCars.size() < 3) {
                raceCars.add(AvailableCars.get(i));
            }
        }

        return raceCars;
    }

    ArrayList<String> generateRacers(ArrayList<Staff> drivers) {//Gets all of the FNCD drivers and creates other drivers and puts them into a list
        ArrayList<String> racers = new ArrayList<String>();
        
        for (Staff d : drivers) {
            racers.add(d.name);
        }
        for (int i = racers.size(); i < 20; i++) {
            racers.add("Racer" + (racers.size() + 1));
        }
        return racers;
    }

    

    void normalDay(Enums.DayOfWeek day) {  // On a normal day, we do all the activities

        // opening
        out("The FNCD is opening...");
        hireNewStaff();    // hire up to 3 of each staff type
        updateInventory();  // buy up to 4 of each type

        // washing - tell the interns to do the washing up
        out("The FNCD interns are washing...");
        ArrayList<Staff> interns = Staff.getStaffByType(staff, Enums.StaffType.Intern);
        for (Staff s:interns) {
            Intern i = (Intern) s;
            i.washVehicles(inventory);
        }

        // repairing - tell the mechanics to do their repairing
        out("The FNCD mechanics are repairing...");
        ArrayList<Staff> mechanics = Staff.getStaffByType(staff, Enums.StaffType.Mechanic);
        for (Staff s:mechanics) {
            Mechanic m = (Mechanic) s;
            m.repairVehicles(inventory);
        }

        // selling
        out("The FNCD salespeople are selling...");
        ArrayList<Buyer> buyers = getBuyers(day);
        ArrayList<Staff> salespeople = Staff.getStaffByType(staff, Enums.StaffType.Salesperson);
        // tell a random salesperson to sell each buyer a car - may get bonus
        for(Buyer b: buyers) {
            out("Buyer "+b.name+" wants a "+b.preference+" ("+b.type+")");
            int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
            Salesperson seller = (Salesperson) salespeople.get(randomSeller);
            Vehicle vSold = seller.sellVehicle(b, inventory);
            // What the FNCD needs to do if a car is sold - change budget and inventory
            if (vSold != null) {
                soldVehicles.add(vSold);
                moneyIn(vSold.price);
                inventory.removeIf ( n -> n.name == vSold.name);
            }
        }

        // ending
        // pay all the staff their salaries
        payStaff();
        // anyone quitting? replace with an intern (if not an intern)
        checkForQuitters();
        // daily report
        reportOut();
    }

    // generate buyers
    ArrayList<Buyer> getBuyers(Enums.DayOfWeek day) {
        // 0 to 5 buyers arrive (2-8 on Fri/Sat)
        int buyerMin = 0;  //normal Mon-Thur
        int buyerMax = 5;
        if (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat) {
            buyerMin = 2;
            buyerMax = 8;
        }
        ArrayList<Buyer> buyers = new ArrayList<Buyer>();
        int buyerCount = Utility.rndFromRange(buyerMin,buyerMax);
        for (int i=1; i<=buyerCount; ++i) buyers.add(new Buyer());
        out("The FNCD has "+buyerCount+" buyers today...");
        return buyers;
    }

    // see if we need any new hires
    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    // adding staff
    // smells like we need a factory or something...
    void addStaff(Enums.StaffType t) {
        Staff newStaff = null;
        if (t == Enums.StaffType.Intern) newStaff = new Intern();
        if (t == Enums.StaffType.Mechanic) newStaff = new Mechanic();
        if (t == Enums.StaffType.Salesperson) newStaff = new Salesperson();
        if (t == Enums.StaffType.Driver) newStaff = new Driver();
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

    // see if we need any vehicles
    void updateInventory() {
        final int numberInInventory = 4;
        for (Enums.VehicleType t : Enums.VehicleType.values()) {
            int typeInList = Vehicle.howManyVehiclesByType(inventory, t);
            int need = numberInInventory - typeInList;
            for (int i = 1; i<=need; ++i) addVehicle(t);
        }

    }

    // add a vehicle of a type to the inventory
    void addVehicle(Enums.VehicleType t) {
        Vehicle v = null;
        if (t == Enums.VehicleType.Car) v = new Car();
        if (t == Enums.VehicleType.PerfCar) v = new PerfCar();
        if (t == Enums.VehicleType.Pickup) v = new Pickup();
        if (t == Enums.VehicleType.Electric) v = new Electric();
        if (t == Enums.VehicleType.Motorcycle) v = new Motorcycle();
        if (t == Enums.VehicleType.MonsterTruck) v = new MonsterTruck();
        moneyOut(v.cost);  // pay for the vehicle
        out ("Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
        inventory.add(v);
    }

    // pay salary to staff and update days worked
    void payStaff() {
        for (Staff s: staff) {
            moneyOut(s.salary);  // money comes from the FNCD
            s.salaryEarned += s.salary;  // they get paid
            s.daysWorked += 1; // they worked another day
        }
    }

    // Huh - no one wants to quit my FNCD!
    // I left this as an exercise to the reader...
    void checkForQuitters() {
        out("No-one on the staff is leaving!");

        // I would check the percentages here
        // Move quitters to the departedStaff list
        // If an intern quits, you're good
        // If a mechanic or a salesperson quits
        // Remove an intern from the staff and use their properties to
        // create the new mechanic or salesperson
    }

    void reportOut() {
        // We're all good here, how are you?
        // Quick little summary of happenings, you could do better

        out("Vehicles in inventory "+inventory.size());
        out("Vehicles sold count "+soldVehicles.size());
        out("Money in the budget "+ Utility.asDollar(getBudget()));
    }
}
