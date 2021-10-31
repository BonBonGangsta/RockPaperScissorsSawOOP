abstract class GamePlayer {
    String playerName;
    Statistics stat;
    GamePlayer(String name){
        this.playerName = name;
        stat = new Statistics();
    }

    public String getName(){
        return this.playerName;
    }

    abstract public int playGame(int choices);

    abstract public Statistics getStat();
    abstract public String overallPlayerStat();
}
