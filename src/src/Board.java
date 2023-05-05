import java.util.*;

public class Board {
    private Scanner readUser = new Scanner(System.in);
    private Random rand = new Random();
    public ArrayList<Player> Players = new ArrayList<Player>();
    public ArrayList<Player> RetiredPlayers = new ArrayList<Player>();
    public int salaryTiles[] = {5, 10, 20, 31, 37, 44, 52, 61, 72, 83, 89, 94, 200};
    public int childrenTile[] = {38, 59};
    public int stopTiles[] = {};
    public Job jobs;
    List<ActionCard> actionCards = ActionCard.generateActionCards();

    public static void main(String[] args) {
        Board GameLife = new Board();
        GameLife.playGame();
    }

    public void playGame() {
        System.out.println("Welcome to the Game of Life!");
        System.out.println("How many players are playing?");
        getPlayerAmount();
        turnOrder();
        for(int i = 0; i < Players.size(); i++) {
            System.out.println("Player " + Players.get(i).playerNumber + " what would you like to do?");
            System.out.println("1. Go to College");
            System.out.println("2. Career Path");
            CollegeCareerPath(Players.get(i));
        }
        
        while(checkAllRetirement() == false) {
            turn();
        }
        calculateWinner();
    }

    public int spin() {
        return rand.nextInt(9) + 1;
    }

    public String getColor(int spunNumber) {
        if (spunNumber % 2 == 0) {
            return "red";
        }
        else {
            return "black";
        }
    }

    public boolean checkAllRetirement(){
        boolean allRetired = true;
        for(int i = 0; i < Players.size(); i++) {
            if(Players.get(i).retired == false) {
                allRetired = false;
            }
        }
        return allRetired;
    }

    public ArrayList<Player> getPlayerAmount() {
        int numPlayers = readUser.nextInt();
        for (int i = 0; i < numPlayers; i++) {
            Players.add(new Player(i + 1));
        }
        return Players;
    }

    public boolean checkSalaryTiles(int tileNumber) {
        for (int i = 0; i < salaryTiles.length; i++) {
            if (salaryTiles[i] == tileNumber) {
                return true;
            }
        }
        return false;
    }

    boolean checkChildrenTile(int tileNumber) {
        for (int i = 0; i < childrenTile.length; i++) {
            if (salaryTiles[i] == tileNumber) {
                return true;
            }
        }
        return false;
    }

    public void turnOrder() {
        int pos = 0;
        int playerSpin = spin();
        for(int i = 0; i < Players.size(); i++) {
            playerSpin = spin();
            Players.get(i).turnOrderSpin = playerSpin;
            System.out.println("Player number: " + Players.get(i).playerNumber + " Player turn spin: " + Players.get(i).turnOrderSpin);
        }

        for (int i = 0; i < Players.size(); i++) {
            pos = i;
            for (int j = 0; j < Players.size(); j++) {
                if (Players.get(j).turnOrderSpin < Players.get(pos).turnOrderSpin) {
                    pos = j;
                }
                
                Collections.swap(Players, i, pos);
            }
        }

        for (int i = 0; i < Players.size(); i++) {
            System.out.println("Player" + Players.get(i).playerNumber + " spun " + Players.get(i).turnOrderSpin);
        }
    }

