import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class JUnitTest {
    Announcer announcer = new Announcer();
    @Test
    void test1_CheckMechanicCleanlinessDowngrade() {
        Mechanic testMechanic = new Mechanic();
        MonsterTruck tMonsterTruck = new MonsterTruck();
        tMonsterTruck.cleanliness = Enums.Cleanliness.Sparkling;
        tMonsterTruck.condition = Enums.Condition.Broken;
        ArrayList<Vehicle> testList = new ArrayList<>();
        testList.add(tMonsterTruck);
        testMechanic.repairVehicles(testList, announcer, 0);
        assertEquals(tMonsterTruck.cleanliness, Enums.Cleanliness.Clean);
    }

    @Test
    void test2_CheckMechanicBonus() {
        Mechanic testMechanic = new Mechanic();
        MonsterTruck tMonsterTruck = new MonsterTruck();
        tMonsterTruck.cleanliness = Enums.Cleanliness.Sparkling;
        tMonsterTruck.condition = Enums.Condition.Broken;
        ArrayList<Vehicle> testList = new ArrayList<>();
        testList.add(tMonsterTruck);
        testMechanic.repairVehicles(testList, announcer, 0);
        assertEquals(500, testMechanic.bonusEarned, 0);
    }

    @Test
    void test3_CheckAddStaff() {
        FNCD test = new FNCD("Test", announcer);
        test.addStaff(Enums.StaffType.Salesperson);
        Salesperson testEmployee = new Salesperson();
        assertEquals(1, test.staff.size());
    }
    @Test
    void test4_CheckStaffInit() {
        FNCD test = new FNCD("Test", announcer);
        test.hireNewStaff();
        assertEquals(12, test.staff.size());
    }

    @Test
    void test5_CheckInternInit() {
        FNCD test = new FNCD("Test", announcer);
        test.hireNewStaff();
        int n = 0;
        for (Staff s: test.staff) {
            if (s.type == Enums.StaffType.Intern) n++;
        }
        assertEquals(3, n);
    }

    @Test
    void test6_PromoteIntern() {
        FNCD test = new FNCD("Test", announcer);
        test.addStaff(Enums.StaffType.Intern);
        Staff newStaff = test.staff.get(0).promote(test.staff.get(0), Enums.StaffType.Salesperson);
        assertEquals(Enums.StaffType.Salesperson, newStaff.type);
    }

    @Test
    void test7_SellVehicle() {
        FNCD test = new FNCD("Test", announcer);
        Buyer testBuyer = new Buyer(test);
        testBuyer.preference = Enums.VehicleType.Car;
        testBuyer.type = Enums.BuyerType.NeedsOne;
        Salesperson testEmployee = new Salesperson();
        test.addVehicle(Enums.VehicleType.Car);
        test.inventory.get(0).racesWon = 100;
        test.inventory.get(0).cleanliness = Enums.Cleanliness.Sparkling;
        test.inventory.get(0).condition = Enums.Condition.LikeNew;
        Vehicle v = testEmployee.sellVehicle(testBuyer, test.inventory, announcer, 0);
        test.inventory.remove(v);
        assertEquals(0, test.inventory.size());
    }

    @Test
    void test8_SalesPersonBonus() {
        FNCD test = new FNCD("Test", announcer);
        Buyer testBuyer = new Buyer(test);
        testBuyer.preference = Enums.VehicleType.Car;
        testBuyer.type = Enums.BuyerType.NeedsOne;
        Salesperson testEmployee = new Salesperson();
        test.addVehicle(Enums.VehicleType.Car);
        test.inventory.get(0).racesWon = 100;
        testEmployee.sellVehicle(testBuyer, test.inventory, announcer, 0);
        assertEquals(500, testEmployee.bonusEarned, 0);
    }

    @Test
    void test9_InternInit() {
        WashBehavior test = new Chemical();
        Intern testIntern = new Intern(test);
        assertEquals(Enums.StaffType.Intern, testIntern.type);
        assertEquals(0, testIntern.bonusEarned, 0);
        assertEquals(0, testIntern.salaryEarned, 0);
        assertEquals(0, testIntern.daysWorked, 0);
        assertEquals(60, testIntern.salary, 0);
    }

    @Test
    void test10_VehicleInit() {
        FNCD test = new FNCD("Test", announcer);
        test.updateInventory();
        assertEquals(36, test.inventory.size());
    }

    @Test
    void test11_BuyerWeekend() {
        FNCD test = new FNCD("Test", announcer);
        ArrayList<Buyer> buyerList = new ArrayList<>();
        buyerList = test.getBuyers(Enums.DayOfWeek.Sat);
        assert(buyerList.size() >= 2);
    }

    @Test
    void test12_raceInit() {
        FNCD test = new FNCD("Test", announcer);
        ArrayList<Staff> drivers = Staff.getStaffByType(test.staff, Enums.StaffType.Driver);
        ArrayList<String> racers = test.generateRacers(drivers);
        assertEquals(20, racers.size());
    }

    @Test
    void test13_payStaff() {
        FNCD test = new FNCD("Test", announcer);
        ArrayList<Staff> driver = new ArrayList<>();
        ArrayList<Staff> Intern = new ArrayList<>();
        ArrayList<Staff> Sales = new ArrayList<>();
        ArrayList<Staff> Mechanic = new ArrayList<>();
        test.hireNewStaff();
        test.payStaff();
        for(Staff s : test.staff){
            if (s.type == Enums.StaffType.Driver) {
                driver.add(s);
            }
            else if (s.type == Enums.StaffType.Intern) {
                Intern.add(s);
            }
            else if (s.type == Enums.StaffType.Salesperson) {
                Sales.add(s);
            }
            else {
                Mechanic.add(s);
            }
        }
        assertEquals(60, Intern.get(0).salaryEarned, 0);
        assertEquals(100, driver.get(0).salaryEarned, 0);
        assertEquals(90, Sales.get(0).salaryEarned, 0);
        assertEquals(120, Mechanic.get(0).salaryEarned, 0);
    }

    @Test
    void test14_OverBudget() {
        FNCD test = new FNCD("Test", announcer);
        test.moneyOut(150000);
        assertEquals(200000, test.getBudget(), 0);
    }

    @Test
    void test15_getRaceCars() {
        FNCD test = new FNCD("Test", announcer);
        test.updateInventory();
        for(int i = 0; i < test.inventory.size(); i++) {
            test.inventory.get(i).condition = Enums.Condition.LikeNew;
        }
        ArrayList<Vehicle> raceCars = new ArrayList<>();
        raceCars = test.getRaceCars(Enums.VehicleType.PerfCar);
        assertEquals(3, raceCars.size());
    }
}