import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Vehicle{
    String name;
    Enums.VehicleType type;
    Enums.Condition condition;
    Enums.Cleanliness cleanliness;
    double cost;
    double price;
    double repair_bonus;
    double wash_bonus;
    double sale_bonus;
    double race_bonus;
    int racesWon;

    Vehicle () {
        // all vehicles have the same cleanliness arrival chance
        double chance = Utility.rnd();
        if (chance <= .05) cleanliness = Enums.Cleanliness.Sparkling;
        else if (chance>.05 && chance<=.4) cleanliness = Enums.Cleanliness.Clean;
        else cleanliness = Enums.Cleanliness.Dirty;
        // all vehicles have the same condition arrival chance (even chance of any)
        condition = Utility.randomEnum(Enums.Condition.class);
        racesWon = 0;
    }

    // utility for getting adjusted cost by condition
    double getCost(int low,int high) {
        double cost = Utility.rndFromRange(low, high);
        if (condition== Enums.Condition.Used) cost = cost*.8;
        if (condition== Enums.Condition.Broken) cost = cost*.5;
        return cost;
    }

    // utility for getting Vehicles by Type
    // You could do this with getClass instead of Type, but I use the enum
    // because it's clearer to me (less Java-y)
    static ArrayList<Vehicle> getVehiclesByType(ArrayList<Vehicle> vehicleList, Enums.VehicleType t) {
        ArrayList<Vehicle> subclassInstances = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.type == t) subclassInstances.add(v);
        }
        return subclassInstances;
    }

    // Utility for finding out how many of a Vehicle there are
    static int howManyVehiclesByType(ArrayList<Vehicle> vehicleList, Enums.VehicleType t) {
        int n = 0;
        for (Vehicle v: vehicleList) {
            if (v.type == t) n++;
        }
        return n;
    }
    public String addOn(){ return ""; }
    public double price(){ return price;}
    public void report() { // for debug only
        System.out.println("\nType:        " + this.getClass());
        System.out.println("Name:        " + name);
        System.out.println("Condition:   " + condition);
        System.out.println("Cleanliness: " + cleanliness);
        System.out.println("Cost:        " + cost);
        System.out.println("SalesPrice:  " + price);
    }
    public void setPrice(double p) {
        this.price = p;
    }
}

class Car extends Vehicle {
    // could make the name list longer to avoid as many duplicates if you like...
    static List<String> names = Arrays.asList("Probe","Escort","Taurus","Fiesta");
    static Namer namer = new Namer(names);
    Car() {
        super();
        type = Enums.VehicleType.Car;
        name = namer.getNext();  // every new car gets a new name
        cost = getCost(10000,20000);
        price = cost * 2;
        repair_bonus = 100;
        wash_bonus = 20;
        sale_bonus = 500;
        race_bonus = 200;
    }
}

class PerfCar extends Vehicle {
    static List<String> names = Arrays.asList("Europa","Cayman","Corvette","Mustang");
    static Namer namer = new Namer(names);
    PerfCar() {
        super();
        type = Enums.VehicleType.PerfCar;
        name = namer.getNext();  // every new perf car gets a unique new name
        cost = getCost(20000,40000);
        price = cost * 2;
        repair_bonus = 300;
        wash_bonus = 100;
        sale_bonus = 1000;
        race_bonus = 400;
    }
}

class Pickup extends Vehicle {
    static List<String> names = Arrays.asList("Ranger","F-250","Colorado","Tundra");
    static Namer namer = new Namer(names);
    Pickup() {
        super();
        type = Enums.VehicleType.Pickup;
        name = namer.getNext();  // every new truck gets a unique new name
        cost = getCost(10000,40000);
        price = cost * 2;
        repair_bonus = 200;
        wash_bonus = 75;
        sale_bonus = 750;
        race_bonus = 300;
    }
}

class Electric extends Vehicle {
    static List<String> names = Arrays.asList("Taycan","Bolt","EQS","EV6");
    static Namer namer = new Namer(names);
    int range;
    Electric() {
        super();
        type = Enums.VehicleType.Electric;
        name = namer.getNext();  // every new electric car gets a unique new name
        cost = getCost(25000,45000);
        price = cost * 2;
        repair_bonus = 200;
        wash_bonus = 75;
        sale_bonus = 750;
        race_bonus = 300;
        range = Utility.rndFromRange(60, 400);
    }
}

class Motorcycle extends Vehicle {
    static List<String> names = Arrays.asList("Grom","Ninja","Softail","CBR1000");
    static Namer namer = new Namer(names);
    double engineSize;
    Random rand = new Random();
    int stdev = 300;
    int mean = 700;
    Motorcycle() {
        super();
        type = Enums.VehicleType.Motorcycle;
        name = namer.getNext();  // every new bike gets a unique new name
        cost = getCost(5000,20000);
        price = cost * 2;
        repair_bonus = 400;
        wash_bonus = 150;
        sale_bonus = 1250;
        race_bonus = 300;
        engineSize = rand.nextGaussian() * stdev + mean; // https://stackoverflow.com/questions/31754209/can-random-nextgaussian-sample-values-from-a-distribution-with-different-mean
    }
}

class MonsterTruck extends Vehicle {
    static List<String> names = Arrays.asList("Batman","Avenger","Bigfoot","Grave Digger", "Predator", "Raminator");
    static Namer namer = new Namer(names);
    MonsterTruck() {
        super();
        type = Enums.VehicleType.MonsterTruck;
        name = namer.getNext();  // every new monster truck gets a unique new name
        cost = getCost(20000,45000);
        price = cost * 2;
        repair_bonus = 500;
        wash_bonus = 120;
        sale_bonus = 1000;
        race_bonus = 350;
    }
}


