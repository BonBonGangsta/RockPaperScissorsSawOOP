public class GameStat {

    private int[][] gameStats;
    private int rows = 3;
    private int columns = 3;

    /**
     * GameStat will use a similar layout of Statistics however, it will be used to keep track of a current Simulated
     * Games' statistics, the number of rows corresponds to the number players meaning:
     *  row 0 = player 1
     *  row 1 = player 2
     *   ...
     *  row n = computer
     *  Note: the last row will be the computer if there is one.
     *
     *  columns are to represent Rounds Won, Rounds Loss, Round Tied in that order starting at index 0.
     */
    GameStat(){
        gameStats = new int[rows][columns];
    }
    GameStat(int numberOfPlayers){
        rows = numberOfPlayers;
        gameStats = new int[rows][columns];
    }

    public int getGameStat(int row, int column){
        return gameStats[row][column];
    }

    public void increateGameStat(int row, int column){
        gameStats[row][column]++;
    }

    public void resetGameStats(){
        gameStats = new int[rows][columns];
    }

}
