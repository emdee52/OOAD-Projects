import java.util.SplittableRandom;

//Coupling
/* Class Vehicle and the subclasses are loosely coupled as changes made in the subclasses
 * dont really affect Vehicle but changes in Vehicle can affect the subclasses but not really
 * they're mostly independent
*/
abstract public class Vehicle {
    SplittableRandom random = new SplittableRandom(); // SplittableRandom allows the use of begin(inclusive) and end(exclusive) parameters
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
        int conditionIndex = random.nextInt(conditions.length); // chooses a random condition index
        this.condition = conditions[conditionIndex]; // random condition based on a random index
        this.costModifier = conditionsCost[conditionIndex]; // costModifier that gets applied to the cost of the Vehicle
        this.cleanliness = rngCleanliness(); // generate a cleanliness value through a random weighted algorithm
        this.sold = false; // vehicles aren't sold when initialized
    }

    private String rngCleanliness() {
        int randGen = random.nextInt(1, 101); // randInt between 1 and 100
        //System.out.println("RandGen: " + randGen);
        if (randGen <= 5) // 5% chance, if randGen is a number between 1-5 out of 100
            return "Sparkling";
        else if (randGen <= 40) // 35% chance, if randGen is a number between 5-40 out of 100
            return "Clean";
        else // 60% chance, if randGen is a number between 41-100 out of 100
            return "Dirty";
    }

    protected String genName() {
        String[] model = {"Dodge", "Toyota", "Ford", "Chevrolet", "Honda", "Volkswagen"};
        String[] perfMakes = {"Coupe", "Roadster", "Hatchback"};
        String[] carMakes = {"Sedan", "SUV", "Wagon"};
        String[] pickupMakes = {"Longbed", "Shortbed", "Dually"};
        int index = random.nextInt(model.length);
        String nameGen = "";
        if (this instanceof Performance) {
            nameGen = "Performance " + model[index] + " " + perfMakes[random.nextInt(perfMakes.length)];
        }
        else if (this instanceof Car) {
            nameGen = "Car " + model[index] + " " + carMakes[random.nextInt(carMakes.length)];
        }
        else if (this instanceof Pickup) {
            nameGen = "Pickup " + model[index] + " " + pickupMakes[random.nextInt(pickupMakes.length)];
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
        System.out.println("\nType:        " + this.getClass());
        System.out.println("Name:        " + getName());
        System.out.println("Condition:   " + getCondition());
        System.out.println("Cleanliness: " + getCleanliness());
        System.out.println("Cost:        " + getCost());
        System.out.println("SalesPrice:  " + getSalesPrice());
        System.out.println("WorkBonus:   " + getWorkBonus());
        System.out.println("Sold?:       " + getStatus());
    }
}

    //Identity
    /*The class names here are a good example of identity rules because the names are unqiue
     * and it is easy to tell what each name means making it even easier to distinguish them
     */
class Performance extends Vehicle {
    public Performance() {
        super();
        this.name = genName();
        this.cost = Math.round(random.nextInt(20000, 40001) * this.costModifier); // Performance vehicle cost will be between $20000 and $40000
        this.salesPrice = this.cost * 2;
        this.workBonus = 2000; // need to find a working value
    }
}

class Car extends Vehicle {
    public Car() {
        super();
        this.name = genName();
        this.cost = Math.round(random.nextInt(10000, 20001) * this.costModifier); // regular Car cost will be between $10000 and $20000
        this.salesPrice = this.cost * 2;
        this.workBonus = 500; // need to find a working value
    }
}

class Pickup extends Vehicle {
    public Pickup() {
        super();
        this.name = genName();
        this.cost = Math.round(random.nextInt(10000, 40001) * this.costModifier); // Pickup vehicle cost will between $10000 and $40000
        this.salesPrice = this.cost * 2;
        this.workBonus = 1250; // need to find a working value
    }
}