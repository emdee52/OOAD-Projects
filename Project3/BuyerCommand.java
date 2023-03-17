public interface BuyerCommand extends SysOut{
    void execute();
}

class FNCDSelect implements BuyerCommand{
    private BuyerRequest bRequest;

    public FNCDSelect(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.choosingFNCD();
    }
}

class AskName implements BuyerCommand {
    private BuyerRequest bRequest;

    public AskName(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingName();
    }
}

class AskTime implements BuyerCommand {
    private BuyerRequest bRequest;

    public AskTime(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingTime();
    }
}

class newSalesPerson implements BuyerCommand {
    private BuyerRequest bRequest;

    public newSalesPerson(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.newSalesPerson();
    }
}

class AskInventory implements BuyerCommand {
    private BuyerRequest bRequest;

    public AskInventory(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingInventory();
    }
}

class InventoryItemDetails implements BuyerCommand {
    private BuyerRequest bRequest;

    public InventoryItemDetails(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingDetails();
    }
}

class BuyItem implements BuyerCommand {
    private BuyerRequest bRequest;

    public BuyItem(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.BuyingVehicle();
    }
}

class endInteractions implements BuyerCommand {
    private BuyerRequest bRequest;

    public endInteractions(BuyerRequest BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.endInteractions();
    }
}