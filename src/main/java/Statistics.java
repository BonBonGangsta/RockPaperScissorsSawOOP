class Statistics {

    private int[] stat;
    private int columnSize = 6;

    Statistics(){
        stat = new int[columnSize];
    }
    /**
     * A players stat is stored in the following order in the array:
     *  index 0 - Rounds Won
     *  index 1 - Rounds Loss
     *  index 2 - Rounds Tied
     *  index 3 - Games Won
     *  index 4 - Games Loss
     *  index 5 - Game Tied
     * @param index the index for which stat to retrieve
     * @return stat located in the super classes array at the index given
     */
    public int getStat(int index) {
        return stat[index];
    }

    // increase the value at the index by 1
    public void updateStat(int index) {
        stat[index]++;
    }

    // resets the statistics to 0
    public void resetStat(){
        stat = new int[columnSize];
    }

    /**
     * Construct a string with the overall stats of the player.
     * @return the overall statistics in a string.
     */
    public String getOverallStats(){
        String playerStats = "";
        playerStats += "- Rounds Won: " + stat[0] + " ";
        playerStats += "Rounds Loss: " + stat[1] + " ";
        playerStats += "Rounds Tied: " + stat[2] + " ";
        playerStats += "Games Won: " + stat[3] + " ";
        playerStats += "Games Loss: " + stat[4] + " ";
        playerStats += "Games Tied: " + stat[5] + " ";
        return playerStats;
    }
}
