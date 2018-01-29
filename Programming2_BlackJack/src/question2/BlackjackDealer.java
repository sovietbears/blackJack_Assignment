package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import question1.Card;
import static question1.Card.isBlackjack;
import question1.Deck;
import question1.Hand;

/**
 *  BlackjackDealer class will represent a a simulation of a black jack 
 * dealer who will oversee the game. 
 * @author Daniel Carey
 */
public class BlackjackDealer implements Dealer, Serializable {
    
    private Hand dealerHand; //Stores dealer's hand
    private List<Card> cardsPlayed; //Stores all the cards that have been played during a hand.
    private List<Player> players; //Players that are assigned to the dealer
    private List<Integer> bets; //Stores bets made by players
    private Deck deck; //Stores deck that is used for the game
    private boolean isBlackjack = false; //Stores true if dealer has blackjack
                                         //value is false by default.
    static final long serialVersionUID = 2L;
    
    /**
     * Constructor method for the dealer which creates dealer hand, the deck
     * that is going to be played which in turn will be shuffled, create empty
     * LinkedList of players and bets that the players have made.
     */
    public BlackjackDealer(){
        this.dealerHand = new Hand();
        this.deck       = new Deck();
        this.deck.shuffle(); //Shuffles deck.
        this.cardsPlayed= new LinkedList<Card>();
        this.players    = new LinkedList<Player>();
        this.bets       = new LinkedList<Integer>();   
        
    }
    
    /**
     * Method to assign players to the dealer object
     * @param p List of players that the dealer object will interact with. 
     */
    @Override
    public void assignPlayers(List<Player> p) {
        this.players = p;
    }
    
    /**
    * Method to take bets from player objects that are assigned to the dealer.
    * These bets will then be added to the dealer's bets LinkedList for 
    * further processing
    */
    @Override
    public void takeBets() {
        //Iterator to traverse through player objects
        Iterator<Player> itPlayer = this.players.iterator();
        Player p; 
        while(itPlayer.hasNext()){
            p = itPlayer.next();
            p.makeBet();
            //retrive bet from player
            int bet = p.getBet();
            //store bet in the dealer object
            this.bets.add(bet);
        }
    }
    
    /**
     * Method to check if the deck has more than 1/4 cards in it.
     * If it doesn't, new deck will be introduced, shuffeled and all the 
     * players that are currently assigned to the dealer will be notified.
     */
    public void checkDeck(){
        if (this.deck.size() < (52/4)) {
            System.out.println("CURREND DECK SIZE: " + this.deck.size());
            System.out.println("NEW DECK");
            this.deck.newDeck();
            this.deck.shuffle();
            //Iterator to traverse players
            Iterator<Player> itPlayer = this.players.iterator();
            Player p; 
            while(itPlayer.hasNext()){
                p = itPlayer.next();
                //Let player know that new deck is introduced
                p.newDeck();
            }
        }
    }
    
    /**
    * Method to deal first two cards to the players that are currently assigned
    * to the dealer by iterating through player objects.
    * At each player iteration deck will be checked if it has more than 1/4 of
    * cards present. Finally one card will be dealt to the dealer.
    **/
    @Override
    public void dealFirstCards() {
        Iterator<Player> itPlayer = this.players.iterator();
        Player p;
        while(itPlayer.hasNext()){
            this.checkDeck();
            p = itPlayer.next();
            p.takeCard(this.deck.deal());
            p.takeCard(this.deck.deal());
        }
        Card dealerFirstCard = this.deck.deal();
        this.dealerHand.addCard(dealerFirstCard);  
        
        Iterator<Player> itPlayer2 = this.players.iterator();
        while (itPlayer2.hasNext()) {
            p = itPlayer2.next();
            p.viewDealerCard(dealerFirstCard);
        }
        
    }
    
    /**
     * Method to play the hand of player p. Keep asking if the player 
     * wants another card until they stick or bust
     * @param p current player object.
    * @return final score of the hand
    **/
    @Override
    public int play(Player p) {
        this.checkDeck();
        if(p.blackjack()){
            return 21;
        }
        else{
            while(p.hit() && !(p.isBust())){
                p.takeCard(this.deck.deal());
                
               // System.out.println("Player took card" + p.getHand().toString());
               // REMOVE ON FINAL
            }
            return p.getHandTotal();
        }
       
    }
    
    /**
     * Method to check if the dealer has a blackjack in his hand and store it 
     * in the dealer object for further processing.
     * @return true if the hand is blackjack false otherwise
     */
    public boolean blackjack(){
        Iterator<Card> it = this.dealerHand.iterator();
        Card a = it.next();
        Card b = it.next();
        
        return isBlackjack(a,b);
    }
    
    /** 
     * Method to play the dealer's hand. The dealer will take cards until his 
     * total score is 17 or higher. However, if the card dealt at the start of 
     * dealer's hand equates to blackjack score of 21 will be returned and
     * isBlackjack will be assigned true.
    * @return dealer score
    */   
    @Override
    public int playDealer() {
        this.dealerHand.addCard(this.deck.deal());
        if (this.blackjack() == true){
            isBlackjack = true;
            return 21;
        }
        else {
            while (scoreHand(this.dealerHand) < 17) {
                this.dealerHand.addCard(this.deck.deal());
            }
            return scoreHand(this.dealerHand);
        }
    }
    
