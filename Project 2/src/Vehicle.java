import java.util.Random;
import java.util.SplittableRandom;

abstract public class Vehicle {
    Random rand = new Random();
    protected String condition; // randomly chosen from (Like New, Used, Broken)
    protected double costModifier; // modifier applied to cost
    protected String cleanliness; // (5% Sparking, 35% Clean, 60% Dirty)
    protected String name;
    protected double cost; // price FNCD pays to buy the vehicle
    protected double salesPrice; // 2x final cost
    protected double workBonus; // bonus $ staff receive for successful washing, repairing, sales
    protected boolean sold; // whether the car has been sold or is still in FNCD inventory

    protected Vehicle() { // Constructor that applies for all vehicles regardless of the "type"(subclass) of Vehicle
        String[] conditions = {"Like New", "Used", "Broken"}; // conditions
        double[] conditionsCost = {1.0, .8, .5}; // the cost modifier for the condition generate
        int conditionIndex = rand.nextInt(conditions.length); // chooses a random condition index
        this.condition = conditions[conditionIndex]; // random condition based on a random index
        this.costModifier = conditionsCost[conditionIndex]; // costModifier that gets applied to the cost of the Vehicle
        this.cleanliness = rngCleanliness(); // generate a cleanliness value through a random weighted algorithm
        this.sold = false; // vehicles aren't sold when initialized
    }

    public static String rngCleanliness() {
        SplittableRandom random = new SplittableRandom(); // SplittableRandom allows the use of begin(inclusive) and end(exclusive) parameters
        int randGen = random.nextInt(1, 101); // randInt between 1 and 100
        //System.out.println("RandGen: " + randGen);
        if (randGen <= 5) // 5% chance, if randGen is a number between 1-5 out of 100
            return "Sparkling";
        else if (randGen <= 40) // 35% chance, if randGen is a number between 5-40 out of 100
            return "Clean";
        else // 60% chance, if randGen is a number between 41-100 out of 100
            return "Dirty";
    }

    protected String genName() { // needs to be updated with better names
        String[] model = {"Dodge", "Toyota", "Ford"};
        String[] make = {"Coupe", "Sedan", "Truck"};
        int index = rand.nextInt(model.length);
        String nameGen = "";
        if (this instanceof Performance) {
            nameGen = model[index] + " Super" + make[index];
        }
        else if (this instanceof Car) {
            nameGen = model[index] + " " + make[index] + " Touring";
        }
        else if (this instanceof Pickup) {
            nameGen = model[index] + " " + make[index] + " 1500";
        }
        else
            System.err.println("ERROR: Not a vehicle");

        return nameGen;
    }
    // Getters
    public String getName() {
        return this.name;
    }

    public String getCleanliness() {
        return this.cleanliness;
    }

    public String getCondition() {
        return this.condition;
    }

    public Double getCost() {
        return this.cost;
    }

    public Double getSalesPrice() {
        return this.salesPrice;
    }

    public Double getWorkBonus() {
        return this.workBonus;
    }

    public Boolean getStatus() {
        return this.sold;
    }

    // Setters
    public void setName(String newName) {
        this.name = newName;
    }

    public void setCleanliness(String newCleanliness) {
        this.cleanliness = newCleanliness;
    }

    public void setCondition(String newCondition) {
        this.condition = newCondition;
    }

    public void setCost(double newCost) {
        this.cost = newCost;
    }

    public void setSalesPrice(double newPrice) {
        this.salesPrice = newPrice;
    }

    public void setWorkBonus(double newBonus) {
        this.workBonus = newBonus;
    }

    public void setStatus(boolean newStatus) {
        this.sold = newStatus;
    }
    public void report() { // for debug only
        System.out.println(getName() + " " + this.getClass());
        System.out.println(getCleanliness());
        System.out.println(getCondition());
        System.out.println(getCost());
        System.out.println(getSalesPrice());
        System.out.println(getWorkBonus());
        System.out.println(getStatus());
        System.out.println();
    }
}

class Performance extends Vehicle {
    Random rand = new Random();
    public Performance() {
        super();
        this.name = genName();
        this.cost = Math.floor((rand.nextInt(40000 - 20000 + 1) + 20000) * this.costModifier); // The cost of a Performance Car will be between $20000 and $40000
        this.salesPrice = this.cost * 2;
        this.workBonus = 0; // need to find a working value
    }

}

class Car extends Vehicle {
    Random rand = new Random();
    public Car() {
        super();
        this.name = genName();
        this.cost = Math.floor((rand.nextInt(20000 - 10000 + 1) + 20000) * this.costModifier); // The cost of a regular car will be $10000 to $20000
        this.salesPrice = this.cost * 2;
        this.workBonus = 0; // need to find a working value
    }
}

class Pickup extends Vehicle {
    Random rand = new Random();
    public Pickup() {
        super();
        this.name = genName();
        this.cost = Math.floor((rand.nextInt(40000 - 10000 + 1) + 20000) * this.costModifier); // The cost of a Pickup will be $10000 to $40000
        this.salesPrice = this.cost * 2;
        this.workBonus = 0; // need to find a working value
    }

}