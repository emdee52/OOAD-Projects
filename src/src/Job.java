import java.util.ArrayList;

public abstract class Job {
    private String name;

    private int startingSalary;
    private int maxSalary;
    private int luckyNumber;
    private int currentSalary;

    public int getCurrentSalary() {return currentSalary;}
    public int getStartingSalary() {return startingSalary;}
    public int getMaxSalary() {return maxSalary;}
    public int getLuckyNumber() {return luckyNumber;}

    public String getName() {return name;}

    public void setCurrentSalary(int salary) {this.currentSalary = salary;}
    public void setStartingSalary(int salary) {this.startingSalary = salary;}
    public void setMaxSalary(int salary) {this.maxSalary = salary;}
    public void setLuckyNumber(int num) {this.luckyNumber = num;}
    public void setName(String name) {this.name = name;}

    public void getRaise() {
        if (this.currentSalary != 0 && this.maxSalary >= currentSalary + 10000) {
            currentSalary += 10000;
        }
    }

    public static ArrayList<Job> getJobChoices(EducationLevel educationLevel) {
        ArrayList<Job> jobs = new ArrayList<Job>();

        if (educationLevel == EducationLevel.Educated) {
            //Programmer, Doctor, Teacher, Lawyer
            if (Programmer.getInstance().getPlayer() == null) {
                jobs.add(Programmer.getInstance());
            }
            if (Doctor.getInstance().getPlayer() == null) {
                jobs.add(Doctor.getInstance());
            }
            if (Teacher.getInstance().getPlayer() == null) {
                jobs.add(Teacher.getInstance());
            }
            if (Lawyer.getInstance().getPlayer() == null) {
                jobs.add(Lawyer.getInstance());
            }
        }
        else { // Not educated
            //Artist, Pro-Athlete, Mechanic, Entertainer
            if (Artist.getInstance().getPlayer() == null) {
                jobs.add(Artist.getInstance());
            }
            if (ProAthlete.getInstance().getPlayer() == null) {
                jobs.add(ProAthlete.getInstance());
            }
            if (Mechanic.getInstance().getPlayer() == null) {
                jobs.add(Mechanic.getInstance());
            }
            if (Entertainer.getInstance().getPlayer() == null) {
                jobs.add(Entertainer.getInstance());
            }
        }
        ArrayList<Job> twojobs = new ArrayList<Job>();
        int jobSize;
        int random;

        while(jobs.size() > 0 && twojobs.size() < 2) {
            jobSize = jobs.size() - 1;
            random = (int) (Math.random() * jobSize);
            twojobs.add(jobs.get(random));
            jobs.remove(random);

        }

        return twojobs;
    }

//    public void assignJob(Player player){}
//    public void looseJob(Player player) {}
}
