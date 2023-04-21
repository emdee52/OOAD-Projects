public class Artist extends Job{
    //An example of lazy Instatiation Singelton
    public static String name = "Artist";
    private static Artist uniqueInstance;

    private static int startingSalary = 40000;
    private static int maxSalary = 60000;
    private static int luckyNumber = 1;

    private Player player;
    private Artist() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Artist getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Artist();
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
