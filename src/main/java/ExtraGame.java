import java.util.ArrayList;
import java.util.Scanner;

/**
 * Extra Game is an extra feature I decided to add in order to spice the project up. This is shows that the program
 * is module to a certain degree.
 *
 * I can implement this Rock, Paper, Scissors, Lizard, Spock game which is an actual Zero Sum Game.
 */
public class ExtraGame extends SimulatedGame{
    private static String rules = "Winner of the round will be determined as follows:\n" +
            "Scissors cuts Paper.\n" +
            "Paper covers Rock.\n" +
            "Rock crushes Lizard.\n" +
            "Lizard poisons Spock.\n" +
            "Spock smashes Scissors.\n" +
            "Scissors decapitate Lizard.\n" +
            "Lizard eats Paper.\n" +
            "Paper disproves Spock.\n" +
            "Spock vaporizes Rock.\n" +
            "And as always, Rock crushes Scissors.\n" ;
    private final int[][] outcome = {{0, -1, 1, 1, -1}, // rock index 0
                                     {1, 0, -1, -1, 1}, // paper index 1
                                     {-1, 1, 0, 1, -1}, // scissors index 2
                                     {-1, 1, -1, 0, 1}, // lizard index 3
                                     {1, -1, 1, -1, 0}}; // Spock index 4
    private GameStat gameStats; // holds the current stats for the rounds ONLY
    private ComputerPlayer Hal3000; // Instance of a computer Player.
    private final int NUMOFCHOICES = outcome.length; // total number of choices available to the players.
    private final int NUMOFROUNDS = 3;

    ExtraGame(){
        Hal3000 = new ComputerPlayer();
        gameStats = new GameStat(3); // 2 human players and 1 computer.
    }

    @Override
    public void playGame(ArrayList<GamePlayer> players, Scanner input){
        boolean stopPlaying = false;
        while (!stopPlaying){
            for (int rounds = 1; rounds <= NUMOFROUNDS; rounds++){
                int p1WoC = getPlayerWeaponOfChoice(players.get(0));
                int p2WoC = getPlayerWeaponOfChoice(players.get(1));
                int compWoC = getPlayerWeaponOfChoice(Hal3000);
                calcualteWinnerOfRound(players.get(0), Hal3000, 0, p1WoC, compWoC);
                calcualteWinnerOfRound(players.get(1), Hal3000, 1, p2WoC, compWoC);
            }
            GameWinner(players);
            gameStats.resetGameStats();
            stopPlaying = !continuePlaying(input);
        }
    }


    private void GameWinner(ArrayList<GamePlayer> players) {
        // if the sum of all the player's wins from the current game is greater than the computer's wins
        // one of the players is the winner.
        if ((gameStats.getGameStat(0,0) + gameStats.getGameStat(1,0)) >
                gameStats.getGameStat(2,0)) {
            // one of the human players is the winner
            // if both player's wins are the same, check who has lost the least amount.
            if(gameStats.getGameStat(0,0) == gameStats.getGameStat(1,0)){
                if(gameStats.getGameStat(0,1) < gameStats.getGameStat(1,1)){
                    // player 1 has lost the least amount of times, player 1 is the winner.
                    players.get(0).getStat().updateStat(3);
                    players.get(1).getStat().updateStat(4);
                    System.out.println("Game Winner is: " + players.get(0).getName());
                }
                else if(gameStats.getGameStat(0,1) == gameStats.getGameStat(1,1)){
                    // both human players share the same number of wins and losses
                    players.get(0).getStat().updateStat(5);
                    players.get(1).getStat().updateStat(5);
                    System.out.println("Game Winner is: TIE BETWEEN HUMANS!!!");
                }
                else {
                    // player 2 has lost the least amount of times, player 2 is the winner.
                    players.get(0).getStat().updateStat(4);
                    players.get(1).getStat().updateStat(3);
                    System.out.println("Game Winner is: " + players.get(1).getName());
                }
            }
            else if(gameStats.getGameStat(0,0) > gameStats.getGameStat(1,0)){
                // player 1 has won the most amount of times, player 1 is the winner.
                players.get(0).getStat().updateStat(3);
                players.get(1).getStat().updateStat(4);
                System.out.println("Game Winner is: " + players.get(0).getName());
            }
            else{
                // player 2 has won the most amount of times, player 2 is the winner.
                players.get(0).getStat().updateStat(4);
                players.get(1).getStat().updateStat(3);
                System.out.println("Game Winner is: " + players.get(1).getName());
            }

        }
        // if the sum of the player's wins combined is equal to the wins of the computer, it is a tie!
        else if ((gameStats.getGameStat(0,0) + gameStats.getGameStat(1,0)) ==
                gameStats.getGameStat(2,0)){
            for (GamePlayer player: players){
                player.getStat().updateStat(5);
            }
            System.out.println("Game Winner is : A TIE BETWEEN EVERYONE!!!");
        }
        else{ // computer is the overall winner of the game since it won the most rounds.
            for (GamePlayer player : players)
            {
                player.getStat().updateStat(4);
            }
            System.out.println("Game Winner is : COMPUTER!!!");

        }

    }

