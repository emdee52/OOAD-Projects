import java.util.ArrayList;

public class Player {
    public String name;
    int money;
    EducationLevel educationLevel;
    int numberOfKids;
    public Job currentJob;

    //Tile currentTile;

    //An example of Eager Instantiation of an Object Pool
    private static ArrayList<Player> uniqueInstances = new ArrayList<Player>();

    Player(String name) {
        this.name = name;
    }

    public static ArrayList<Player> getUniqueInstances() {
        return uniqueInstances;
    }

    public static int getNumberOfPlayers() {
        return uniqueInstances.size();
    }

    public static int addNewPlayer(String name) {
        if (getNumberOfPlayers() >= 4) {return 1;}
        else {
            Player p = new Player(name);
            uniqueInstances.add(p);
            return 0;
        }
    }

//    public void assignJob(Job job) {
//        currentJob = job;
//        job.assignJob(this);
//    }
}