    public void turn() {
        milestoneStrategyHelper context;
        for(int i = 0; i < Players.size(); i++) {
            Player currentPlayer = Players.get(i);
            System.out.println("It is Player " + currentPlayer.playerNumber + "'s turn");
            int PlayerSpin = spin();

            System.out.println("Player " + currentPlayer.playerNumber + " spun " + PlayerSpin);
            checkLuckyNumber(currentPlayer, PlayerSpin);


            if(currentPlayer.tileNumber >= 26 && currentPlayer.Milestone[2] == false) {
                System.out.println("Player " + currentPlayer.playerNumber + " has hit a milestone!");
                LifeNightPath(currentPlayer);
            }
            if(currentPlayer.tileNumber >= 46 && currentPlayer.Milestone[3] == false) {
                LifeFamilyPath(currentPlayer);
            }
            if(currentPlayer.lifeFamilyTiles >= 10 && currentPlayer.Milestone[4] == false) {

            }
            if(currentPlayer.tileNumber >= 74 && currentPlayer.Milestone[5] == false) {

            }

            if(currentPlayer.collegeStatus) {
                context = new milestoneStrategyHelper(new graduatedMilestone());
                context.executeMilestoneAction(currentPlayer, PlayerSpin);
                if(currentPlayer.careerCollegeTiles >= 10 && currentPlayer.currentJob == null) {
                    ArrayList<Job> jobChoices = jobs.getJobChoices(EducationLevel.Educated);
                    chooseJob(jobChoices, currentPlayer);
                }
            }
            if (currentPlayer.marriedStatus == false && currentPlayer.tileNumber >= 14) {
                context = new milestoneStrategyHelper(new marriedMilestone());
                context.executeMilestoneAction(currentPlayer, PlayerSpin);

                int giftSpin = spin() * 10000;
                System.out.println("Player " + currentPlayer.playerNumber + " spun " + giftSpin/10000);

                currentPlayer.money += giftSpin * (Players.size() - 1);

                for(int j = 0; j < Players.size(); j++) {
                    if(Players.get(j) != currentPlayer) {
                        System.out.println("Player " + Players.get(j).playerNumber + " paid Player " + currentPlayer.playerNumber +" " + giftSpin + " in wedding gifts");
                        Players.get(j).money -= giftSpin;
                    }
                }
            }
            else if(currentPlayer.nightSchoolStatus) {
                context = new milestoneStrategyHelper(new nightSchoolMilestone());
                context.executeMilestoneAction(currentPlayer, PlayerSpin);
            }
            else if(currentPlayer.familyStatus) {
                context = new milestoneStrategyHelper(new familyMilestone());
                context.executeMilestoneAction(currentPlayer, PlayerSpin);
            }
            if(currentPlayer.kidsStatus) {
                context = new milestoneStrategyHelper(new kidsMilestone());
                context.executeMilestoneAction(currentPlayer, PlayerSpin);
            }
            else {
                currentPlayer.tileNumber += PlayerSpin;
                System.out.println("Player " + currentPlayer.playerNumber + " moved " + PlayerSpin + " spaces");
                System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);
            }

            //Check player retired
            if(currentPlayer.tileNumber >= 98 ) {
                retirePlayer(currentPlayer);
            }

            if(checkSalaryTiles(currentPlayer.tileNumber) == false && checkChildrenTile(currentPlayer.tileNumber) == false) {
//                System.out.println("Current money: " + currentPlayer.money);
                drawCard();
//                System.out.println("After drawing card money: " + currentPlayer.money);
                currentPlayer.actionCards += 1;
            }

            if(checkChildrenTile(currentPlayer.tileNumber)) {
                if (currentPlayer.tileNumber == 38) {
                    System.out.println("Player " + currentPlayer.playerNumber + " now has a twins");
                    currentPlayer.kids += 2;
                }
                else {
                    System.out.println("Player " + currentPlayer.playerNumber + " now has a kid");
                    currentPlayer.kids += 1;
                }
            }
            
            if(currentPlayer.tileNumber >= salaryTiles[currentPlayer.salaryTier]) { 
                if (currentPlayer.salaryTier != salaryTiles.length) {
                    currentPlayer.salaryTier += 1;
                }
                getSalary(currentPlayer);
            }
        }
    }

    public void CollegeCareerPath(Player currPlayer) {
        int CollegeCareerPathChoice = readUser.nextInt();
        while(CollegeCareerPathChoice != 1 || CollegeCareerPathChoice != 2) {
            if(CollegeCareerPathChoice == 1) {
                System.out.println("Player " + currPlayer.playerNumber + " chose to go to college");
                System.out.println("Player " + currPlayer.playerNumber + " paid 100,000");
                currPlayer.money -= 100000;
                currPlayer.collegeStatus = true;
                return;
            }
            else if (CollegeCareerPathChoice == 2) {
                System.out.println("Player "+ currPlayer.playerNumber + " chose career path");
                currPlayer.milestoneStatus += 1;
                ArrayList<Job> jobChoices = jobs.getJobChoices(EducationLevel.Uneducated);
                chooseJob(jobChoices, currPlayer);
                return;
            }
            else {
                System.out.println("Please choose a proper option");
            }
        }
    }

    public void LifeNightPath(Player currPlayer) {
        int PathChoice = 0;
        System.out.println("Player " + currPlayer.playerNumber + " what would you like to do?");
        System.out.println("1. Go to Night school");
        System.out.println("2. Go on with life");

        currPlayer.Milestone[2] = true;
        currPlayer.milestoneStatus += 1;
        
        while(PathChoice != 1 || PathChoice != 2) {
            PathChoice = readUser.nextInt();
            if(PathChoice == 1) {
                System.out.println("Player " + currPlayer.playerNumber + " chose to go to night school");
                System.out.println("Player " + currPlayer.playerNumber + " paid 100,000");
                currPlayer.money -= 100000;
                ArrayList<Job> jobChoices = jobs.getJobChoices(EducationLevel.Educated);
                chooseJob(jobChoices, currPlayer);
                currPlayer.nightSchoolStatus = true;
                currPlayer.Milestone[2] = true;
                currPlayer.milestoneStatus += 1;
                return;
            }
            else if (PathChoice == 2) {
                System.out.println("Player "+ currPlayer.playerNumber + " chose life path");
                currPlayer.Milestone[2] = true;
                currPlayer.milestoneStatus += 1;
                return;
            }
            else {
                System.out.println("Please choose a proper option");
            }
        }
    }

    public void LifeFamilyPath(Player currPlayer) {
        int PathChoice = 0;
        System.out.println("Player " + currPlayer.playerNumber + " what would you like to do?");
        System.out.println("1. Go to Family Path");
         System.out.println("2. Go on with life");
        while(PathChoice != 1 || PathChoice != 2) {
            PathChoice = readUser.nextInt();
            if(PathChoice == 1) {
                currPlayer.familyStatus = true;
                currPlayer.Milestone[3] = true;
                currPlayer.milestoneStatus += 1;
                return;
            }
            else if (PathChoice == 2) {
                System.out.println("Player "+ currPlayer.playerNumber + " chose life path");
                currPlayer.Milestone[3] = true;
                currPlayer.milestoneStatus += 1;
                return;
            }
            else {
                System.out.println("Please choose a proper option");
            }
        }
    }

    public void retirePlayer(Player retiringPlayer) {
        retiringPlayer.retired = true;
        Players.remove(retiringPlayer);
        RetiredPlayers.add(retiringPlayer);
    }

    public void calculateWinner() {
        ArrayList<Integer> totalWealth = new ArrayList<>();
        int retirementBonus = 100000 * (RetiredPlayers.size() + 1);
        for(int i = 0; i < RetiredPlayers.size(); i++) {
            Player currPlayer = RetiredPlayers.get(i);
            int totalPlayerWealth = retirementBonus;
            retirementBonus -= 100000;
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " recieved their retirement bonus of " + retirementBonus);
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " had total money of " + RetiredPlayers.get(i).money);
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " had " + RetiredPlayers.get(i).kids + " kids ");
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " had " + RetiredPlayers.get(i).actionCards + " action cards");
            totalPlayerWealth = currPlayer.money + totalPlayerWealth;
            totalPlayerWealth += (currPlayer.kids * 50000) + totalPlayerWealth;
