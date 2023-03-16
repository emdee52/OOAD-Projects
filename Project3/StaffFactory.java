public abstract class StaffFactory {
    protected abstract Staff createStaff();

    public StaffFactory getFactory(Enums.StaffType type) {
        switch(type) {
            case Intern:
                return new InternFactory();
            case Mechanic:
                return new MechanicFactory();
            case Salesperson:
                return new SalespersonFactory();
            case Driver:
                return new DriverFactory();
            default:
                return null;
        }
    }
}

class InternFactory extends StaffFactory {

    public Staff createStaff() {
        WashBehavior washType = null;
        int washChooser = Utility.rndFromRange(0, 2);
        switch (washChooser) { //Determines what wash the intern will be instantiated with
            case 0:
                washType = new Chemical();
                break;
            case 1:
                washType = new ElbowGrease();
                break;
            case 2:
                washType = new Detailed();
                break;
            default:
                System.err.println("What did you do");
                break;
        }
        return new Intern(washType);
    }
}

class MechanicFactory extends StaffFactory {
    public Staff createStaff() {
        return new Mechanic();
    }
}

class SalespersonFactory extends StaffFactory {
    public Staff createStaff() {
        return new Salesperson();
    }
}

class DriverFactory extends StaffFactory {
    public Staff createStaff() {
        return new Driver();
    }
}