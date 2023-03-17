public interface BuyerCommand extends SysOut{
    void execute();
}

class FNCDSelect implements BuyerCommand{
    private Buyer bRequest;

    public FNCDSelect(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.choosingFNCD(bRequest.currentFNCD);
    }
}

class AskName implements BuyerCommand {
    private Buyer bRequest;

    public AskName(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingName();
    }
}

class AskTime implements BuyerCommand {
    private Buyer bRequest;

    public AskTime(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingTime();
    }
}

class newSalesPerson implements BuyerCommand {
    private Buyer bRequest;

    public newSalesPerson(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.newSalesPerson();
    }
}

class AskInventory implements BuyerCommand {
    private Buyer bRequest;

    public AskInventory(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingInventory();
    }
}

class InventoryItemDetails implements BuyerCommand {
    private Buyer bRequest;

    public InventoryItemDetails(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.AskingDetails();
    }
}

class BuyItem implements BuyerCommand {
    private Buyer bRequest;

    public BuyItem(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.BuyingVehicle();
    }
}

class endInteractions implements BuyerCommand {
    private Buyer bRequest;

    public endInteractions(Buyer BuyerRequest) {
        this.bRequest = BuyerRequest;
    }
    public void execute() {
        bRequest.endInteractions();
    }
}