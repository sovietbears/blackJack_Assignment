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
            System.out.println("Player " + player++ + " current balance: "
                    + p.getBalance()
                    + " Player score: " + this.dealer.scoreHand(p.getHand()));
            System.out.println("hand is " + p.getHand().toString());
        }

        this.dealer.playDealer();
        System.out.println("Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
        //System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString());
        this.dealer.settleBets();
        this.dealer.newHand();
        this.playerBalances();
    }

    public void multipleHands() {
        Scanner sc = new Scanner(System.in);
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
                System.out.println("Player " + player++ + " current balance: "
                        + p.getBalance()
                        + " Player score: " + this.dealer.scoreHand(p.getHand()));
                //System.out.println("hand is " + p.getHand().toString());
            }

            this.dealer.playDealer();
            System.out.println("Dealer score: " + this.dealer.scoreHand(this.dealer.getDealerHand()));
            //System.out.println("Dealer hand: " + this.dealer.getDealerHand().toString());
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

    public static void basicGame() {
        Scanner sc = new Scanner(System.in);
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
                + "\n-------------------------------------------------------");
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
            usrInput = sc.nextInt();
            if (usrInput > 0 && usrInput < 5) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            basicTable.perHand();
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;

                        case 2:
                            basicTable.multipleHands();
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        case 3:
                            saveGame(basicTable);
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        case 4:
                            basicTable = loadGame(basicTable);
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;

                    }
                    if (basicTable.players.isEmpty()) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("All players ran out of money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = sc.nextInt();

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
    
    public static void humanGame(){
        Scanner sc = new Scanner(System.in);
        int usrInput;
        boolean runGame = true;
        boolean currentGame;

        System.out.println("\nWelcome to a human game of Blackjack. \n\n"
                + "During the game you will be able to play out your hands\n"
                + "You may continue for as long as you want to or if you are \n"
                + "bored of this then you will have the option to terminate the"
                + "game. Good luck."
                + "\n-------------------------------------------------------");
        while (runGame) {
            currentGame = true;
            BlackjackTable humanTable = new BlackjackTable();
            Player p1 = new HumanPlayer();
            Player p2 = new BasicPlayer();
            humanTable.players.add(p1);
            humanTable.players.add(p2);
            humanTable.dealer.assignPlayers(humanTable.players);
            System.out.println("<<<<<<<<<<<<New game has begun.>>>>>>>>>>>>>> \n");
            choiceMessage();
            usrInput = sc.nextInt();
            if (usrInput > 0 && usrInput < 5) {
                while (currentGame) {
                    switch (usrInput) {
                        case 1:
                            humanTable.perHand();
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;

                        case 2:
                            humanTable.multipleHands();
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        case 3:
                            saveGame(humanTable);
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        case 4:
                            humanTable = loadGame(humanTable);
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;
                        default:
                            System.out.println("Please select from options given. "
                                    + "You said " + usrInput + " which is not allowed."
                                    + "\nTry again.");
                            choiceMessage();
                            usrInput = sc.nextInt();
                            break;

                    }
                    if (humanTable.players.isEmpty()) {
                        currentGame = false;
                        boolean errorCheck = true;
                        System.out.println("All players ran out of money.");
                        while (errorCheck) {

                            System.out.println("\nWould you like to start again?"
                                    + "\n1. Yes \n2. No");

                            usrInput = sc.nextInt();

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
    

    public static void saveGame(BlackjackTable table) {
        String filename = "blackJackGame.ser";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(table);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BlackjackTable loadGame(BlackjackTable table) {
        BlackjackTable tLoad = table;
        try {
            FileInputStream fis = new FileInputStream("blackJackGame.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            tLoad = (BlackjackTable) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return tLoad;
    }

    public static void main(String[] args) {
        basicGame();
    }

}
