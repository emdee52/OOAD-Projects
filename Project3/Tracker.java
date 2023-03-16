/*
 * 
 * This file is used to help implement the Observer pattern and singleton pattern
 * The tracker singleton has been implemented using the eager method
 * 
 */

import java.text.DecimalFormat;

public class Tracker implements Listener {
    private static final Tracker instance = new Tracker(0, 0); // Eager method for singleton instantiation

    private double fncdTotal;
    private double staffTotal;
    private DecimalFormat f = new DecimalFormat("##.00");

    private Tracker(double fncdTotal, double staffTotal) {
        this.fncdTotal = fncdTotal;
        this.staffTotal = staffTotal;
    }

    public static Tracker getInstance() {
        return instance;
    }

    public void updateFncdTotal(double amount) {
        fncdTotal = amount;
    }

    public void updateStaffTotal(double amount) {
        staffTotal = amount;
    }

    @Override
    public void notifyEvent(String event, int day) {
        if (event.charAt(0) != ' ') {
            String[] strArray = event.split(" ");
            if (strArray[1].equals("it")) {
                System.out.println("\nTracker: Day " + day);
                System.out.println("Total money earned by all Staff: $" + f.format(staffTotal));
                System.out.println("Total money earned by all FNCDs: $" + f.format(fncdTotal));
            }
        }
    }
}