public class ProAthlete extends Job {
    //An example of lazy Instatiation Singelton
    private static String name = "Pro Athlete";
    private static ProAthlete uniqueInstance;

    private static int startingSalary = 70000;
    private static int maxSalary = 100000;
    private static int luckyNumber = 4;

    private Player player;
    private ProAthlete() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static ProAthlete getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ProAthlete();
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
