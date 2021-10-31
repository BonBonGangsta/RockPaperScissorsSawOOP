import java.util.ArrayList;
import java.util.Scanner;

class Game extends SimulatedGame {
    // Rules are given in the SRS
    private static final String rules = "Winner of the round will be determined as follows:\n" +
            "a. Rock breaks Scissors and Saw therefore Rock wins over Scissors and Saw. Rock loses against Paper.\n" +
            "b. Scissors cuts Paper therefore Scissors win over Paper. Scissors lose against Rock and Saw.\n" +
            "c. Paper covers Rock therefore Paper wins over Rock. Paper loses against Scissors and Saw.\n" +
            "d. Saw cuts through Scissors and Paper therefore Saw wins over Scissors and Paper. Saw loses against Rock.\n" +
            "e. If Player and Computer make the same selection, there is a tie.\n";
    private final int[][] outcome = {{0, -1, 1, 1}, {1, 0, -1, -1}, {-1, 1, 0, -1}, {-1, 1, 1, 0}};
    private GameStat gameStats; // holds the current stats for the rounds ONLY
    private ComputerPlayer Hal3000; // Instance of a computer Player.
    private final int NUMOFCHOICES = outcome.length; // total number of choices available to the players.
    private final int NUMOFROUNDS = 3; // Number of rounds that will consist a game.

    // create a game with the number of players playing and a computer.
    Game(){
        Hal3000 = new ComputerPlayer();
        gameStats = new GameStat(3); // 2 human players and 1 computer.
    }

    /**
     * While the player wants to play, play a game of RPSS, this consists of 3 rounds.
     * @param players ArrayList of human Players able to play
     * @param input Scanner object that will be passed on by GameDriver.java
     */
    @Override
    public void playGame(ArrayList<GamePlayer> players, Scanner input){
        boolean stopPlaying = false;
        while (!stopPlaying){
            for (int rounds = 1; rounds <= NUMOFROUNDS; rounds++){
                // get the players choices
                int p1WoC = getPlayerWeaponOfChoice(players.get(0));
                int p2WoC = getPlayerWeaponOfChoice(players.get(1));
                // have the computer generate it's random choice
                int compWoC = getPlayerWeaponOfChoice(Hal3000);
                // calculate who wins between each player vs computer.
                calculateWinnerOfRound(players.get(0), 0, p1WoC, compWoC);
                calculateWinnerOfRound(players.get(1), 1, p2WoC, compWoC);
            }
            // once three rounds are over, calculate who is the winner of the game, best out of 3.
            GameWinner(players);
            // reset the game stats to 0
            gameStats.resetGameStats();
            // ask human players if they would like to continue playing.
            stopPlaying = !continuePlaying(input);
        }
    }


    /**
     * Based on the total rounds won by each player from Game's statistics. the Game winner is calculated, the players'
     * statistics is updated and the winner is announced.
     * @param players Arraylist of players active in the Game.
     */
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

    /**
     * The winner fo the round between a human player and computer is based on the index for which their
     * weapons corresponds to and it's outcome. Increase the human player's statistics and announce the winner of
     * the round between human and computer.
     * @param player human player
     * @param playerRow index for which the player corresponds to in the Game's Statistics, 0 = player 1, 1= player 2
     *                  3 = computer.
     * @param playerChoice human's choice of weapon.
     * @param computerChoice computer's randomly generated choice of weapon.
     */
    public void calculateWinnerOfRound(GamePlayer player, int playerRow, int playerChoice, int computerChoice){
        if(getWinner(playerChoice, computerChoice) == 0){
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has chosen " + getWeaponFromChoice(computerChoice) + " winner: Tie!");
            player.getStat().updateStat(2); // increase the player's tie stat for ROUNDS
            gameStats.increateGameStat(playerRow,2); // increase the current game's stat for ties for player
            gameStats.increateGameStat(2,2); // increase the current game's stat for ties for computer
        }
        else if (getWinner(playerChoice, computerChoice) > 0){
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has chosen " + getWeaponFromChoice(computerChoice) + " winner: " + player.getName());
            player.getStat().updateStat(0); // increase the player's win stat for ROUNDS
            gameStats.increateGameStat(playerRow,0); // increase the current game's stat for wins for player
            gameStats.increateGameStat(2,1);// increase the current game's stat for loss of computer
        }
        else {
            System.out.println(player.getName() + " has chosen " + getWeaponFromChoice(playerChoice) +
                    " and Computer has chosen " + getWeaponFromChoice(computerChoice) + " winner: Computer!");
            player.getStat().updateStat(1); // increase the player's win stat for ROUNDS
            gameStats.increateGameStat(playerRow,1); // increase the current game's stat for wins for player
            gameStats.increateGameStat(2,0);// increase the current game's stat for loss of computer
        }
    }

    /**
     * return the String definition of the weapon based on the number given.
     * @param choice int which matches the menu options.
     * @return name of the weapon based on the menu option.
     */
    private String getWeaponFromChoice(int choice) {
        if (choice == 1) return "Rock";
        else if (choice == 2) return "Paper";
        else if (choice == 3) return "Scissors";
        else return "Saw";
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
                if (ultimateChoice >= 1 && ultimateChoice <= 4) {
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

    /**
     * ask the player if they would like to continue playing.
     * @param input String of either Y/N will be accepted.
     * @return true if the player wants to continue otherwise, false.
     */
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

    /**
     * print out the rules.
     */
    @Override
    void getRules(){
        System.out.println(rules);
    }

}
