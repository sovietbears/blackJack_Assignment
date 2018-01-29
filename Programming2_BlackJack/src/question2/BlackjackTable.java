package question2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The main driver class that can initialize blackjack games. Made up of basic,
 * human and advanced game.
 *
 * @author Daniel Carey
 */
public class BlackjackTable implements Serializable {

    private List<Player> players;
    private BlackjackDealer dealer;
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
                    +"\n--------------------------------------------------------\n");
        }

        this.dealer.playDealer();
        System.out.println("\n-------------------------Dealer-----------------------------------\n"
                + "Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
        System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString()
        + "\n---------------------------------------------------------------\n");
        this.dealer.settleBets();
        this.dealer.newHand();
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
                    +"\n-------------------------------------------------------\n");
            }

            this.dealer.playDealer();
            System.out.println("\n-------------------------Dealer-----------------------------------\n"
                + "Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
        System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString()
        + "\n-----------------------------------------------------------------\n");
            this.dealer.settleBets();
            this.dealer.newHand();
            this.playerBalances();
        }
    }

    public static void choiceMessage() {
        System.out.println(
                "\nPlease choose from list by writing corrisponding number.\n"
                + "1. Go through hand.\n"
                + "2. Go through multiple hands.\n"
                + "3. Save Game.\n"
                + "4. Load Game.");
    }

    public static void choiceMessageHuman() {
        System.out.println(
                "\nPlease choose from list by writing corrisponding number.\n"
                + "1. Play next hand.\n"
                + "2. Quit game.\n"
                + "3. Save Game.\n"
                + "4. Load Game.");
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
            if (usrInput > 0 && usrInput < 5) {
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
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = basicTable.sc.nextInt();
                            break;

                    }
                    if (basicTable.players.isEmpty()) {
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
            if (usrInput > 0 && usrInput < 5) {
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
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = intermediateTable.sc.nextInt();
                            break;

                    }
                    if (intermediateTable.players.isEmpty()) {
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
            if (usrInput > 0 && usrInput < 5) {
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
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = advancedTable.sc.nextInt();
                            break;

                    }
                    if (advancedTable.players.isEmpty()) {
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

    public static void main(String[] args) {
        //basicGame();
        //intermediateGame();
         advancedGame();
        //humanGame();
    }

}