    /**
    * scoreHand: The dealer should score the player hands, not rely on the player 
    * to do it.
    * @param h dealer's or player's hand
    * @return score. Note if there are aces, this should be the highest possible 
    * value that is less than 21
    * ACE, THREE   should return 14.
    * ACE, THREE, TEN should return 14.
    * ACE, ACE, TWO, THREE should return 17.
    * ACE, ACE, TEN should return 12.
    */    
    @Override
    public int scoreHand(Hand h) {
        ArrayList<Integer> vals = h.handCalcArr();
        TreeSet<Integer> set = new TreeSet<Integer>(vals);
        if (vals.size() == 1) {
            return vals.get(0);
        }
        else if(set.floor(21) == null){
            return set.ceiling(21);
        }
        else{
            return set.floor(21);
        }
    }
    
    
    /**
     * This method will settle all the bets after dealer's turn.
     * If player sticks on any value lower than 21 and dealer busts, the 
     * player wins sum equal to their bet.
     * 
     * If dealer is not bust, the scores are compared. If dealer's hand is lower
     * than the player then they win sum equal to their bet. Player loses if its
     * lower than the dealer's hand.
     * 
     * If player and dealer have equal score the player will retain their bet.
     * 
     * Exception: If player has blackjack and dealer does not. The player wins
     * twice their bet.
     * 
     * If player and the dealer has blackjack then it is also a push.
     * 
     */    
    @Override
    public void settleBets() {
        Iterator<Player> itPlayer = this.players.iterator();
        
        Player p;
        int dealerScore = this.scoreHand(dealerHand);
        int playerScore;
        boolean pBlackjack;
        
        
        while (itPlayer.hasNext()) {
            p = itPlayer.next();
            playerScore = this.scoreHand(p.getHand());
            //System.out.println("SETTLE BET SCORE: Player: " + playerScore + " Dealer: " + dealerScore);
            pBlackjack = p.blackjack();
           
            if(pBlackjack && !(this.isBlackjack)){
                p.settleBet(2);
                //System.out.println("Player has blackjack");
            }
            else if(!(p.isBust()) && dealerScore > 21){
                p.settleBet(1);
                //System.out.println("Player won with dealer bust. " + p.isBust());
            }
            else if(dealerScore > playerScore || p.isBust()){
                if (!(p.settleBet(-1))){
                   itPlayer.remove();
                }
                //System.out.println("Player is bust or lost.");
            }
            else if(dealerScore < playerScore){
                p.settleBet(1);
                //System.out.println("Player won, due to higher score.");
            }
            else if(dealerScore == playerScore){
                p.settleBet(0);
               //System.out.println("Scores match.");
            }          
        }
    }
    
    
    public void newHand(){
        System.out.println("NEW HAND ACTIVATED");
        Iterator<Player> it = this.players.iterator();
        Player p;
        Card c;
        Hand temp;
        while(it.hasNext()){
            p = it.next();
            System.out.println("PLAYERS: " + p.toString());
            p.newHand().toString();
//            Iterator<Card> cIt = temp.iterator();
//            while (cIt.hasNext()) {
//                c = cIt.next();
//                System.out.println("Cards added. " + c.toString());
//                this.cardsPlayed.add(c);
//            }
        }
        Iterator<Player> it2 = this.players.iterator();
        while (it2.hasNext()) {
            p = it2.next();
            p.viewCards(this.cardsPlayed);
        }
        this.dealerHand.removeCollection(dealerHand);
        this.cardsPlayed.removeAll(cardsPlayed);
    }
    
    /**
     * Test method to check current cards in dealer's hand
     * @return 
     */
    public Hand getDealerHand(){
        return this.dealerHand;
    }
    
    public static void main(String[] args) {
        
        //Testing the constructor
        BlackjackDealer d = new BlackjackDealer();
        
        //Assigning new players
        List<Player> players = new LinkedList<Player>();
        Player p1 = new BasicPlayer();
        Player p2 = new BasicPlayer();
        Player p3 = new BasicPlayer();
        
        players.add(p1);
        players.add(p2);
        players.add(p3);
        
        d.assignPlayers(players);
        
        //Testing if all bets have been recorded.
        d.takeBets();
        System.out.println("\nPlayer bets.\n");
        int pNum = 1;
        for (Player player : players) {
            System.out.println("player p" + pNum++ + " Balance: " + player.getBalance() + " bet is: " + player.getBet());
        }
        d.dealFirstCards();
        
        
//        System.out.println("P1 hand: " + p1.getHand().toString() + 
//                "Hard card Total: " + p1.getHandTotal());
        
        //Testing play method
        System.out.println("\nPlayed p1 test: " + d.play(p1));
        System.out.println("Played p2 test: " + d.play(p2));
        System.out.println("Played p3 test: " + d.play(p3));
        
        //Testing blackjack and playDealer methods
        //System.out.println("\nCurrent dealer card/s: " + d.getDealerHand().toString());
        System.out.println("\nPlay Dealer result: " + d.playDealer());
        System.out.println("\nCurrent dealer card/s: " + d.getDealerHand().toString());
        //System.out.println("Bool value if dealer has blackjack." + d.blackjack());
        
        //Testing settle bet function
        d.settleBets();
        System.out.println("\nPlayer balances.\n");
        pNum = 1;
        for (Player player : players) {
            System.out.println("player p" + pNum++ + " balance is: " + player.getBalance());
            //System.out.println(player.getHand().toString());
        }
        
    }
    
}
