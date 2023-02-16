import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

//Encapsulation
/*The staff class is a good example of encapsulation as the class bundles together 
 * many different data types like classes(mechanic, intern, etc.), ints, strings, etc. You
 * can also access them all through a single point and that is the class Staff
*/
public class Staff {
    DecimalFormat df = new DecimalFormat("##.00"); // Rounds to 2 decimal places
    Random random = new Random();
    static private int IdCounter; //used to keep track of each instance of staff
    protected int employeeID; //also the same as employee name but uses ID for unique identifier
    protected int daysWorked;
    protected double bonus;
    protected double salary;
    protected double totalPay; //keeps track of total salary earned by employee
    protected double totalBonus; //keeps track of total bonus earned by employee
    protected boolean employed; //keeps track of employment staus
    private String type;

    public Staff() { //Constructor applying to all staff regardless of posistion
        daysWorked = 0;
        bonus = 0;
        employed = true;
    }

    //Cohesion
    /*GenID is an example of high cohesion because ID generator is used to create IDs
     * for each instance of Staff and that is it's sole purpose and it used multiple times
     * mainly in the constructor arguments of each class below
     */

    public int genID() {
        int Id = getIDCounter();
        Id++; //increments ID Counter
        setIDCounter(Id);
        return Id;
    }

    public String getType(){
        return this.type;
    }

    public void addBonus(double bonus) { //adds bonus to bonus already received
        this.bonus = this.bonus + bonus;
    }

    public Staff promote(Staff intern, int newPosition) { //promote an intern; newPosition used to determine whether they become a mechanic(0) or salesPerson(1)
        int currentID = intern.employeeID; //Get current values that need to be transferred
        int daysWorked = intern.daysWorked;
        double currentBonus = intern.bonus;

        if (newPosition == 0) {         //Mechanic Position
            Staff newPos = new mechanic(); //Create Position
            intern = newPos;                //Transfer Position
            intern.employeeID = currentID;  //Transfer all values that way inter stats arent reset
            intern.daysWorked = daysWorked;
            intern.bonus = currentBonus;
        }
        else if (newPosition == 1) {    //Sales Person Position
            Staff newPos = new salesPerson();
            intern = newPos;
            intern.employeeID = currentID;
            intern.daysWorked = daysWorked;
            intern.bonus = currentBonus;
        }
        else {
            System.err.println("Error: Invalid Position");
        }

        return intern;
    }

    public void quit(ArrayList<Staff> staff) { //updates whether an employee has quit
        this.employed = false;
    }

    //getter functions
    public int getIDCounter() {
        return IdCounter;
    }
    public int getEmployeeID() {
        return employeeID;
    }
    public int getDaysWorked() {
        return this.daysWorked;
    }
    public double getBonus() {
        return this.bonus;
    }
    public double getSalary() {
        return this.salary;
    }
    public double getTotalPay() {
        return this.totalPay;
    }
    public double getTotalBonus() {
        return this.totalBonus;
    }
    public boolean getEmployeeStatus() {
        return this.employed;
    }

    //setter functions
    public void setIDCounter(int newIDCount) {
        IdCounter = newIDCount;
    }
    public void setEmployeeID(int newID) {
        employeeID = newID;
    }
    public void setDaysWorked(int newDays) {
        this.daysWorked = newDays;
    }
    public void setBonus(double newBonus) {
        this.bonus = newBonus;
    }
    public void setSalary(double newSalary) {
        this.salary = newSalary;
    }
    public void setEmployeeStatus(boolean newStatus) {
        this.employed = newStatus;
    }
    public void addTotalPay(double pay) {
        totalPay += pay;
    }
    public void addTotalBonus(double bonus) {
        totalBonus += bonus;
    }

    //Polymorphism
    /* The function has the same name as another in vehicle class and they both do different things
     * without us having to redfine the function. This function prints out the data of Staff class
     * while the other function prints out the data of vehicle class
    */
    public void report() { //debugging
        System.out.println("\nClass:       " + this.getClass());
        System.out.println("ID:          " + getEmployeeID());
        System.out.println("Days Worked: " + getDaysWorked());
        System.out.println("Bonus:       " + getBonus());
        System.out.println("Salary:      " + getSalary());
        System.out.println("E Status:    " + getEmployeeStatus());
    }
}

