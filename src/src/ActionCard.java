public class ActionCard {
    private int value;
    private String description;
    private Player owner;
    private Player target;

    public void spin() {

    }

    public void steal() {

    }

    public int getValue() {return value;}
    public String getDescription() {return description;}
    public Player getOwner() {return owner;}
    public Player getTarget() {return target;}

    public void setValue(int value_) {value = value_;}
    public void setDescription(String desc) {description = desc;}
    public void setOwner(Player owner_) {owner = owner_;}
    public void setTarget(Player target_) {target = target_;}

    public void assignCard() {}
}
