/*
 * 
 * Factory pattern implementation below for vehicle
 * 
 */


public abstract class VehicleFactory {
    protected abstract Vehicle createVehicle();

    static public VehicleFactory getFactory(Enums.VehicleType type) {
        switch(type) {
            case Car:
                return new CarFactory();
            case PerfCar:
                return new PerfCarFactory();
            case Pickup:
                return new PickupFactory();
            case Electric:
                return new ElectricFactory();
            case Motorcycle:
                return new MotorcycleFactory();
            case MonsterTruck:
                return new MonsterTruckFactory();
            case SUV:
                return new SUVFactory();
            case SportsCar:
                return new SportsCarFactory();
            case Sedan:
                return new SedanFactory();
            default:
                return null;
        }
    }
}

class CarFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new Car();
    }
}

class PerfCarFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new PerfCar();
    }
}

class PickupFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new Pickup();
    }
}

class ElectricFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new Electric();
    }
}

class MotorcycleFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new Motorcycle();
    }
}

class MonsterTruckFactory extends VehicleFactory {

    @Override
    protected Vehicle createVehicle() {
        return new MonsterTruck();
    }
}

class SUVFactory extends VehicleFactory{

    @Override
    protected Vehicle createVehicle() {
        return new SUV();
    }
}

class SportsCarFactory extends VehicleFactory{

    @Override
    protected Vehicle createVehicle() {
        return new SportsCar();
    }
}

class SedanFactory extends VehicleFactory{

    @Override
    protected Vehicle createVehicle() {
        return new Sedan();
    }
}


