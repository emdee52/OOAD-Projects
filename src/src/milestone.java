import java.util.*;

public interface milestone {
    Random rand = new Random();
    public void milestoneAction(Player currentPlayer, int playerSpin);

}

class graduatedMilestone implements milestone {
    public void milestoneAction(Player currentPlayer, int playerSpin) {
        currentPlayer.careerCollegeTiles += playerSpin;
        
        if(currentPlayer.careerCollegeTiles >= 10 && currentPlayer.Milestone[0] == false) {
            currentPlayer.careerCollegeTiles = 10;
            currentPlayer.Milestone[0] = true;
            currentPlayer.milestoneStatus += 1;

            System.out.println("Player " + currentPlayer.playerNumber + " has hit a milestone!");
            System.out.println("Player " + currentPlayer.playerNumber + " graduated!");
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.careerCollegeTiles + "in college path") ;

        }
        else if(currentPlayer.careerCollegeTiles >= 12) {
            currentPlayer.collegeStatus = false;
            int extraMove = currentPlayer.careerCollegeTiles - 12;
            currentPlayer.tileNumber += extraMove;
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);
        }
        else {
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.careerCollegeTiles + "in college path");
        }
    }
}

class marriedMilestone implements milestone {
    public void milestoneAction(Player currentPlayer, int playerSpin) {
        currentPlayer.tileNumber += playerSpin;

        currentPlayer.Milestone[1] = true;
        currentPlayer.milestoneStatus += 1;

        currentPlayer.tileNumber = 14;

        System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);
        System.out.println("Player " + currentPlayer.playerNumber + " has hit a milestone!");
        System.out.println("Player " + currentPlayer.playerNumber + " is getting married!");
        System.out.println("Player " + currentPlayer.playerNumber + " spins to see how money they get from other players in wedding gifts");

        currentPlayer.marriedStatus = true;
    }
}

class nightSchoolMilestone implements milestone {
    public void milestoneAction(Player currentPlayer, int playerSpin) {
        currentPlayer.lifeNightSchoolTiles += playerSpin;

        if(currentPlayer.lifeNightSchoolTiles >= 10) {
            currentPlayer.nightSchoolStatus = false;
            int extraMove = currentPlayer.lifeNightSchoolTiles - 10;
            currentPlayer.tileNumber += extraMove;
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);
        }
        else {
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.lifeNightSchoolTiles + "in night school path");
        }
    }
}

class familyMilestone implements milestone {
    public void milestoneAction(Player currentPlayer, int playerSpin) {
        System.out.println("Player " + currentPlayer.playerNumber + " chose to go to family path");
        currentPlayer.lifeFamilyTiles += playerSpin;

        if(currentPlayer.lifeFamilyTiles == 4 || currentPlayer.lifeFamilyTiles == 7 || currentPlayer.lifeFamilyTiles == 8) {
            currentPlayer.kids += 1;
            System.out.println("Player " + currentPlayer.playerNumber + " got 1 baby");
            System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
        }
        else if (currentPlayer.lifeFamilyTiles == 2 || currentPlayer.lifeFamilyTiles == 5) {
            currentPlayer.kids += 2;
            System.out.println("Player " + currentPlayer.playerNumber + " has twins");
            System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
        }
        

        if(currentPlayer.lifeFamilyTiles >= 10) {
            currentPlayer.familyStatus = false;
            currentPlayer.kidsStatus = true;
            currentPlayer.lifeFamilyTiles = 10;
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.tileNumber);
        }
        else {
            System.out.println("Player " + currentPlayer.playerNumber + " on tile " + currentPlayer.lifeFamilyTiles + "in family path");
        }
    }
}

class kidsMilestone implements milestone {
    public void milestoneAction(Player currentPlayer, int playerSpin) {
        currentPlayer.careerCollegeTiles += playerSpin;
        
        if(currentPlayer.lifeFamilyTiles >= 10 && currentPlayer.Milestone[4] == false) {
            currentPlayer.lifeFamilyTiles = 10;
            currentPlayer.Milestone[4] = true;
            currentPlayer.milestoneStatus += 1;
            currentPlayer.kidsStatus = false;

            System.out.println("Player " + currentPlayer.playerNumber + " has hit a milestone!");
            System.out.println("Player " + currentPlayer.playerNumber + " will now spin to see how many kids they're going to have");
            System.out.println("Spin for baby:");
            System.out.println("1 - 3: 0 baby");
            System.out.println("4 - 6: 1 baby");
            System.out.println("7 - 8: twins");
            System.out.println("9 - 10: triplets");

            int babySpin = rand.nextInt(9) + 1;
            System.out.println("Player " + currentPlayer.playerNumber + " spun " + babySpin);

            if (babySpin >= 1 && babySpin <= 3) {
                currentPlayer.kids += 0;

                System.out.println("Player " + currentPlayer.playerNumber + " got 0 babies");
                System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
            }
            else if (babySpin >= 4 && babySpin <= 6) {
                currentPlayer.kids += 1;

                System.out.println("Player " + currentPlayer.playerNumber + " got 1 baby");
                System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
            }
            else if (babySpin >= 7 && babySpin <= 8) {
                currentPlayer.kids += 2;

                System.out.println("Player " + currentPlayer.playerNumber + " got twins");
                System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
            }
            else if (babySpin >= 9 && babySpin <= 10) {
                currentPlayer.kids += 3;

                System.out.println("Player " + currentPlayer.playerNumber + " got triplets");
                System.out.println("Player " + currentPlayer.playerNumber + " has a total of " + currentPlayer.kids + " kids");
            }
        }
    }
}
