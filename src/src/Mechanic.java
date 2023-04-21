public class Mechanic extends Job{
    //An example of lazy Instatiation Singelton
    private static String name = "Mechanic";
    private static Mechanic uniqueInstance;

    private static int startingSalary = 50000;
    private static int maxSalary = 60000;
    private static int luckyNumber = 6;

    private Player player;
    private Mechanic() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Mechanic getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Mechanic();
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
