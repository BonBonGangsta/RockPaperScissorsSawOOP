import java.util.ArrayList;
import java.util.Scanner;

abstract class SimulatedGame {
    private String rules;

    abstract void playGame(ArrayList<GamePlayer> players, Scanner input);

    abstract void getRules();
}
