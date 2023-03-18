import java.util.ArrayList;
import java.util.Objects;

// This represents the FNCD business and things they would control
public class FNCD implements SysOut {
    String name;
    ArrayList<Staff> staff;  // folks working
    ArrayList<Staff> departedStaff;   // folks that left
    ArrayList<Vehicle> inventory;   // vehicles at the FNCD
    ArrayList<Vehicle> soldVehicles;   // vehicles the buyers bought
    ArrayList<Vehicle> racingVehicles;
    double moneyEarned;
    private double budget;   // big money pile
    private int dayCount;
    public Announcer announcer;

    FNCD(String name, Announcer ann) {
        this.name = name;
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();
        budget = 100000;  // I changed this just to see additions to the budget happen
        moneyEarned = 0;
        announcer = ann;
    }

    public void setDayCount(int day) {
        dayCount = day;
    }

    double getBudget() {
        return budget;    // I'm keeping this private to be on the safe side
    }
    void moneyIn(double cash) {  // Nothing special about taking money in yet
        budget += cash;
        moneyEarned += cash;
    }
    void moneyOut(double cash) {   // I check for budget overruns on every payout
        budget -= cash;
        if (budget<0) {
            budget += 250000;
            outP(this.name + ": ***Budget overrun*** Added $250K, budget now: " + Utility.asDollar(budget), announcer, dayCount);
        }
    }

    // Here's where I process daily activities
    // I debated about moving the individual activities out to an Activity class
    // It would make the normal day less of a monster maybe, eh...