    public void calcualteWinnerOfRound(GamePlayer player, GamePlayer computer , int playerRow, int playerChoice, int computerChoice){
        if(getWinner(playerChoice, computerChoice) == 0){
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has choosen " + getWeaponFromChoice(computerChoice) + " winner: Tie!");
            player.getStat().updateStat(2); // increase the player's tie stat for ROUNDS
            gameStats.increateGameStat(playerRow,2); // increase the current game's stat for ties for player
            gameStats.increateGameStat(2,2); // increase the current game's stat for ties for computer
        }
        else if (getWinner(playerChoice, computerChoice) > 0){
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has choosen " + getWeaponFromChoice(computerChoice) + " winner: " + player.getName());
            player.getStat().updateStat(0); // increase the player's win stat for ROUNDS
            gameStats.increateGameStat(playerRow,0); // increase the current game's stat for wins for player
            gameStats.increateGameStat(2,1);// increase the current game's stat for loss of computer
        }
        else {
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has choosen " + getWeaponFromChoice(computerChoice) + " winner: Computer!");
            player.getStat().updateStat(1); // increase the player's win stat for ROUNDS
            gameStats.increateGameStat(playerRow,1); // increase the current game's stat for wins for player
            gameStats.increateGameStat(2,0);// increase the current game's stat for loss of computer
        }
    }

    private String getWeaponFromChoice(int choice) {
        if (choice == 1) return "Rock";
        else if (choice == 2) return "Paper";
        else if (choice == 3) return "Scissors";
        else if (choice == 4) return "Lizard";
        else return "Spock";
    }

    /**
     * Returns the outcome of the player's choice against the computer choice. Note that 1 needs to be removed in
     * order to align with the index that corresponds to the choice.
     * @param playerChoice player's choice
     * @param computerChoice computer's choice
     * @return -1 loss, 0 tie, 1 win for the player.
     */
    public int getWinner(int playerChoice, int computerChoice){
        return outcome[playerChoice - 1][computerChoice - 1];
    }

    public int getPlayerWeaponOfChoice(GamePlayer player){
        if (player instanceof Player) {
            while (true) {
                System.out.println(player.getName() + ", what weapon will you choose? '1' Rock, '2' Paper, '3' Scissors" +
                        " '4' Saw.");
                int ultimateChoice = player.playGame(NUMOFCHOICES);
                if (ultimateChoice >= 1 && ultimateChoice <= NUMOFCHOICES) {
                    return ultimateChoice;
                } else {
                    System.out.println("ERROR: please enter a number corresponding to the weapon selection");
                }
            }
        }
        else {
            return player.playGame(NUMOFCHOICES);
        }

    }

    public boolean continuePlaying(Scanner input){
        System.out.println("Would you like to keep playing? 'Y' for Yes , 'N' for No");
        boolean response = false;
        try{
            String answer = input.next();
            if (answer.equalsIgnoreCase("y")){
                response = true;
            }
            else if (answer.equalsIgnoreCase("n")){
                response = false;
            }
        }
        catch (Exception e){
            System.out.println("ERROR: please enter 'Y' for Yes, 'N' for No.");
        }
        return response;
    }
    @Override
    void getRules(){
        System.out.println(rules);
    }

}
