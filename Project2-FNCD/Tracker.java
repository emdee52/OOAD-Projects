/*
 * 
 * This file is used to help implement the Observer pattern
 * 
 * 
 */

public class Tracker implements Listener{
    double fncdTotal;
    double staffTotal;

    Tracker(double fncdTotal, double staffTotal){
        this.fncdTotal = fncdTotal;
        this.staffTotal = staffTotal;
    }

    @Override
    public void notifyEvent(String event, int day) {
        if (event.charAt(0) != ' ') {
            String[] strArray = event.split(" ");
            if (strArray[1].equals("it")) {
                System.out.println("\nTracker: Day " + day);
                System.out.println("Total money earned by all Staff: " + staffTotal);
                System.out.println("Total money earned by the FNCD: " + fncdTotal);
            }
        }
    }
}