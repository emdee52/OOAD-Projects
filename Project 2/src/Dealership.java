import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Dealership {
    DecimalFormat df = new DecimalFormat("##.00"); // Rounds to 2 decimal places
    SplittableRandom random = new SplittableRandom();
    private double sales;
    private double budget;
    private boolean overBudget;
    private double overBudgetCash;
    private ArrayList<Vehicle> inventory;
    private ArrayList<Vehicle> soldInventory;
    private ArrayList<Staff> employees;
    private ArrayList<Staff> departedEmployees;
    private int numDays;
    private final String[] day = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private int numInterns;
    private int numMechanics;
    private int numSalesPerson;
    private int numPerformance;
    private int numCars;
    private int numPickups;

    Dealership() {
        budget = 500000;
        numDays = 0;
        overBudgetCash = 0;
        sales = 0;
        numInterns = numMechanics = numSalesPerson = 0;
        inventory = new ArrayList<>();
        soldInventory = new ArrayList<>();
        employees = new ArrayList<>();
        departedEmployees = new ArrayList<>();
    }

    public void open() {
        sales = 0;
        numDays++;
        System.out.printf("*** FNCD Day %d ***\n", numDays);
        if (numDays % 7 == 0) {
            System.out.printf("FNCD is closed on %ss.\n", day[6]);
            return;
        }
        System.out.printf("Opening... (current budget $%s)\n", df.format(budget));
        hireInterns();
        buyVehicles();
        washing();
        repairing();
        System.out.println(budget);
        selling();
        System.out.println(budget);
        ending();
        System.out.println(budget);
    }

    // Getters
    public double getSales() {
        return sales;
    }
    public double getBudget() {
        return budget;
    }
    public boolean isOverBudget() {
        return overBudget;
    }
    public double getOverBudgetCash() {
        return overBudgetCash;
    }
    public ArrayList<Vehicle> getInventory() {
        return inventory;
    }
    public ArrayList<Vehicle> getSoldInventory() {
        return soldInventory;
    }
    public ArrayList<Staff> getEmployees() {
        return employees;
    }
    public ArrayList<Staff> getDepartedEmployees() {
        return departedEmployees;
    }
    public int getNumInterns() {
        return numInterns;
    }
    public int getNumMechanics() {
        return numMechanics;
    }
    public int getNumSalesPerson() {
        return numSalesPerson;
    }

    // Setters
    public void addSales(double sale) {
        sales += sale;
    }
    public void setOverBudget(boolean over) {
        overBudget = over;
    }
    public void addOverBudgetCash() {
        budget += 250000;
        overBudgetCash += 250000;
    }
    public void addInventory(Vehicle newVehicle) {
        inventory.add(newVehicle);
    }
    public void addSoldInventory(Vehicle soldVehicle) {
        soldInventory.add(soldVehicle);
    }
    public void addEmployees(Staff newStaff) {
        employees.add(newStaff);
    }
    public void setDepartedEmployees(Staff quitter) {
        departedEmployees.add(quitter);
    }
    public void incrementDay() {
        numDays += 1;
    }

    public void hireInterns() {


        while (numInterns < 3) {
            intern intern = new intern();
            System.out.println("Hired Intern " +  intern.getEmployeeID());
            addEmployees(intern);
            numInterns += 1;
            if (numMechanics < 3)
                promoteInterns(numMechanics, 0);
            if (numSalesPerson < 3)
                promoteInterns(numSalesPerson, 1);
        }
    }

    public void promoteInterns(int current, int type) {
        for (int i = employees.size() - 1; i>=0; i--) {
            if (employees.get(i) instanceof intern) {
                if (current < 3) {
                    Staff newStaff = employees.get(i).promote(employees.get(i), type);
                    System.out.printf("Promoted Intern %d into %s\n", employees.get(i).getEmployeeID(), newStaff.getType());
                    employees.add(newStaff);
                    employees.remove(i);
                    current += 1;
                    System.out.println("Type: " + type);
                    if (type == 0)
                        numMechanics += 1;
                    else if (type == 1)
                        numSalesPerson += 1;
                    numInterns -= 1;
                }
            }
        }
    }

    public void buyVehicles() {
        while (numPerformance < 4) {
            numPerformance += 1;
            Vehicle newVehicle = new Performance();
            inventory.add(newVehicle);
            budget -= newVehicle.cost;
            System.out.printf("Purchased %s, %s %s for $%s Cost\n", newVehicle.condition, newVehicle.cleanliness, newVehicle.name, df.format(newVehicle.cost));
        }

        while (numCars < 4) {
            numCars += 1;
            Vehicle newVehicle = new Car();
            inventory.add(newVehicle);
            budget -= newVehicle.cost;
            System.out.printf("Purchased %s, %s %s for $%s Cost\n", newVehicle.condition, newVehicle.cleanliness, newVehicle.name, df.format(newVehicle.cost));
        }

        while (numPickups < 4) {
            numPickups += 1;
            Vehicle newVehicle = new Pickup();
            inventory.add(newVehicle);
            budget -= newVehicle.cost;
            System.out.printf("Purchased %s, %s %s for $%s Cost\n", newVehicle.condition, newVehicle.cleanliness, newVehicle.name, df.format(newVehicle.cost));
        }
    }

    public void washing() {
        for (Staff employee : employees) {
            int washLimit = 0;
            if (employee instanceof intern) {
                intern workingIntern = (intern) employee;
                while (washLimit < 2) {
                    workingIntern.wash(inventory);
                    washLimit += 1;
                }
            }
        }
    }

    public void repairing() {
        for (Staff employee : employees) {
            int repairLimit = 0;
            if (employee instanceof mechanic) {
                mechanic workingMechanic = (mechanic) employee;
                while (repairLimit < 2) {
                    workingMechanic.repair(inventory);
                    repairLimit += 1;
                }
            }
        }
    }

    public void removeSoldVehicles() {
        for (int i = inventory.size() - 1; i>=0; i--) {
            if (inventory.get(i).getStatus()) {
                Vehicle sold = inventory.get(i);
                soldInventory.add(sold);
                inventory.remove(sold);
            }
        }
    }

    public void selling() {
        int totalBuyers;
        ArrayList<Integer> salespersonIndices = new ArrayList<>();
        if (numDays % 7 == 6 || numDays % 7 == 5)
            totalBuyers = random.nextInt(2,9); // 2 to 8 buyers
        else
            totalBuyers = random.nextInt(0, 6); // 0 to 5 buyers

        System.out.println(employees.size());
        for (int i = 0; i < employees.size(); i++) { // gets all the indices of salespersons and stores them in salespersonIndices to randomly assign to buyer
            if (employees.get(i) instanceof salesPerson) {
                salespersonIndices.add(i);
            }
        }
        System.out.println(salespersonIndices.size());
        for (int i = 0; i < totalBuyers; i ++) {
            Buyer customer = new Buyer();
            salesPerson seller = (salesPerson) employees.get(salespersonIndices.get(random.nextInt(salespersonIndices.size()))); // assign random salesPerson to buyer
            Vehicle saleVehicle = seller.sell(customer, inventory);
            if (saleVehicle.getStatus()) { // if sold
                budget += saleVehicle.getSalesPrice();
                sales += saleVehicle.getSalesPrice();
                removeSoldVehicles();
            }
            
        }
    }

    public void ending() {
        System.out.println(employees.size());
        for (int i = employees.size() - 1; i>=0; i--) {
            budget -= employees.get(i).getSalary();
            employees.get(i).addTotalPay(employees.get(i).getSalary());

            budget -= employees.get(i).getBonus();
            employees.get(i).addTotalBonus(employees.get(i).getSalary());
            employees.get(i).setBonus(0); // reset daily bonus earned
            if(employees.get(i) instanceof intern) {
                if (random.nextInt(10) >= 9 && numInterns == 3) { // 10% chance to quit
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: has quit the FNCD\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                    employees.get(i).setEmployeeStatus(false);
                    departedEmployees.add(employees.get(i));
                    employees.remove(i);
                }
                else {
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: employed\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                }
            }
            else if(employees.get(i) instanceof mechanic) {

                if (random.nextInt(10) >= 9 && numMechanics == 3) { // 10% chance to quit
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: has quit the FNCD\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                    employees.get(i).setEmployeeStatus(false);
                    departedEmployees.add(employees.get(i));
                    employees.remove(i);
                    numMechanics -= 1;
                }
                else {
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: employed\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                }
            }
            else {
                if (random.nextInt(10) >= 9 && numSalesPerson == 3) { // 10% chance to quit
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: has quit the FNCD\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                    employees.get(i).setEmployeeStatus(false);
                    departedEmployees.add(employees.get(i));
                    employees.remove(i);
                    numSalesPerson -= 1;
                }
                else {
                    System.out.printf("%s %d- Days Worked: %d, Total Pay: %s, Total Bonus: %s, Employment: employed\n", 
                    employees.get(i).getType(), employees.get(i).getEmployeeID(), employees.get(i).getDaysWorked(), df.format(employees.get(i).getTotalPay()), df.format(employees.get(i).getTotalBonus()));
                }
            }
        }  
    }

    public void printReport() {

    }
}
