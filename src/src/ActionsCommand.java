public interface ActionsCommand{
    void execute();
}

class SpinActionCard implements ActionsCommand{
    private ActionCard card;

    public SpinActionCard(ActionCard receivedCard) {
        this.card = receivedCard;
    }
    public void execute() {
        card.spin();
    }
}

class StealActionCard implements ActionsCommand {
    private ActionCard card;

    public StealActionCard(ActionCard receivedCard) {
        this.card = receivedCard;
    }
    public void execute() {
        card.steal();
    }
}

class CollectActionCard implements ActionsCommand {
    private ActionCard card;

    public CollectActionCard(ActionCard receivedCard) {
        this.card = receivedCard;
    }
    public void execute() {
        card.collect();
    }
}