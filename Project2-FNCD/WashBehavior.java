import java.util.ArrayList;

/*
 * 
 * This file was used to implement the Strategy pattern for wash behavior
 * 
 */
public interface WashBehavior extends SysOut{
    public void wash(ArrayList<Vehicle> vList, Announcer announcer, int day, Intern I);
}

class Chemical implements WashBehavior{
    public void wash(ArrayList<Vehicle> vList, Announcer announcer, int day, Intern I) {
        int washCount = 0;
        Enums.Cleanliness startAs;
        double chance = Utility.rnd();
        double conditionChance = Utility.rnd();
        for (Vehicle v: vList) { //Clean first dirty car seen
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;
                if (chance <= .8) v.cleanliness = Enums.Cleanliness.Clean;
                if (chance >.8 && chance <=.9) {
                    v.cleanliness = Enums.Cleanliness.Sparkling;
                    I.bonusEarned += v.wash_bonus;
                    outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                }
                if (conditionChance <=.1) {
                    v.condition = Enums.Condition.Broken;
                    outP(v.name + " became " + v.condition, announcer, day);
                }
                outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method", announcer, day);
                if (washCount == 2) break;
                out("Wash Count: " + washCount);
            }
        }
        if (washCount<2) { //This goes through the clean list of cars if no dirty cars
            for (Vehicle v: vList) {
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    startAs = Enums.Cleanliness.Clean;
                    washCount += 1;
                    if (chance <= .1) v.cleanliness = Enums.Cleanliness.Dirty;
                    if (chance > .1 && chance <= .2) {
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        I.bonusEarned += v.wash_bonus;
                        outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                    }
                    if (conditionChance <=.1) {
                        v.condition = Enums.Condition.Broken;
                        outP(v.name + " became " + v.condition, announcer, day);
                    }
                    outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(5, this.getClass().toString().length()) + " method", announcer, day);
                    if (washCount == 2) break;
                    out("Wash Count: " + washCount);
                }
                
            }
        }
    }
}

class ElbowGrease implements WashBehavior { //Same behavior as above except adjusted cleaning odds
    public void wash(ArrayList<Vehicle> vList, Announcer announcer, int day, Intern I) {
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
                    outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                }
                if (conditionChance <=.1) {
                    v.condition = Enums.Condition.LikeNew;
                    outP(v.name + " became " + v.condition, announcer, day);
                }
                outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method", announcer, day);
                if (washCount == 2) break;
                out("Wash Count: " + washCount);
            };
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
                        outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                    }
                    if (conditionChance <=.1) {
                        v.condition = Enums.Condition.LikeNew;
                        outP(v.name + " became " + v.condition, announcer, day);
                    }
                    outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method", announcer, day);
                    if (washCount == 2) break;
                    out("Wash Count: " + washCount);
                }
            }
        }
    }
}

class Detailed implements WashBehavior { //Same behavior as above except adjusted cleaning odds
    public void wash(ArrayList<Vehicle> vList, Announcer announcer, int day, Intern I) {
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
                    outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                }
                outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method", announcer, day);
                if (washCount == 2) break;
                out("Wash Count: " + washCount);
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
                        outP("Intern "+I.name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"!", announcer, day);
                    }
                    outP("Intern "+I.name+" washed "+v.name+" "+startAs+" to "+v.cleanliness + " using " + this.getClass().toString().substring(6, this.getClass().toString().length()) + " method", announcer, day);
                    if (washCount == 2) break;
                    out("Wash Count: " + washCount);
                }
            }
        }
    }
}