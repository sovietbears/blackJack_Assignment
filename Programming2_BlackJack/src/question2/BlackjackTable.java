package question2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import question2.Card;
import question2.Hand;
import static question2.Hand.removeCollection;

/**
 * The main driver class that can initialize blackjack games. Made up of basic,
 * human and advanced game.
 *
 * @author Daniel Carey
 */
public class BlackjackTable implements Serializable {

    private List<Player> players;
    private BlackjackDealer dealer;
    private List<Card> cardsPlayed;
    private transient Scanner sc;
    private static final int MAX_PLAYERS = 8;
    private static final int MAX_BET = 500;
    private static final int MIN_BET = 1;

    static final long serialVersionUID = 3L;

    /**
     * Constructor to start a blackjackTable
     */
    public BlackjackTable() {
        this.players = new LinkedList<Player>();
        this.dealer = new BlackjackDealer();
        this.cardsPlayed = new LinkedList<Card>();
        this.sc = new Scanner(System.in);
    }

    public void playerBalances() {
        Iterator<Player> itPlayer = this.players.iterator();
        Player p;
        int pnum = 1;
        while (itPlayer.hasNext()) {
            p = itPlayer.next();
            System.out.println("Player " + pnum++ + " balance is: " + p.getBalance());
        }
    }

    public void perHand() {
        System.out.println("\n---------------NEW HAND---------------------");
        this.cardsPlayed = new LinkedList<Card>();
        this.dealer.takeBets();
        this.dealer.dealFirstCards();
        Iterator<Player> itPlayer = this.players.iterator();
        Player p;
        int player = 1;
        while (itPlayer.hasNext()) {
            p = itPlayer.next();
            this.dealer.play(p);
            System.out.println(""
                    + "---------------------Player " + player++ + "-----------------------\n"
                    + " Current bet: " + p.getBet());
            System.out.println("hand is " + p.getHand().toString()
                    + "\n--------------------------------------------------------\n");
        }
        this.dealer.playDealer();
        System.out.println("\n-------------------------Dealer-----------------------------------\n"
                + "Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
        System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString()
                + "\n---------------------------------------------------------------\n");
        this.dealer.settleBets();

        for (Player player1 : players) {
            Hand temp = new Hand(player1.newHand());
            this.cardsPlayed.addAll(temp.getCardsInHand());
        }
        this.cardsPlayed.addAll(this.dealer.getDealerHand().getCardsInHand());
        for (Player player1 : players) {
            player1.viewCards(this.cardsPlayed);
        }

        removeCollection(this.dealer.getDealerHand());
        this.playerBalances();
    }

    public void multipleHands() {
        int runs;
        System.out.println("Please state how many hands would you like to play.");
        runs = sc.nextInt();
        while (runs <= 0) {
            System.out.println("Please enter a number above 0.");
            runs = sc.nextInt();
        }
        for (int i = 0; i < runs; i++) {
            if (this.players.isEmpty()) {
                break;
            }
            System.out.println("\n---------------NEW HAND---------------------");
            this.cardsPlayed = new LinkedList<Card>();
            this.dealer.takeBets();
            this.dealer.dealFirstCards();
            Iterator<Player> itPlayer = this.players.iterator();
            Player p;

            int player = 1;

            while (itPlayer.hasNext()) {
                p = itPlayer.next();
                this.dealer.play(p);
                System.out.println(""
                        + "---------------------Player " + player++ + "-----------------------\n"
                        + " Current bet: " + p.getBet());
                System.out.println("hand is " + p.getHand().toString()
                        + "\n-------------------------------------------------------\n");
            }

            this.dealer.playDealer();
            System.out.println("\n-------------------------Dealer-----------------------------------\n"
                    + "Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
            System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString()
                    + "\n-----------------------------------------------------------------\n");
            this.dealer.settleBets();
            for (Player player1 : players) {
                Hand temp = new Hand(player1.newHand());
                this.cardsPlayed.addAll(temp.getCardsInHand());
            }
            this.cardsPlayed.addAll(this.dealer.getDealerHand().getCardsInHand());
            for (Player player1 : players) {
                player1.viewCards(this.cardsPlayed);
            }

            removeCollection(this.dealer.getDealerHand());
            this.playerBalances();
        }
    }

    public static void choiceMessage() {
        System.out.println(
                "\nPlease choose from list by writing corrisponding number.\n"
                + "1. Go through hand.\n"
                + "2. Go through multiple hands.\n"
                + "3. Save Game.\n"
                + "4. Load Game.\n"
                + "5. Quit Game.\n");
    }

