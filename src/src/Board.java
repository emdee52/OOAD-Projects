import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class Board {
    private Scanner readUser = new Scanner(System.in);
    private Random rand = new Random();
    public ArrayList<Player> Players = new ArrayList<Player>();
    public int tiles[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};

    public static void main(String[] args) {
        Board GameLife = new Board();
        GameLife.startGame();
    }

    public void startGame() {
        System.out.println("Welcome to the Game of Life!");
        System.out.println("How many players are playing?");
        getPlayerAmount();
        turnOrder();
        turn();
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

    public ArrayList<Player> getPlayerAmount() {
        int numPlayers = readUser.nextInt();
        for (int i = 0; i < numPlayers; i++) {
            Players.add(new Player(i + 1));
        }
        return Players;
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
        CommandInvoker cardCommand = new CommandInvoker();
        for(int i = 0; i < Players.size(); i++) {
            Player currentPlayer = Players.get(i);
            currentPlayer.tileNumber += spin();
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
}