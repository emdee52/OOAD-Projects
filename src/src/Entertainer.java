public class Entertainer extends Job{
    //An example of lazy Instatiation Singelton
    private static String name = "Entertainer";
    private static Entertainer uniqueInstance;

    private static int startingSalary = 60000;
    private static int maxSalary = 100000;
    private static int luckyNumber = 7;

    private Player player;
    private Entertainer() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Entertainer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Entertainer();
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
