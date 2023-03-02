import java.util.ArrayList;

public interface WashBehavior{
    public void wash(ArrayList<Vehicle> vList, Intern I);
}

class Chemical implements WashBehavior{
    public void wash(ArrayList<Vehicle> vList, Intern I) {
        int washCount = 0;
        Enums.Cleanliness startAs;
        double chance = Utility.rnd();
        double conditionChance = Utility.rnd();
        for (Vehicle v: vList) {
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                if (chance <= .8) v.cleanliness = Enums.Cleanliness.Clean;
                if (chance >.8 && chance <=.9) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    I.bonusEarned += v.wash_bonus;
                    System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                if (conditionChance <=.1) {
                    v.condition = Enums.Condition.Broken;
                    System.out.println(v.name + " became " + v.condition);
                }
                System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method");
                if (washCount == 2) break;
            }
        }
        if (washCount<2) {
            for (Vehicle v: vList) {
                washCount += 1;
                startAs = Enums.Cleanliness.Clean;
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    if (chance <= .1) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance > .1 && chance <= .2) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        I.bonusEarned += v.wash_bonus;
                        System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    if (conditionChance <=.1) {
                        v.condition = Enums.Condition.Broken;
                        System.out.println(v.name + " became " + v.condition);
                    }
                    System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(5, this.getClass().toString().length()) + " method");
                    if (washCount == 2) break;
                }
            }
        }
    }
}

class ElbowGrease implements WashBehavior {
    public void wash(ArrayList<Vehicle> vList, Intern I) {
        int washCount = 0;
        Enums.Cleanliness startAs;
        double chance = Utility.rnd();
        double conditionChance = Utility.rnd();
        for (Vehicle v: vList) {
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                if (chance <= .7) v.cleanliness = Enums.Cleanliness.Clean;
                if (chance >.7 && chance <=.75) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    I.bonusEarned += v.wash_bonus;
                    System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                if (conditionChance <=.1) {
                    v.condition = Enums.Condition.LikeNew;
                    System.out.println(v.name + " became " + v.condition);
                }
                System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method");
                if (washCount == 2) break;
            }
        }
        if (washCount<2) {
            for (Vehicle v: vList) {
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    startAs = Enums.Cleanliness.Clean;
                    if (chance <= .15) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance >.15 && chance <=.30) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        I.bonusEarned += v.wash_bonus;
                        System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    if (conditionChance <=.1) {
                        v.condition = Enums.Condition.LikeNew;
                        System.out.println(v.name + " became " + v.condition);
                    }
                    System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method");
                    if (washCount == 2) break;
                }
            }
        }
    }
}

class Detailed implements WashBehavior {
    public void wash(ArrayList<Vehicle> vList, Intern I) {
        int washCount = 0;
        Enums.Cleanliness startAs;
        double chance = Utility.rnd();
        for (Vehicle v: vList) {
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                if (chance <= .6) v.cleanliness = Enums.Cleanliness.Clean;
                if (chance >.6 && chance <=.8) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    I.bonusEarned += v.wash_bonus;
                    System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                }
                System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method");
                if (washCount == 2) break;
            }
        }
        if (washCount<2) {
            for (Vehicle v: vList) {
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    startAs = Enums.Cleanliness.Clean;
                    if (chance <= .05) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance >.05 && chance <=.45) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        I.bonusEarned += v.wash_bonus;
                        System.out.println("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!");
                    }
                    System.out.println("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method");
                    if (washCount == 2) break;
                }
            }
        }
    }
}