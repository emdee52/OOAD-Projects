public class Lawyer extends Job {
    //An example of lazy Instatiation Singelton
    private static String name = "Lawyer";
    private static Lawyer uniqueInstance;

    private static int startingSalary = 80000;
    private static int maxSalary = 150000;
    private static int luckyNumber = 3;

    private Player player;
    private Lawyer() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Lawyer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Lawyer();
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
