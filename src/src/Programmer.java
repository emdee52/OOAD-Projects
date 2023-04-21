public class Programmer extends Job {
    //An example of lazy Instatiation Singelton
    private static String name = "Programmer";
    private static Programmer uniqueInstance;

    private static int startingSalary = 80000;
    private static int maxSalary = 100000;
    private static int luckyNumber = 5;

    private Player player;
    private Programmer() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Programmer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Programmer();
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