    void raceDay() {   // Race Day
        outP(this.name + ": Its Racing Time!", announcer, dayCount);

        Enums.VehicleType raceType = Utility.randomEnum(Enums.VehicleType.class); //Gets the type of race

        while(raceType == Enums.VehicleType.Car || raceType == Enums.VehicleType.Electric){ //Makes sure the type of race will be valid type
            raceType = Utility.randomEnum(Enums.VehicleType.class);
        }

        ArrayList<Staff> drivers = Staff.getStaffByType(staff, Enums.StaffType.Driver); //Gets cars and racers from the FNCD
        ArrayList<Vehicle> raceCars = getRaceCars(raceType);

        while(drivers.size() != raceCars.size()){ //Makes sure that there won't be more racers than cars
            drivers.remove(drivers.size() - 1) ;
        }

        ArrayList<String> racingList = generateRacers(drivers); //Get the other racers
        ArrayList<String> placement = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int raceplacer = Utility.rndFromRange(0, racingList.size() - 1);
            int raceCarIdx = Utility.rndFromRange(0, raceCars.size() -1);
            placement.add(racingList.get(raceplacer)); //Randomly adds them into the list and placement is determined by index

            racingList.remove(raceplacer); //Removes racer that way they aren't placed again
            
            outP("Place: " + (i + 1) + " " + placement.get(i), announcer, dayCount);
            if (i < 3) { //Checks if FNCD driver came top 3
                for(Staff s: drivers) {
                    Driver d = (Driver) s;
                    if (d.name == placement.get(i)) {
                        Vehicle carDriven = raceCars.get(raceCarIdx);//determines which car driver used
                        raceCars.remove(raceCarIdx);

                        //adjusts values
                        carDriven.racesWon++;
                        d.racesWon++;
                        d.bonusEarned += carDriven.race_bonus;
                        budget -= carDriven.race_bonus;
                    }
                }
            }
            if(i >= 15){ //Checks if FNCD drivers came bottom 5
                for(Staff s: drivers) {
                    double injuryChance = Utility.rnd();
                    Driver d = (Driver) s;
                    if (d.name == placement.get(i)) {
                        Vehicle carDriven = raceCars.get(raceCarIdx);//determines which car driver used and sets it to broken
                        raceCars.remove(raceCarIdx);
                        outP(carDriven.name + " is now broken.", announcer, dayCount);
                        carDriven.condition = Enums.Condition.Broken;

                        //checks if driver was injured
                        if(injuryChance >= 0.3) {
                            departedStaff.add(d);
                            outP(d.type+" "+ d.name + " is leaving due to injury", announcer, dayCount);
                            staff.remove(d);
                        }
                    }
                    
                }
            }
        }
    }

    ArrayList<Vehicle> getRaceCars(Enums.VehicleType vehicle) { //Returns a list of available race cars depending on type
        ArrayList<Vehicle> AvailableCars = Vehicle.getVehiclesByType(inventory, vehicle);
        ArrayList<Vehicle> raceCars = new ArrayList<>();

        for(int i = 0; i < AvailableCars.size(); i++) {
            if (AvailableCars.get(i).condition != Enums.Condition.Broken && raceCars.size() < 3) {
                raceCars.add(AvailableCars.get(i));
            }
        }

        return raceCars;
    }

    ArrayList<String> generateRacers(ArrayList<Staff> drivers) {//Gets all of the FNCD drivers and creates other drivers and puts them into a list
        ArrayList<String> racers = new ArrayList<>();
        
        for (Staff d : drivers) {
            racers.add(d.name);
            outP(this.name+" FNCD driver participating: " + d.name , announcer, dayCount);
        }
        for (int i = racers.size(); i < 20; i++) {
            racers.add("Racer" + (racers.size() + 1));
            outP("Other driver participating: " + racers.get(i), announcer, dayCount);
        }
        return racers;
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
        ArrayList<Buyer> buyers = new ArrayList<>();
        int buyerCount = Utility.rndFromRange(buyerMin,buyerMax);
        for (int i=1; i<=buyerCount; ++i) buyers.add(new Buyer(this));
        outP("The " +this.name+ " FNCD has "+buyerCount+" buyers today...", announcer, dayCount);
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
    // Factory method implemented for Staff
    void addStaff(Enums.StaffType t) {
        StaffFactory factory = StaffFactory.getFactory(t); // Abstract factory, we won't know what object will call createStaff()
        Staff newStaff = factory.createStaff();
        outP(this.name + ": Hired a new "+newStaff.type+" named "+ newStaff.name, announcer, dayCount);
        staff.add(newStaff);
    }

    // see if we need any vehicles
    void updateInventory() {
        final int numberInInventory = 4;
        for (Enums.VehicleType t : Enums.VehicleType.values()) {
            int typeInList = Vehicle.howManyVehiclesByType(inventory, t);
            outP("Type: " + t.toString(), announcer, dayCount);
            outP("Type in list: " + typeInList, announcer, dayCount);
            int need = numberInInventory - typeInList;
            for (int i = 1; i <= need; ++i) addVehicle(t);
        }

    }

    // add a vehicle of a type to the inventory
    void addVehicle(Enums.VehicleType t) {
        VehicleFactory factory = VehicleFactory.getFactory(t); // Abstract factory, we won't know what object will call createStaff()
        Vehicle v = factory.createVehicle();

        moneyOut(v.cost);  // pay for the vehicle
        outP(this.name + ": Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost), announcer, dayCount);
        inventory.add(v);
    }

    // pay salary to staff and update days worked
    void payStaff() {
        for (Staff s: staff) {
            moneyOut(s.salary);  // money comes from the FNCD
            s.salaryEarned += s.salary;  // they get paid
            Staff.staffEarned += s.salary;
            s.daysWorked += 1; // they worked another day
        }
    }

    // Huh - no one wants to quit my FNCD!
    // I left this as an exercise to the reader...
    void checkForQuitters() {
        int mechanicQuit = 0;
        int internQuit = 0;
        int salesPersonQuit = 0;
        ArrayList<String> namesList = new ArrayList<>();
        for (int i = staff.size() - 1; i>=0; i--) {
            Staff currentStaff = staff.get(i);
            double chance = Utility.rnd();

            if(currentStaff.type == Enums.StaffType.Driver); //Pass over drivers since their quit condition is special

            else if (chance <= .1){
                if(currentStaff.type == Enums.StaffType.Salesperson && salesPersonQuit == 0) { //Only one person of each type can quit
                    //Annonces and adds departing staff to departed staff
                    departedStaff.add(currentStaff);
                    outP(currentStaff.type+" "+ currentStaff.name + " is leaving the "+this.name+" FNCD", announcer, dayCount);
                    namesList.add(currentStaff.name);

                    //Begins process of promoting Intern by removing the intern and bringing them back as the new position
                    ArrayList<Staff> interns = Staff.getStaffByType(staff,  Enums.StaffType.Intern);
                    Staff intern = interns.get(0);
                    outP(intern.type+" "+ intern.name + " has been promoted to " + currentStaff.type + " in the "+this.name+" FNCD", announcer, dayCount);
                    Staff p = intern.promote(intern, currentStaff.type);
                    staff.removeIf ( n -> Objects.equals(n.name, intern.name) && n.type == Enums.StaffType.Intern);
                    staff.add(p);
                    namesList.add(intern.name);

                    staff.removeIf ( n -> Objects.equals(n.name, currentStaff.name)); //Fully removes the departing staff member

                    salesPersonQuit++;
                }
                else if (currentStaff.type == Enums.StaffType.Mechanic && mechanicQuit == 0) { //Same process for mechanic as salesperson above
                    departedStaff.add(currentStaff);
                    outP(currentStaff.type+" "+ currentStaff.name + " is leaving the "+this.name+" FNCD", announcer, dayCount);
                    namesList.add(currentStaff.name);

                    ArrayList<Staff> interns = Staff.getStaffByType(staff,  Enums.StaffType.Intern);
                    Staff intern = interns.get(0);
                    outP(intern.type+" "+ intern.name + " has been promoted to " + currentStaff.type + " in the "+this.name+" FNCD", announcer, dayCount);
                    Staff p = intern.promote(intern, currentStaff.type);
                    staff.removeIf ( n -> Objects.equals(n.name, intern.name) && n.type == Enums.StaffType.Intern);
                    staff.add(p);
                    namesList.add(intern.name);
                    staff.removeIf ( n -> Objects.equals(n.name, currentStaff.name));

                    mechanicQuit++;
                } 
                else if(currentStaff.type == Enums.StaffType.Intern && internQuit == 0){
                    //removes intern and adds them to departed staff
                    departedStaff.add(currentStaff);
                    outP(currentStaff.type+" "+ currentStaff.name + " is leaving the " +this.name+" FNCD", announcer, dayCount);
                    namesList.add(currentStaff.name);

                    internQuit++;
                    staff.removeIf ( n -> Objects.equals(n.name, currentStaff.name));
                }

            }
        }
    }

    void reportOut() {
        // We're all good here, how are you?
        // Quick little summary of happenings, you could do better

        outP(this.name+" FNCD: Vehicles in inventory "+inventory.size(), announcer, dayCount);
        outP(this.name+" FNCD: Vehicles sold count "+soldVehicles.size(), announcer, dayCount);
        outP(this.name+" FNCD: Money in the budget "+ Utility.asDollar(getBudget()), announcer, dayCount);
    }
}
