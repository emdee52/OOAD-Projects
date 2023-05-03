import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ActionCard {
    private String description;
    private int value;
    private Player owner;
    private Action action; // Command pattern interface

    public ActionCard(String description, int value, Action action) {
        this.description = description;
        this.value = value;
        this.action = action;
    }

    public String getDescription() { return description; }
    public int getValue() { return value; }
    public Player getOwner() { return owner; }
    public void setOwner(Player owner) { this.owner = owner; }

    // Command pattern invoker - Executes the action associated with the card
    public void performAction(Player player, List<Player> players, TargetSelector targetSelector) {
        action.execute(player, players, targetSelector); //  Not all cards require players and/or targetSelector
    }

    // Command pattern interface for action card behavior
    public interface Action {
        void execute(Player player, List<Player> players, TargetSelector targetSelector);
    }

    // Interface to select target player as Java doesn't allow direct method calls like selectTarget in lambdas
    public interface TargetSelector {
        Player selectTarget(Player currentPlayer, List<Player> players);
    }

    public static List<ActionCard> generateActionCards() {
        List<ActionCard> actionCards = new ArrayList<>();

        // Add action cards to the deck
        actionCards.add(new ActionCard("Take money from another player", 3000, (player, players, targetSelector) -> {
            int spinnerResult = player.spin();
            int amount = spinnerResult * 1000;
            Player targetPlayer = targetSelector.selectTarget(player, players);
            if (targetPlayer != null) {
                targetPlayer.addMoney(-amount);
                player.addMoney(amount);
            }
        }));

        actionCards.add(new ActionCard("Pay $5,000 to another player", 2000, (player, players, targetSelector) -> {
            Player targetPlayer = targetSelector.selectTarget(player, players);
            if (targetPlayer != null) {
                player.addMoney(-5000);
                targetPlayer.addMoney(5000);
            }
        }));

        actionCards.add(new ActionCard("Receive $1,000 times spinner result from the bank", 5000, (player, players, targetSelector) -> {
            int spinnerResult = player.spin();
            player.addMoney(1000 * spinnerResult);
        }));

        actionCards.add(new ActionCard("Bank pays highest spinner $10K times spinner result", 4000, (player, players, targetSelector) -> {
            int highestSpin = player.spin();
            Player highestSpinner = player;

            for (Player p : players) {
                if (p == player) continue;
                int currentSpin = p.spin();
                if (currentSpin > highestSpin) {
                    highestSpin = currentSpin;
                    highestSpinner = p;
                }
            }

            highestSpinner.addMoney(10000 * highestSpin);
        }));

        actionCards.add(new ActionCard("Collect $10,000 from the bank", 1000, (player, players, targetSelector) -> {
            player.addMoney(10000);
        }));

        actionCards.add(new ActionCard("Pay $5,000 to the bank", 2000, (player, players, targetSelector) -> {
            player.addMoney(-5000);
        }));

        // TODO: Add more action cards as needed

        // Shuffle the action cards
        Collections.shuffle(actionCards);

        return actionCards;
    }

    public void steal() {
    }

    public void spin() {
    }

    public void collect() {
    }
}
