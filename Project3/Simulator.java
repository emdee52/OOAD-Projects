import java.util.ArrayList;

// Simulator to cycle for select number of days
public class Simulator implements SysOut {
    final int numDays;
    Enums.DayOfWeek dayOfWeek;
    public Announcer announcer;
    public Logger logger;
    public Tracker tracker;
    ArrayList<FNCD> FNCDTracker;
    Simulator() {
        numDays = 30;  // magic number for days to run here
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);  // we'll start on a random day (for fun)
    }

    // cycling endlessly through enum values
    // https://stackoverflow.com/questions/34159413/java-get-next-enum-value-or-start-from-first
    public Enums.DayOfWeek getNextDay(Enums.DayOfWeek e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        Enums.DayOfWeek[] days = Enums.DayOfWeek.values();
        nextIndex %= days.length;
        return days[nextIndex];
    }

    void runParallel() {
        announcer = new Announcer();
        logger = Logger.getInstance();
        Tracker tracker = Tracker.getInstance();
        announcer.addListener(logger);
        announcer.addListener(tracker);

        FNCD northFNCD = new FNCD("North", announcer);
        FNCD southFNCD = new FNCD("South", announcer);
        for (int day = 1; day <= numDays; ++day) {
            out(">>> Start Simulation Day "+day+" "+dayOfWeek);

            open(northFNCD, day);
            open(southFNCD, day);

            // washing - tell the interns to do the washing up
            wash(northFNCD, day);
            wash(southFNCD, day);

            // repairing - tell the mechanics to do their repairing
            repair(northFNCD, day);
            repair(southFNCD, day);

            // selling
            sell(northFNCD, day);
            sell(southFNCD, day);

            // ending
            // pay all the staff their salaries
            northFNCD.payStaff();
            southFNCD.payStaff();

            // anyone quitting? replace with an intern (if not an intern)
            northFNCD.checkForQuitters();
            southFNCD.checkForQuitters();

            if (Enums.DayOfWeek.Wed == dayOfWeek || Enums.DayOfWeek.Sun == dayOfWeek) {
                northFNCD.raceDay();
                southFNCD.raceDay();
            }
            // daily report
            northFNCD.reportOut();
            southFNCD.reportOut();

            tracker.updateFncdTotal(northFNCD.moneyEarned + southFNCD.moneyEarned);
            tracker.updateStaffTotal(Staff.staffEarned);

            outP("That's it for the day.", announcer, day);
            out(">>> End Simulation Day "+day+" "+dayOfWeek+"\n");
            dayOfWeek = getNextDay(dayOfWeek);  // increment to the next day
        }
    }

    void open(FNCD fncd, int day){
        outP("The " + fncd.name + " FNCD is opening...", announcer, day);
        fncd.hireNewStaff();    // hire up to 3 of each staff type
        fncd.updateInventory();  // buy up to 4 of each type
    }

    void wash(FNCD fncd, int day){
        outP("The " + fncd.name + " FNCD interns are washing...", announcer, day);
        ArrayList<Staff> interns = Staff.getStaffByType(fncd.staff, Enums.StaffType.Intern);
        for (Staff s : interns) {
            Intern i = (Intern) s;
            i.washVehicles(fncd.inventory, announcer, day, i);
        }
    }

    void repair(FNCD fncd, int day){
        outP("The " + fncd.name + " FNCD mechanics are repairing...", announcer, day);
        ArrayList<Staff> mechanics = Staff.getStaffByType(fncd.staff, Enums.StaffType.Mechanic);
        for (Staff s : mechanics) {
            Mechanic m = (Mechanic) s;
            m.repairVehicles(fncd.inventory, announcer, day);
        }
    }

    void sell(FNCD fncd, int day){
        outP("The " + fncd.name + " FNCD salespeople are selling...", announcer, day);
        ArrayList<Buyer> buyers = fncd.getBuyers(dayOfWeek);
        ArrayList<Staff> salespeople = Staff.getStaffByType(fncd.staff, Enums.StaffType.Salesperson);
        // tell a random salesperson to sell each buyer a car - may get bonus
        for (Buyer b : buyers) {
            outP("Buyer " + b.name + " wants a " + b.preference + " (" + b.type + ")", announcer, day);
            int randomSeller = Utility.rndFromRange(0, salespeople.size() - 1);
            Salesperson seller = (Salesperson) salespeople.get(randomSeller);
            Vehicle vSold = seller.sellVehicle(b, fncd.inventory, announcer, day);
            // What the FNCD needs to do if a car is sold - change budget and inventory
            if (vSold != null) {
                fncd.soldVehicles.add(vSold);
                fncd.moneyIn(vSold.price);
                fncd.inventory.removeIf(n -> n.name == vSold.name);
            }
        }
    }
}
