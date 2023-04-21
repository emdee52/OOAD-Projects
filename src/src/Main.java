import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        // TESTS FOR PLAYER
        Player.addNewPlayer(1);
        System.out.println(Player.getUniqueInstances().get(0).playerNumber);

        // TESTS FOR JOBS
//        Player p = Player.getUniqueInstances().get(0);
//        Artist.getInstance().assignJob(p);
//        System.out.println(p.currentJob.getCurrentSalary());

        ArrayList<Job> jobs = Job.getJobChoices(EducationLevel.Uneducated);

        for (int i = 0; i < jobs.size(); i++) {
            System.out.println(jobs.get(i).getName());
        }

        ArrayList<Job> jobs2 = Job.getJobChoices(EducationLevel.Educated);

        for (int i = 0; i < jobs2.size(); i++) {
            System.out.println(jobs2.get(i).getName());
        }

        //System.out.println(Job.getJobChoices(EducationLevel.Uneducated).get(3).getStartingSalary());
    }
}