//            for (ActionCard card : currPlayer.ownedActionCards) totalPlayerWealth += card.getValue();
            totalPlayerWealth += (currPlayer.actionCards * 100000) + totalPlayerWealth;
            totalWealth.add(totalPlayerWealth);
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " total wealth: " + totalPlayerWealth);
        }

        Collections.sort(totalWealth);

        //Annouce winners
        for(int i = 0; i < RetiredPlayers.size(); i++) {
            if (i == 0) {
                System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " is the winner!");
            }
            System.out.println("Player " + RetiredPlayers.get(i).playerNumber + " is in position " + (i + 1));
        }

    }

    public void drawCard() {
        for (Player currentPlayer : Players) {
            // Draw the top card from the deck
            ActionCard drawnCard = actionCards.remove(0);
            actionCards.add(drawnCard);

            // Set the owner of the card
            drawnCard.setOwner(currentPlayer);
            currentPlayer.ownedActionCards.add(drawnCard);

            System.out.println(currentPlayer.playerNumber + " drew the card: " + drawnCard.getDescription());
            // Perform the action of the card
            drawnCard.performAction(currentPlayer, Players, this::selectTarget);
        }
    }



    public void checkLuckyNumber(Player currentPlayer, int spunNumber) {
        for(int i = 0; i < Players.size(); i++) {
            if(Players.get(i).currentJob != null) {
                if(spunNumber == Players.get(i).currentJob.getLuckyNumber() && Players.get(i) != currentPlayer) {
                    System.out.println("Player " + currentPlayer.playerNumber + " rolled Player " + Players.get(i).playerNumber + "'s lucky number");
                    System.out.println("Player " + currentPlayer.playerNumber + " paying " + Players.get(i).playerNumber + " 10,000 dollars");
                    currentPlayer.money -= 10000;
                    Players.get(i).money += 10000;
                }
            }
        }
    }

    public void chooseJob(ArrayList<Job> jobChoices, Player jobPlayer) {
        System.out.println("Which job do you want?");
        for(int i = 0; i < jobChoices.size(); i++) { 
            System.out.println((i + 1) + ". " + jobChoices.get(i).getName() + " Salary: " + jobChoices.get(i).getStartingSalary() + " Lucky Number: " + jobChoices.get(i).getLuckyNumber());
        }
        int jobNumber = readUser.nextInt();
        if(jobNumber == 1) {
            System.out.println("Player " + jobPlayer.playerNumber + " chose " + jobChoices.get(jobNumber -1).getName());
            jobPlayer.currentJob = jobChoices.get(jobNumber -1);
        }
        else {
            System.out.println("Player " + jobPlayer.playerNumber + " chose " + jobChoices.get(jobNumber -1).getName());
            jobPlayer.currentJob = jobChoices.get(jobNumber -1);
        }
    }

    public void getSalary(Player paidPlayer) {
        if(paidPlayer.currentJob != null) {
            paidPlayer.money += paidPlayer.currentJob.getStartingSalary();
            System.out.println("Player " + paidPlayer.playerNumber + " recieved their salary of " +  paidPlayer.currentJob.getStartingSalary());
            System.out.println("Player " + paidPlayer.playerNumber + " has total of " +  paidPlayer.money + " dollars");
        }   
    }

    // Method to select a target player from the list of players
    private Player selectTarget(Player currentPlayer, List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a target player (INPUT PLAYER NUMBER):");

        // Show a list of available players to choose from
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != currentPlayer) {
                System.out.println( "Player " + (i + 1));
            }
        }

        // Get the user's choice
        int choice = scanner.nextInt();
        Player targetPlayer = players.get(choice - 1);

        // Return null if the current player is chosen, else will return selected player
        if (targetPlayer == currentPlayer) {
            System.out.println("Invalid choice. You cannot target yourself.");
            return null;
        }

        return targetPlayer;
    }
}