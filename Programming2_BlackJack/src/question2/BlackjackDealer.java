package question2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import question1.Deck;
import question1.Hand;

/**
 *  BlackjackDealer class will represent a a simulation of a black jack 
 * dealer who will oversee the game. 
 * @author Daniel Carey
 */
public class BlackjackDealer implements Dealer {
    
    private Hand dealerHand;
    private List<Player> players;
    private List<Integer> bets;
    private Deck deck;
    private boolean isBlackjack;

    public BlackjackDealer(){
        this.dealerHand = new Hand();
        this.deck       = new Deck();
        this.deck.shuffle();
        this.players    = new LinkedList<Player>();
        this.bets       = new LinkedList<Integer>();   
        
    }
    
    /**
     * Method to give the dealer access to players who are playing
     * @param p 
     */
    @Override
    public void assignPlayers(List<Player> p) {
        this.players = p;
    }
    
    /**
    * takeBets: Take the bets for all the assigned players
    */
    @Override
    public void takeBets() {
        Iterator<Player> itPlayer = this.players.iterator();
        while(itPlayer.hasNext()){
            int bet = itPlayer.next().getBet();
            this.bets.add(bet);
        }
    }
    
    public void checkDeck(){
        if (this.deck.size() < (52/4)) {
            this.deck.newDeck();
            this.deck.shuffle();
            Iterator<Player> itPlayer = this.players.iterator();
            Player p;
            while(itPlayer.hasNext()){
                p = itPlayer.next();
                p.newDeck();
            }
        }
    }
    
    /**
    * dealFirstCards: Deal first two cards to each player, and one card to the dealer           
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
        this.dealerHand.addCard(this.deck.deal());
    }
    
    /**
    *play: play the hand of player p. 
    * Keep asking if the player wants a card until they stick or they bust
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

            }
            return p.getHandTotal();
        }
       
    }
    
    /** playDealer: Play the dealer hand
    * The dealer must take cards until their total is 17 or higher. 
    * @return dealer score
    */   
    @Override
    public int playDealer() {
        if ()
        while (scoreHand(this.dealerHand) < 17) {
            this.dealerHand.addCard(this.deck.deal());
        }
        return scoreHand(this.dealerHand);
    }
    
    /**
    * scoreHand: The dealer should score the player hands, not rely on the player 
    * to do it.
    * @param h
    * @return score. Note if there are aces, this should be the highest possible 
    * value that is less than 21
    * ACE, THREE   should return 14.
    * ACE, THREE, TEN should return 14.
    * ACE, ACE, TWO, THREE should return 17.
    * ACE, ACE, TEN should return 12.
    */    
    @Override
    public int scoreHand(Hand h) {
        int handTotal = 0;
        ArrayList<Integer> vals = h.handCalcArr();
        Collections.reverse(vals);
        for (Integer val : vals) {
            if (val <= 21) {
                handTotal = val;
            }
            else{
                handTotal = val;
            }
        }
        return handTotal;
    }
    
    /** settleBets: This should settle all the bets at the end of the hand.
    * 
    */    
    @Override
    public void settleBets() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        BlackjackDealer d = new BlackjackDealer();
        
        List<Player> players = new LinkedList<Player>();
        Player p1 = new BasicPlayer();
        Player p2 = new BasicPlayer();
        Player p3 = new BasicPlayer();
        
        players.add(p1);
        players.add(p2);
        players.add(p3);
        
        d.assignPlayers(players);
        
        d.takeBets();
        
        d.dealFirstCards();
        
        System.out.println("P1 hand: " + p1.getHand().toString() + 
                "Hard card Total: " + p1.getHandTotal());
        p1.getHand();
        
    }
    
}
