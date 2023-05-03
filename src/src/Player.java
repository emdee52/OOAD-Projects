import java.util.ArrayList;
import java.util.Random;


public class Player {
    String name;
    int money;
    EducationLevel educationLevel;
    int numberOfKids;
    public Job currentJob;
    public int playerNumber = 0;
    int actionCards = 0;
    int kids = 0;
    int turnOrderSpin = 0;
    int tileNumber = 0;
    private static final Random Rand = new Random();

    Player(int playerNum) {
        playerNumber = playerNum;
    }

    //Tile currentTile;

    //An example of Eager Instantiation of an Object Pool
    private static ArrayList<Player> uniqueInstances = new ArrayList<Player>();

    public static ArrayList<Player> getUniqueInstances() {
        return uniqueInstances;
    }

    public static int getNumberOfPlayers() {
        return uniqueInstances.size();
    }

    public static int addNewPlayer(int num) {
        if (getNumberOfPlayers() >= 4) {return 1;}
        else {
            Player p = new Player(num);
            uniqueInstances.add(p);
            return 0;
        }
    }

    public void addMoney(int cash) {
        money += cash;
    }

    public int spin() {
        Random random = new Random();
        int spinResult = random.nextInt(10) + 1;
        System.out.println(getName() + " spun the spinner and it landed on " + spinResult);
        return spinResult;
    }

    public String getName() { return name;}


//    public void assignJob(Job job) {
//        currentJob = job;
//        job.assignJob(this);
//    }
}
