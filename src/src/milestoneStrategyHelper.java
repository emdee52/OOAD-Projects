/*
 * 
 * Uses strategy pattern
 * 
 */


public class milestoneStrategyHelper {
    private milestone milestones;

    public milestoneStrategyHelper(milestone milestone) {
        this.milestones = milestone;
    }

    public void executeMilestoneAction(Player currentPlayer, int playerSpin) {
        milestones.milestoneAction(currentPlayer, playerSpin);
    }
}
