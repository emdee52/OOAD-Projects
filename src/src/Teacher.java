public class Teacher extends Job {
    //An example of lazy Instatiation Singelton
    private static String name = "Teacher";
    private static Teacher uniqueInstance;

    private static int startingSalary = 40000;
    private static int maxSalary = 50000;
    private static int luckyNumber = 8;

    private Player player;
    private Teacher() {
        this.setStartingSalary(startingSalary);
        this.setMaxSalary(maxSalary);
        this.setLuckyNumber(luckyNumber);
        this.setName(name);
    }

    public Player getPlayer() {
        return uniqueInstance.player;
    }

    public static Teacher getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Teacher();
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
