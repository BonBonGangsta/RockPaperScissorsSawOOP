import java.util.Random;

public class ComputerPlayer extends GamePlayer{

    private Statistics compStats;
    public Random ran;
    ComputerPlayer(){
        super("Computer");
    }
    @Override
    public int playGame(int choices) {
        ran = new Random(System.currentTimeMillis());
        return ran.nextInt(choices) + 1;
    }

    @Override
    public Statistics getStat(){
        return compStats;
    }
    @Override
    public String overallPlayerStat(){
        return playerName + " " + compStats.getOverallStats();
    }
}
