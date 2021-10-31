import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GameDriver {

    private final static int MAXSIZE = 20;
    private final static int MINSIZE = 5;

    public static String player1Name ="";
    public static  String player2Name = "";
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
     * tests if the the first name is the same as the second name regardless of capitalization.
     * @param name1 first main name.
     * @param name2 name to compare it to.
     * @return weather they are the same.
     */
    public static boolean uniqueName(String name1, String name2){
        return !name1.equalsIgnoreCase(name2);
    }


    public static boolean validateName(String name1, String name2){
        return ((name1.length() > MINSIZE && name1.length() < MAXSIZE) && (uniqueName(name1, name2)));
    }

    /** MENU SECTION **/

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

    public static void showMenu(){
        System.out.println(menuText);
    }


    /** Statistics generation **/

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
