import java.util.ArrayList;

public abstract class Job {

    private int startingSalary;
    private int maxSalary;
    private int luckyNumber;
    private int currentSalary;

    public int getCurrentSalary() {return currentSalary;}
    public int getStartingSalary() {return startingSalary;}
    public int getMaxSalary() {return maxSalary;}
    public int getLuckyNumber() {return luckyNumber;}

    public void setCurrentSalary(int salary) {this.currentSalary = salary;}
    public void setStartingSalary(int salary) {this.startingSalary = salary;}
    public void setMaxSalary(int salary) {this.maxSalary = salary;}
    public void setLuckyNumber(int num) {this.luckyNumber = num;}

    public void getRaise() {
        if (this.currentSalary != 0 && this.maxSalary >= currentSalary + 10000) {
            currentSalary += 10000;
        }
    }

    public static ArrayList<Job> getJobChoices(EducationLevel educationLevel) {
        ArrayList<Job> jobs = new ArrayList<Job>();

        if (educationLevel == EducationLevel.Educated) {
            //TODO add all jobs that aren't assigned to list
        }
        else { // Not educated
            //TODO add all jobs that aren't assigned to list
        }
        //TODO Return one to two of the options

        return jobs;
    }

//    public void assignJob(Player player){}
//    public void looseJob(Player player) {}
}
