import java.util.ArrayList;
import java.util.Scanner;

public class GameDriver {
    // settings that can be changed if needed.
    private final static int MAXSIZE = 20;
    private final static int MINSIZE = 5;

    // names of players, for now it's 2. initilize them as empty strings so that Java won't throw warnings.
    public static String player1Name ="";
    public static  String player2Name = "";
    // Menu stored as a String to call at any point in time and as many times as needed.
    // menu can change if needed.
    public final static String menuText = "Please select a menu item by entering its corresponding number and hitting enter" +
            ":\n1. Play game\n2. Show game rules\n3. Show Statistics\n4. Exit";

    public final static String goodByeMessage = "GoodBye"; // message can be changed if needed.


    public static void main(String[] args){
        boolean  gameExited = false;
        ArrayList<GamePlayer> victims = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        getPlayerName(sc);
        Player p1 = new Player(player1Name);
        Player p2 = new Player(player2Name);
        victims.add((GamePlayer) p1);
        victims.add((GamePlayer) p2);
        SimulatedGame game;
        game = new Game();
        /**
         * if you want to play the extra feature comment the last line of code and uncomment this code
         * game = new ExtraGame();
         */

        while (!gameExited) {
            int choice = getMenuSelection(sc);
            if(choice == 1){ game.playGame(victims, sc);}
            else if (choice == 2){ game.getRules(); }
            else if (choice == 3){overallStats(victims);}
            else{
                sc.close();
                exit();
            }
        }
    }
    /** GETTING NAMES **/
    /**
     * While the names are not valid, ask the players for their names.
     * @param input Scanner that will be used to take in the names.
     */
    public static void getPlayerName(Scanner input){
            while (!validateName(player1Name, player2Name)){
            System.out.println("What is" +
                    " the name of the first player?");
            player1Name = input.nextLine();
            if (!validateName(player1Name, player2Name)){
                System.out.println("ERROR: the name for the first player is not valid, please" +
                        " enter a name that is more than " + MINSIZE + " and less than " + MAXSIZE + " characters");
            }
        }
        while (!validateName(player2Name, player1Name)){
            System.out.println("What is the name of the second player?");
            player2Name = input.nextLine();
            if (!validateName(player2Name, player1Name)){
                System.out.println("ERROR: the name for the second player is not valid, please" +
                        " enter a name that is more than " + MINSIZE + " and less than " + MAXSIZE + " characters"
                + " and that is unique to " + player1Name);
            }
        }
    }


    /**
     * tests if the first name is the same as the second name regardless of capitalization.
     * @param name1 first main name.
     * @param name2 name to compare it to.
     * @return weather they are the same.
     */
    public static boolean uniqueName(String name1, String name2){
        return !name1.equalsIgnoreCase(name2);
    }


    /**
     * In order to validate names, we need the first name to be greater than the MINSIZE and less than MAXSIZE (exclusive)
     * and the names must be unique to each other.
     * @param name1 name of the player to test the length and uniqueness.
     * @param name2 name of the other player needing to compare to.
     * @return true if it meets the requirements.
     */
    public static boolean validateName(String name1, String name2){
        return ((name1.length() > MINSIZE && name1.length() < MAXSIZE) && (uniqueName(name1, name2)));
    }

    /** MENU SECTION **/

    /**
     * Display the menu and allow the players to choose from the menu selection, if they enter anything invalid, throw
     * an error and keep prompting.
     * @param input Scanner to take in the player's choices.
     * @return return the menu selection as an integer.
     */
    public static int getMenuSelection(Scanner input){
        int menuChoice = 0;
        showMenu();
        try{
            menuChoice = input.nextInt();
            if (menuChoice >=1 && menuChoice <=4){
                return menuChoice;
            }
            else{
                System.out.println("ERROR: Please user an number within the menu choices.");
            }
        }
        catch (Exception e){
            System.out.println("ERROR: Invalid input given, please enter a number within the menu choices.");
        }
        return menuChoice;
    }

    /**
     * print out the menu.
     */
    public static void showMenu(){
        System.out.println(menuText);
    }


    /** Statistics generation **/

    /**
     * Call each human players in the arraylist to return their overall statistics, and print it. Then
     * calculate the overall winner of the Game by comparing total number of Games won.
     * @param players Arraylist of Human Players.
     */
    public static void overallStats(ArrayList<GamePlayer> players){
        for(GamePlayer player: players){
            System.out.println(player.overallPlayerStat());
        }
        if (players.get(0).getStat().getStat(3) > players.get(1).getStat().getStat(3)){
            System.out.println("Overall Human winner is: " + players.get(0).getName());
        }
        else if(players.get(0).getStat().getStat(3) == players.get(1).getStat().getStat(3)){
            System.out.println("Overall Human winner is: a TIE!");
        }
        else{
            System.out.println("Overall Human Winner is: " + players.get(1).getName());
        }
    }

    /** miscellaneous **/

    /**
     * close the software after displaying the goodbye message
     */
    public static void exit(){
        System.out.println(goodByeMessage);
        System.exit(0);
    }

}