class mechanic extends Staff{
    private final String type = "Mechanic";
    public mechanic() {
        super();
        employeeID = genID();
        this.salary = 1000;
    }
    public String getType(){
        return this.type;
    }

    public void repair(ArrayList<Vehicle> vehicles){
        ArrayList<Vehicle> repairList = new ArrayList<Vehicle>();

        //Sort cars that aren't Like New
        for(int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).condition != "Like New") {
                repairList.add(vehicles.get(i));
            }
        }

        if(repairList.size() != 0) {
            int repairodds = random.nextInt(10); //Chooses from 0 - 9
            int repairChooser = random.nextInt(repairList.size());

            if (repairodds >= 7) { //equal to 80% since counting from 0
                //repairList.get(repairChooser).report();
                if (repairList.get(repairChooser).condition == "Broken") {
                    repairList.get(repairChooser).condition = "Used";
                    this.addBonus(repairList.get(repairChooser).workBonus);
                    repairList.get(repairChooser).salesPrice = repairList.get(repairChooser).salesPrice * 1.5;
                    System.out.printf("Mechanic %d repaired Broken %s and made it Used (Earned $%s bonus)\n", 
                    this.employeeID, repairList.get(repairChooser).getName(), df.format(repairList.get(repairChooser).getWorkBonus()));
                    //repairList.get(repairChooser).report();
                }
                else {
                    repairList.get(repairChooser).condition = "Like New";
                    this.addBonus(repairList.get(repairChooser).workBonus);
                    repairList.get(repairChooser).salesPrice = repairList.get(repairChooser).salesPrice * 1.25;
                    System.out.printf("Mechanic %d repaired Used %s and made it Like New (Earned $%s bonus)\n", 
                    this.employeeID, repairList.get(repairChooser).getName(), df.format(repairList.get(repairChooser).getWorkBonus()));
                    //repairList.get(repairChooser).report();
                }
            }
            if (repairList.get(repairChooser).cleanliness == "Sparkling") { // Vehicle goes down a level of cleanliness regardless of it being fixed
                repairList.get(repairChooser).cleanliness = "Clean";
            }
            else if (repairList.get(repairChooser).cleanliness == "Clean") {
                repairList.get(repairChooser).cleanliness = "Dirty";
            }
        }
    }
}

class salesPerson extends Staff{
    private final String type = "Salesperson";
    public salesPerson() {
        super();
        this.employeeID = genID();
        this.salary = 1000;
    }

    public String getType(){
        return this.type;
    }

