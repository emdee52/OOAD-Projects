import java.util.*;

public class Board {
    private Scanner readUser = new Scanner(System.in);
    private Random rand = new Random();
    public ArrayList<Player> Players = new ArrayList<Player>();
    public ArrayList<Player> RetiredPlayers = new ArrayList<Player>();
    public int salaryTiles[] = {5, 10, 20, 31, 37, 44, 52, 61, 72, 83, 89, 94};
    public int childrenTile[] = {38, 59};
    public int CollegeCareerPathChoice;
    public Job jobs;

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
        for(int i = 0; i < Players.size(); i++) {
            Player currentPlayer = Players.get(i);
            System.out.println("It is Player " + currentPlayer.playerNumber + "'s turn");
            int PlayerSpin = spin();

            System.out.println("Player " + currentPlayer.playerNumber + " spun " + PlayerSpin);
            checkLuckyNumber(currentPlayer, PlayerSpin);

            if(CollegeCareerPathChoice == 1) {
                CollegePath(currentPlayer, PlayerSpin);
            }
            currentPlayer.tileNumber += PlayerSpin;
            System.out.println("Player " + currentPlayer.playerNumber + " moved " + PlayerSpin + " spaces");
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);

            if(currentPlayer.tileNumber >= 98 ) {
                retirePlayer(currentPlayer);
            }

            if(checkSalaryTiles(currentPlayer.tileNumber) == false && checkChildrenTile(currentPlayer.tileNumber) == false) {
                drawCard();
            }
            if(checkChildrenTile(currentPlayer.tileNumber)) {
                if (currentPlayer.tileNumber == 38) {
                    System.out.println("Player " + currentPlayer.playerNumber + " now has a twins");
                }
                else {
                    System.out.println("Player " + currentPlayer.playerNumber + " now has a kid");
                }
            }
            
            if(currentPlayer.tileNumber >= salaryTiles[currentPlayer.salaryTier]) { 
                if (currentPlayer.salaryTier == salaryTiles.length) {

                }
                currentPlayer.salaryTier += 1;
                getSalary(currentPlayer);
            }
        }
    }

    public void CollegeCareerPath(Player currPlayer) {
        CollegeCareerPathChoice = readUser.nextInt();
        while(CollegeCareerPathChoice != 1 || CollegeCareerPathChoice != 2) {
            if(CollegeCareerPathChoice == 1) {
                System.out.println("Player " + currPlayer.playerNumber + " chose to go to college");
                System.out.println("Player " + currPlayer.playerNumber + " paid 100,000");
                currPlayer.money -= 100000;
                return;
            }
            else if (CollegeCareerPathChoice == 2) {
                System.out.println("Player "+ currPlayer.playerNumber + " chose career path");
                ArrayList<Job> jobChoices = jobs.getJobChoices(EducationLevel.Uneducated);
                chooseJob(jobChoices, currPlayer);
                return;
            }
            else {
                System.out.println("Please choose a proper option");
            }
        }
    }

    public void CollegePath(Player currentPlayer, int playerSpin) {
        boolean mileStoneReached = false;
        System.out.println("Player " + currentPlayer.playerNumber + " spun " + playerSpin + " in college");
        checkLuckyNumber(currentPlayer, playerSpin);

        currentPlayer.careerCollegeTiles = currentPlayer.careerCollegeTiles + playerSpin;
        System.out.println("Player " + currentPlayer.playerNumber + " on " + currentPlayer.careerCollegeTiles + " tile");
        
        if(currentPlayer.careerCollegeTiles >= 10 && mileStoneReached == false) {
            currentPlayer.careerCollegeTiles = 10;
            mileStoneReached = true;    
            System.out.println("Player " + currentPlayer.playerNumber + " graduated");

        }
        else if(currentPlayer.careerCollegeTiles > 12) {
            currentPlayer.careerCollegeTiles = 13;
            mileStoneReached = true;
            int extraMove = currentPlayer.careerCollegeTiles - 12;
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
            totalPlayerWealth = currPlayer.money + totalPlayerWealth;
            totalPlayerWealth += (currPlayer.kids * 50000) + totalPlayerWealth;
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
        CommandInvoker cardCommand = new CommandInvoker();
        for(int i = 0; i < Players.size(); i++) {
            Player currentPlayer = Players.get(i);
            switch (currentPlayer.tileNumber) {
                case 1:
                    cardCommand.addCommand(null);
                    break;
                case 2:
                    cardCommand.addCommand(null);
                    break;
                case 3:
                    cardCommand.addCommand(null);
                    break;
                default:
                    break;
            }   
        }
    }

    public void checkLuckyNumber(Player currentPlayer, int spunNumber) {
        for(int i = 0; i < Players.size(); i++) {
            if(Players.get(i).currentJob != null) {
                if(spunNumber == Players.get(i).currentJob.getLuckyNumber()) {
                    System.out.println("Player " + currentPlayer.playerNumber + " rolled Player " + Players.get(i).playerNumber + "'s lucky number");
                    System.out.println("Player " + currentPlayer.playerNumber + " paying " + Players.get(i).playerNumber + "10,000 dollars");
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
            System.out.println("Player " + jobPlayer.playerNumber + " chose" + jobChoices.get(jobNumber -1).getName());
            jobPlayer.currentJob = jobChoices.get(jobNumber -1);
        }
        else {
            System.out.println("Player " + jobPlayer.playerNumber + " chose" + jobChoices.get(jobNumber -1).getName());
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
    private static Player selectTarget(Player currentPlayer, List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a target player:");

        // Show a list of available players to choose from
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != currentPlayer) {
                System.out.println((i + 1) + ": " + players.get(i).getName());
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