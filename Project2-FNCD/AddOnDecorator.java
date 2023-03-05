/*
 * 
 * This file is used to help implement the Decorator pattern
 * 
 * 
 */

public abstract class AddOnDecorator extends Vehicle {

    // Protected variable
    protected Vehicle vehicle;

    // Abstract class method
    public AddOnDecorator(Vehicle vehicleToDecorate) {
        this.vehicle = vehicleToDecorate;
    }
    @Override public String addOn(){
        return vehicle.addOn() + "                                         with ";
    }
    @Override public double price(){
        return vehicle.price();
    }
}

class WarrantyDecorator extends AddOnDecorator {

    public WarrantyDecorator(Vehicle decoratedAddOn)
    {
        super(decoratedAddOn);
    }

    public String addOn()
    {
        return super.addOn() + "Warranty Add-On: " + Utility.asDollar(price()) + "\n";
    }
    public double price(){
        return super.price() * 1.2;
    }
}

class UndercoatingDecorator extends AddOnDecorator {

    public UndercoatingDecorator(Vehicle decoratedAddOn)
    {
        super(decoratedAddOn);
    }

    public String addOn()
    {
        return super.addOn() + "Undercoating Add-On: " + Utility.asDollar(price()) + "\n";
    }
    public double price(){
        return super.price() * 1.05;
    }
}

class AAADecorator extends AddOnDecorator {

    public AAADecorator(Vehicle decoratedAddOn)
    {
        super(decoratedAddOn);
    }

    public String addOn()
    {
        return super.addOn() + "Road Rescue Coverage Add-On: " + Utility.asDollar(price()) + "\n";
    }
    public double price(){
        return super.price() * 1.02;
    }
}

class SatRadioDecorator extends AddOnDecorator {

    public SatRadioDecorator(Vehicle decoratedAddOn)
    {
        super(decoratedAddOn);
    }

    public String addOn()
    {
        return super.addOn() + "Satellite Radio Add-On: " + Utility.asDollar(price()) + "\n";
    }
    public double price(){
        return super.price() * 1.05;
    }
}