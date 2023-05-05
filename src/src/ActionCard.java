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
        actionCards.add(new ActionCard("Spin and take spin result times $1,000 from another player", 3000, (player, players, targetSelector) -> {
            int spinnerResult = player.spin();
            int amount = spinnerResult * 1000;
            Player targetPlayer = targetSelector.selectTarget(player, players);
            if (targetPlayer != null) {
                targetPlayer.addMoney(-amount);
                player.addMoney(amount);
            }
        }));

        actionCards.add(new ActionCard("Pay $5,000 to another player", 2000, (player, players, targetSelector) -> {
            // Selects the other player
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

        actionCards.add(new ActionCard("Collect $10,000 from the bank", 1000, (player, players, targetSelector) -> player.addMoney(10000)));

        actionCards.add(new ActionCard("Pay $5,000 to the bank", 2000, (player, players, targetSelector) -> player.addMoney(-5000)));

        actionCards.add(new ActionCard("Every player spins for money from the bank", 2000, (player, players, targetSelector) -> {
            for (Player p : players) {
                int spinResult = p.spin();
                if (spinResult >= 1 && spinResult <= 3) {
                    p.addMoney(2000);
                } else if (spinResult >= 4 && spinResult <= 7) {
                    p.addMoney(4000);
                } else if (spinResult >= 8 && spinResult <= 10) {
                    p.addMoney(7000);
                }
            }
        }));

        actionCards.add(new ActionCard("Two players spin for prize", 2000, (player, players, targetSelector) -> {
            int playerSpin = player.spin();

            // Selects the other player
            Player otherPlayer = targetSelector.selectTarget(player, players);
            int otherPlayerSpin = otherPlayer.spin();

            // The player with the higher spin result wins
            Player winner;
            int winningSpin;
            if (playerSpin >= otherPlayerSpin) {
                winner = player;
                winningSpin = playerSpin;
            } else {
                winner = otherPlayer;
                winningSpin = otherPlayerSpin;
            }

            // The winner receives $1,250 times the result of their spin
            winner.addMoney(1250 * winningSpin);
        }));

        // Shuffle the action cards
        Collections.shuffle(actionCards);

        return actionCards;
    }
}