    public Vehicle sell(Buyer buyer, ArrayList<Vehicle> vehicles){
        ArrayList<Vehicle> buyerWants = new ArrayList<Vehicle>();

        //Find all vehicles that are in stock that buyer wants
        for(int i = 0; i < vehicles.size(); i++) {
            if (buyer.vehicleType.length() == vehicles.get(i).getClass().toString().substring(6).length()) {
                buyerWants.add(vehicles.get(i));
            }
        }
        
        //Finding highest price vehicle that buyer wants
        int buyOdds = random.nextInt(100);
        if(buyerWants.size() != 0) { //Goes through car type buyer wants and will got to else if none availiable
            Vehicle highestPrice = buyerWants.get(0);
            for(int i = 0; i < buyerWants.size(); i++) {
                if(buyerWants.get(i).salesPrice > highestPrice.salesPrice) {
                    highestPrice = buyerWants.get(i);
                }
            }

            //Increases chances based on car status
            if (highestPrice.cleanliness == "Sparkling") {
                buyer.buyingChance = buyer.buyingChance + 10.0;
            }
            if (highestPrice.condition == "Like New") {
                buyer.buyingChance = buyer.buyingChance + 10.0;
            }

            //will return cars that get bought
            if(buyOdds > buyer.buyingChance) {
                this.addBonus(highestPrice.workBonus);
                highestPrice.sold = true;
                System.out.printf("Salesperson %d sold %s %s %s to Buyer for $%s (earned $%s bonus)\n", 
                this.employeeID, highestPrice.getCleanliness(), highestPrice.getCondition(), highestPrice.getName(), df.format(highestPrice.getSalesPrice()), df.format(highestPrice.getWorkBonus()));
                return highestPrice;
            }
        }
        else { //goes through other car types
            System.out.println("Vehicle Size: " + vehicles.size());
            Vehicle highestPrice = vehicles.get(0);
            for(int i = 0; i < vehicles.size(); i++) {
                if(vehicles.get(i).salesPrice > highestPrice.salesPrice) {
                    highestPrice = vehicles.get(i);
                }
            }
            
            //updating car sell chances
            buyer.buyingChance = buyer.buyingChance - 20.0;

            if (highestPrice.cleanliness == "Sparkling") {
                buyer.buyingChance = buyer.buyingChance + 10.0;
            }
            if (highestPrice.condition == "Like New") {
                buyer.buyingChance = buyer.buyingChance + 10.0;
            }

            //sees if car is sold and will update values if sold
            if(buyOdds > buyer.buyingChance) {
                this.addBonus(highestPrice.workBonus);
                highestPrice.sold = true;
                System.out.printf("Salesperson %d sold %s %s %s to Buyer for $%s (earned $%s bonus)\n", 
                this.employeeID, highestPrice.getCleanliness(), highestPrice.getCondition(), highestPrice.getName(), df.format(highestPrice.getSalesPrice()), df.format(highestPrice.getWorkBonus()));
                return highestPrice;
            }
        }
        
        return vehicles.get(0);
    }
}

class intern extends Staff {
    private final String type = "Intern";

    public intern() {
        super();
        this.employeeID = genID();
        this.salary = 500;
    }

    public String getType(){
        return this.type;
    }

    public void wash(ArrayList<Vehicle> vehicles){
        ArrayList<Vehicle> dirty = new ArrayList<Vehicle>();
        ArrayList<Vehicle> clean = new ArrayList<Vehicle>();

        //Sort cars by cleanliness
        for(int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).cleanliness == "Dirty") {
                dirty.add(vehicles.get(i));
            }
            else if (vehicles.get(i).cleanliness == "Clean") {
                clean.add(vehicles.get(i));
            }
        }

        if(dirty.size() != 0) {
            int washodds = random.nextInt(10); //Chooses from 0 - 9
            int washChooser = random.nextInt(dirty.size());
            if (washodds >= 8) { //equal to 10% since counting from 0
                //dirty.get(washChooser).report();
                dirty.get(washChooser).cleanliness = "Sparkling";
                this.addBonus(dirty.get(washChooser).workBonus);
                System.out.printf("Intern %d washed %s and made it Sparkling (earned $%s bonus)\n", 
                this.employeeID, dirty.get(washChooser).getName(), df.format(dirty.get(washChooser).getWorkBonus()));
            }
            else if (washodds >= 7) { //equal to 80% since counting from 0
                //dirty.get(washChooser).report();
                dirty.get(washChooser).cleanliness = "Clean";
                System.out.printf("Intern %d washed %s and made it Clean\n", 
                this.employeeID, dirty.get(washChooser).getName());
            }
        }
        else if (clean.size() != 0){
            int washodds = random.nextInt(20); //Chooses from 0 - 19
            int washChooser = random.nextInt(clean.size());
            if (washodds >= 6) { //equal to 30% since counting from 0
                //clean.get(washChooser).report();
                clean.get(washChooser).cleanliness = "Sparkling";
                this.addBonus(clean.get(washChooser).workBonus);
                System.out.printf("Intern %d washed %s and made it Sparkling (earned $%s bonus)\n", 
                this.employeeID, clean.get(washChooser).getName(), df.format(clean.get(washChooser).getWorkBonus()));
            }
            else if (washodds >= 1) { //equal to 5% since counting from 0
                //clean.get(washChooser).report();
                clean.get(washChooser).cleanliness = "Dirty";
                System.out.printf("Intern %d washed %s and made it Clean\n", 
                this.employeeID, clean.get(washChooser).getName());
            }}
    }
}