    public static void choiceMessageHuman() {
        System.out.println(
                "\nPlease choose from list by writing corrisponding number.\n"
                + "1. Play next hand.\n"
                + "2. Quit game.\n"
                + "3. Save Game.\n"
                + "4. Load Game.\n");
    }

    public static void basicGame() {
        int usrInput;
        boolean runGame = true;
        boolean currentGame;

        System.out.println("\nWelcome to a basic game of Blackjack. \n\n"
                + "During the game you will be able to choose to either \n"
                + "1. Go through hand by hand.\n"
                + "2. Go through multiple hands.\n"
                + "3. Save Game.\n"
                + "4. Load Game.\n"
                + "5. Quit Game.\n"
                + "If all the players run out of funds, you will be asked"
                + "if you would like to run another basic game simulation"
                + "\n-----------------------------------------------------------");
        while (runGame) {
            currentGame = true;
            BlackjackTable basicTable = new BlackjackTable();
            Player p1 = new BasicPlayer();
            Player p2 = new BasicPlayer();
            Player p3 = new BasicPlayer();
            Player p4 = new BasicPlayer();
            basicTable.players.add(p1);
            basicTable.players.add(p2);
            basicTable.players.add(p3);
            basicTable.players.add(p4);
            basicTable.dealer.assignPlayers(basicTable.players);
            System.out.println("<<<<<<<<<<<<New game has begun.>>>>>>>>>>>>>> \n");
            choiceMessage();
            usrInput = basicTable.sc.nextInt();
            if (usrInput > 0 && usrInput < 6) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            basicTable.perHand();
                            choiceMessage();
                            usrInput = basicTable.sc.nextInt();
                            break;

                        case 2:
                            basicTable.multipleHands();
                            choiceMessage();
                            usrInput = basicTable.sc.nextInt();
                            break;
                        case 3:
                            saveGame(basicTable);
                            System.out.println("Game has been saved.");
                            choiceMessage();
                            usrInput = basicTable.sc.nextInt();
                            break;
                        case 4:
                            basicTable = loadGame();
                            System.out.println("Game has been loaded.");
                            choiceMessage();
                            basicTable.sc = new Scanner(System.in);
                            usrInput = basicTable.sc.nextInt();
                            break;
                        case 5:
                            runGame = false;
                            currentGame = false;
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = basicTable.sc.nextInt();
                            break;

                    }
                    if (basicTable.players.isEmpty() && usrInput != 5) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("All players ran out of money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = basicTable.sc.nextInt();

                            switch (usrInput) {
                                case 1:
                                    errorCheck = false;
                                    break;
                                case 2:
                                    System.out.println("Game Terminated. "
                                            + "Thank you for playing.");
                                    runGame = false;
                                    errorCheck = false;
                                    break;
                                default:
                                    System.out.println("Please enter a valid"
                                            + " option.");
                                    errorCheck = true;
                                    break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Please select from options given. "
                        + "You said " + usrInput + " which is not allowed.");
            }

        }
        System.out.println("Thank you for playing Blackjack. Hopefully you "
                + "had fun. Game Terminated.");
    }

    public static void intermediateGame() {
        int usrInput;
        boolean runGame = true;
        boolean currentGame;

        System.out.println("\nWelcome to an Intermediate game of Blackjack. \n\n"
                + "During the game you will be able to choose to either \n"
                + "1. Go through hand by hand.\n"
                + "2. Go through multiple hands.\n"
                + "3. Save Game.\n"
                + "4. Load Game.\n"
                + "5. Quit Game.\n"
                + "If all the players run out of funds, you will be asked"
                + "if you would like to run another basic game simulation"
                + "\n-----------------------------------------------------------");
        while (runGame) {
            currentGame = true;
            BlackjackTable intermediateTable = new BlackjackTable();
            Player p1 = new IntermediatePlayer();
            Player p2 = new IntermediatePlayer();
            Player p3 = new IntermediatePlayer();
            Player p4 = new IntermediatePlayer();
            intermediateTable.players.add(p1);
            intermediateTable.players.add(p2);
            intermediateTable.players.add(p3);
            intermediateTable.players.add(p4);
            intermediateTable.dealer.assignPlayers(intermediateTable.players);
            System.out.println("<<<<<<<<<<<<New game has begun.>>>>>>>>>>>>>> \n");
            choiceMessage();
            usrInput = intermediateTable.sc.nextInt();
            if (usrInput > 0 && usrInput < 6) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            intermediateTable.perHand();
                            choiceMessage();
                            usrInput = intermediateTable.sc.nextInt();
                            break;

                        case 2:
                            intermediateTable.multipleHands();
                            choiceMessage();
                            usrInput = intermediateTable.sc.nextInt();
                            break;
                        case 3:
                            saveGame(intermediateTable);
                            System.out.println("Game has been saved.");
                            choiceMessage();
                            usrInput = intermediateTable.sc.nextInt();
                            break;
                        case 4:
                            intermediateTable = loadGame();
                            System.out.println("Game has been loaded.");
                            choiceMessage();
                            intermediateTable.sc = new Scanner(System.in);
                            usrInput = intermediateTable.sc.nextInt();
                            break;
                        case 5:
                            runGame = false;
                            currentGame = false;
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = intermediateTable.sc.nextInt();
                            break;

                    }
                    if (intermediateTable.players.isEmpty() && usrInput != 5) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("All players ran out of money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = intermediateTable.sc.nextInt();

                            switch (usrInput) {
                                case 1:
                                    errorCheck = false;
                                    break;
                                case 2:
                                    System.out.println("Game Terminated. "
                                            + "Thank you for playing.");
                                    runGame = false;
                                    errorCheck = false;
                                    break;
                                default:
                                    System.out.println("Please enter a valid"
                                            + " option.");
                                    errorCheck = true;
                                    break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Please select from options given. "
                        + "You said " + usrInput + " which is not allowed.");
            }

        }
        System.out.println("Thank you for playing Blackjack. Hopefully you "
                + "had fun. Game Terminated.");
    }

    public static void advancedGame() {
        int usrInput;
        boolean runGame = true;
        boolean currentGame;

        System.out.println("\nWelcome to an Advanced game of Blackjack. \n\n"
                + "During the game you will be able to choose to either \n"
                + "1. Go through hand by hand.\n"
                + "2. Go through multiple hands.\n"
                + "3. Save Game.\n"
                + "4. Load Game.\n"
                + "5. Quit Game.\n"
                + "If all the players run out of funds, you will be asked"
                + "if you would like to run another basic game simulation"
                + "\n-----------------------------------------------------------");
        while (runGame) {
            currentGame = true;
            BlackjackTable advancedTable = new BlackjackTable();
            Player p1 = new BasicPlayer();
            Player p2 = new IntermediatePlayer();
            Player p3 = new AdvancedPlayer();
            advancedTable.players.add(p1);
            advancedTable.players.add(p2);
            advancedTable.players.add(p3);
            advancedTable.dealer.assignPlayers(advancedTable.players);
            System.out.println("<<<<<<<<<<<<New game has begun.>>>>>>>>>>>>>> \n");
            choiceMessage();
            usrInput = advancedTable.sc.nextInt();
            if (usrInput > 0 && usrInput < 6) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            advancedTable.perHand();
                            choiceMessage();
                            usrInput = advancedTable.sc.nextInt();
                            break;

                        case 2:
                            advancedTable.multipleHands();
                            choiceMessage();
                            usrInput = advancedTable.sc.nextInt();
                            break;
                        case 3:
                            saveGame(advancedTable);
                            System.out.println("Game has been saved.");
                            choiceMessage();
                            usrInput = advancedTable.sc.nextInt();
                            break;
                        case 4:
                            advancedTable = loadGame();
                            System.out.println("Game has been loaded.");
                            choiceMessage();
                            advancedTable.sc = new Scanner(System.in);
                            usrInput = advancedTable.sc.nextInt();
                            break;
                        case 5:
                            runGame = false;
                            currentGame = false;
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = advancedTable.sc.nextInt();
                            break;

                    }
                    if (advancedTable.players.isEmpty() && usrInput != 5) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("All players ran out of money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = advancedTable.sc.nextInt();

                            switch (usrInput) {
                                case 1:
                                    errorCheck = false;
                                    break;
                                case 2:
                                    System.out.println("Game Terminated. "
                                            + "Thank you for playing.");
                                    runGame = false;
                                    errorCheck = false;
                                    break;
                                default:
                                    System.out.println("Please enter a valid"
                                            + " option.");
                                    errorCheck = true;
                                    break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Please select from options given. "
                        + "You said " + usrInput + " which is not allowed.");
            }

        }
        System.out.println("Thank you for playing Blackjack. Hopefully you "
                + "had fun. Game Terminated.");

    }

    public static void humanGame() {
        int usrInput;
        boolean runGame = true;
        boolean currentGame;

        System.out.println("\nWelcome to a human game of Blackjack. \n\n"
                + "During the game you will be able to play out your hands\n"
                + "You may continue for as long as you want to or if you are \n"
                + "bored of this then you will have the option to terminate the"
                + "game. Good luck."
                + "\n-----------------------------------------------------------");
        while (runGame) {
            currentGame = true;
            BlackjackTable humanTable = new BlackjackTable();
            Player p1 = new BasicPlayer();
            Player p2 = new HumanPlayer();
            humanTable.players.add(p1);
            humanTable.players.add(p2);
            humanTable.dealer.assignPlayers(humanTable.players);
            System.out.println("<<<<<<<<<<<<New game has begun.>>>>>>>>>>>>>> \n");
            choiceMessageHuman();
            usrInput = humanTable.sc.nextInt();
            if (usrInput > 0 && usrInput < 5) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            humanTable.perHand();
                            choiceMessageHuman();
                            usrInput = humanTable.sc.nextInt();
                            break;
                        case 2:
                            runGame = false;
                            currentGame = false;
                            break;
                        case 3:
                            saveGame(humanTable);
                            System.out.println("Game has been saved.");
                            choiceMessageHuman();
                            usrInput = humanTable.sc.nextInt();
                            break;
                        case 4:
                            humanTable = loadGame();
                            System.out.println("Game has been loaded.");
                            choiceMessageHuman();
                            humanTable.sc = new Scanner(System.in);
                            usrInput = humanTable.sc.nextInt();
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessageHuman();
                            usrInput = humanTable.sc.nextInt();
                            break;

                    }
                    if (humanTable.players.isEmpty()) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("You have lost all your money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = humanTable.sc.nextInt();

                            switch (usrInput) {
                                case 1:
                                    errorCheck = false;
                                    break;
                                case 2:
                                    System.out.println("Game Terminated. "
                                            + "Thank you for playing.");
                                    runGame = false;
                                    errorCheck = false;
                                    break;
                                default:
                                    System.out.println("Please enter a valid"
                                            + " option.");
                                    errorCheck = true;
                                    break;
                            }
                        }
                    }

                }
            } else {
                System.out.println("Please select from options given. "
                        + "You said " + usrInput + " which is not allowed.");
            }

        }
        System.out.println("Thank you for playing Blackjack. Hopefully you "
                + "had fun. Game Terminated.");
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static double handAverage(ArrayList<Integer> hands) {
        Integer sum = 0;
        if (!hands.isEmpty()) {
            for (Integer hand : hands) {
                sum += hand;
            }
            return round(sum.doubleValue() / hands.size(), 2);
        }
        return round(sum, 2);
    }

    public static void advancedGameAverage() throws IOException {
        //Make a table for players
        BlackjackTable advancedTable = new BlackjackTable();
        //construct players
        Player p1 = new BasicPlayer();
        Player p2 = new IntermediatePlayer();
        Player p3 = new AdvancedPlayer();

        //Prepare arrays that will store average profit/loss per deck played
        //for each player
        ArrayList<Integer> basicAverageDeck = new ArrayList<Integer>();
        ArrayList<Integer> intermediateAverageDeck = new ArrayList<Integer>();
        ArrayList<Integer> advancedAverageDeck = new ArrayList<Integer>();

        //Prepare arrays to store all the averages for each player
        ArrayList<Double> basicAverageTotal = new ArrayList<Double>();
        ArrayList<Double> intermediateTotal = new ArrayList<Double>();
        ArrayList<Double> advancedAverageTotal = new ArrayList<Double>();

        //Variables to store current player balance
        int basicBalance;
        int intermediateBalance;
        int advancedBalance;

        //Add player to the table
        advancedTable.players.add(p1);
        advancedTable.players.add(p2);
        advancedTable.players.add(p3);

        //Assign players to the dealer
        advancedTable.dealer.assignPlayers(advancedTable.players);

        //Loop with a high number of iterations
        for (int i = 0; i < 1000; i++) {
            //Check if players have been kicked due to insufficient balance
            if (advancedTable.players.isEmpty()) {
                break;
            }

            //Assign current balance
            basicBalance = p1.getBalance();
            intermediateBalance = p2.getBalance();
            advancedBalance = p3.getBalance();

            //Create list to store cards played
            advancedTable.cardsPlayed = new LinkedList<Card>();
            advancedTable.dealer.takeBets();
            advancedTable.dealer.dealFirstCards();

            //to check if new deck has been introduced
            if (advancedTable.dealer.getDeckStatus()) {
                //Average is calculated and added to array
                basicAverageTotal.add(handAverage(basicAverageDeck));
                intermediateTotal.add(handAverage(intermediateAverageDeck));
                advancedAverageTotal.add(handAverage(advancedAverageDeck));
                //Prepare empty arraylists
                basicAverageDeck = new ArrayList<Integer>();
                intermediateAverageDeck = new ArrayList<Integer>();
                advancedAverageDeck = new ArrayList<Integer>();
                //update current balance
                basicBalance = p1.getBalance();
                intermediateBalance = p2.getBalance();
                advancedBalance = p3.getBalance();
            }
            //Deal cards to players
            Iterator<Player> itPlayer = advancedTable.players.iterator();
            Player p;
            while (itPlayer.hasNext()) {
                p = itPlayer.next();

                advancedTable.dealer.play(p);
            }
            advancedTable.dealer.playDealer();
            advancedTable.dealer.settleBets();

            //Calculate profit/loss each hand and add to array list
            basicAverageDeck.add(p1.getBalance() - basicBalance);
            intermediateAverageDeck.add(p2.getBalance() - intermediateBalance);
            advancedAverageDeck.add(p3.getBalance() - advancedBalance);

            for (Player player1 : advancedTable.players) {
                Hand temp = new Hand(player1.newHand());
                advancedTable.cardsPlayed.addAll(temp.getCardsInHand());
            }
            advancedTable.cardsPlayed.addAll(advancedTable.dealer.getDealerHand().getCardsInHand());
            for (Player player1 : advancedTable.players) {
                player1.viewCards(advancedTable.cardsPlayed);
            }
            removeCollection(advancedTable.dealer.getDealerHand());
        }

        System.out.println("BASIC PLAYER AVERAGE:\t\t" + basicAverageTotal.toString());
        System.out.println("INTERMEDIATE PLAYER AVERAGE:\t" + intermediateTotal.toString());
        System.out.println("ADVANCED PLAYER AVERAGE:\t" + advancedAverageTotal.toString());
        writeToText(basicAverageTotal, intermediateTotal, advancedAverageTotal);
        
    }
    
    public static void writeToText(ArrayList<Double> basic, 
            ArrayList<Double> inter,ArrayList<Double> adv) 
            throws FileNotFoundException, IOException{
        File file = new File("averageProfits.txt");
        FileOutputStream fileOut = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(fileOut);
        pw.println("Basic Player Averages");
        for (Double n : basic) {
            pw.println(n);
        }
        pw.println("Intermediate Player Averages");
        for (Double n : inter) {
            pw.println(n);
        }
        pw.println("Advanced Player Averages");
        for (Double n : adv) {
            pw.println(n);
        }
        pw.close();
        fileOut.close();
    }

    public static void saveGame(BlackjackTable table) {
        String filename = "blackJackGame.ser";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(table);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BlackjackTable loadGame() {
        BlackjackTable tLoad = new BlackjackTable();
        try {
            FileInputStream fis = new FileInputStream("blackJackGame.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            tLoad = (BlackjackTable) in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return tLoad;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Blackjack simulator.");
        Scanner sc = new Scanner(System.in);
        boolean choice = true;
        int userInput;
        do {
            System.out.println("\nPlease choose one of the following:"
                + "\n 1. Play basic game."
                + "\n 2. Play intermediate game."
                + "\n 3. Play advanced game."
                + "\n 4. Play human game."
                + "\n 5. Get  average profit/loss per deck using advanced game."
                + "\n    This will save to text file as well"
                + "\n 6. Quit.");
            userInput = sc.nextInt();
            switch (userInput) {
                case 1:
                    basicGame();
                    break;
                case 2:
                    intermediateGame();
                    break;
                case 3:
                    advancedGame();
                    break;
                case 4:
                    humanGame();
                    break;
                case 5:
                    advancedGameAverage();
                    break;
                case 6:
                    choice = false;
                    break;
                default:
                    System.out.println("You have entered " + userInput
                    + " which is not one of the choices given. "
                + "please pick again."
                + "\n 1. Play basic game."
                + "\n 2. Play intermediate game."
                + "\n 3. Play advanced game."
                + "\n 4. Play human game."
                + "\n 5. Get  average profit/loss per deck using advanced game."
                + "\n This will save to text file as well."
                + "\n 6. Quit.");
                    userInput = sc.nextInt();
                    break;
            }
        }while(choice);
        System.out.println("Thank you for playing. Program terminated.");
    }

}
