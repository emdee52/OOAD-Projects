public class Doctor extends Job {
    //An example of lazy Instatiation Singelton
    private static String name = "Doctor";
    private static Doctor uniqueInstance;

    private static int startingSalary = 90000;
    private static int maxSalary = 9990000;
    private static int luckyNumber = 2;

    private Player player;
    private Doctor() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Doctor getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Doctor();
        }
        return uniqueInstance;
    }

    public static void assignJob(Player player) {
        if (uniqueInstance.player == null) {
            uniqueInstance.player = player;
            uniqueInstance.setCurrentSalary(startingSalary);
            player.currentJob = uniqueInstance;
        }
    }


    public static void looseJob(Player player) {
        uniqueInstance.player = null;
    }
}
