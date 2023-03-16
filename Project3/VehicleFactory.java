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


