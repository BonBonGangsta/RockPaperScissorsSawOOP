import java.util.Scanner;

public class Player extends GamePlayer {
    private Statistics playerStat;

    Player(String name) {
        super(name);
        this.playerStat = new Statistics();
    }


    @Override
    public int playGame(int choices) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        boolean intGiven = false;
        while (!intGiven) {
            try {
                choice= input.nextInt();
            } catch (Exception e) {
                System.out.println("ERROR: Please enter a number.");
            }
            intGiven = true;
        }
        return choice;
    }

    @Override
    public Statistics getStat() {
        return playerStat;
    }
    @Override
    public String overallPlayerStat(){
        return playerName + " " + playerStat.getOverallStats();
    }
}
