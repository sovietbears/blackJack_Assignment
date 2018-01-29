/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static question1.Card.isBlackjack;
import question1.Hand;
import question1.Card;

/**
 * This class represents a basic Blackjack player, following simple strategy to
 * win the game.
 *
 * @author Daniel Carey
 */
public class BasicPlayer implements Player, Serializable {
    
    private int balance;
    private Hand playerHand;
    private int bet;
    private List<Card> dealerHand;
    private Card dealerCard;
    private boolean newDeck;
    private boolean isBlackjack;
    
    static final long serialVersionUID = 1L;

    public BasicPlayer() {
        this.playerHand = new Hand();
        this.balance = 200;
    }

    /**
     * Clear previous hand ready for new cards
     *
     * @return Player returns old hand
    *
     */
    @Override
    public Hand newHand() {
        Hand oldHand = this.playerHand;
        this.playerHand.removeCollection(this.playerHand);
        return oldHand;
    }

    /**
     * This method represents the bet that will be made by the basic player.
     * makeBet method should be called before any cards have been dealt;
     *
     * @return the bet this player will make which is at default of 10;
     */
    @Override
    public int makeBet() {
        this.bet = 0;
        if (balance >= 10) {
            this.bet += 10;
            return 10;
            } 
        else {
            return 0;
            }

    }

    /**
     * getBet: @return the bet for the current game. This must not exceed the
     * players balance
     */
    @Override
    public int getBet() {
        return this.bet;
    }

    /**
     * getBalance: @return the players current total pot.
     */
    @Override
    public int getBalance() {
        return this.balance;
    }

    /**
     * hit: this method should determine whether the player wants to take a
     * card. return true if a card is required, false otherwise. 
    *
     */
    @Override
    public boolean hit() {
        return this.getHandTotal() < 17;
    }

    /**
     * takeCard: If a card is requested by hit() it should be added to the
     * players hand with this method
     *
     * @param c
     */
    @Override
    public void takeCard(Card c) {
        this.playerHand.addCard(c);
    }

    /**
     * settleBet: The value passed is positive if the player won, negative
     * otherwise.
     *
     * @param p
     * @return true if the player has funds remaining, false otherwise.
     */
    @Override
    public boolean settleBet(int p) {    
        switch(p){
            case -1:
                this.balance -= this.bet;
                return this.balance >= 10;
            case 0:
                return this.balance >= 10;
            case 1:
                this.balance += this.bet;
                return this.balance >= 10;
            case 2: 
                this.balance += (this.bet * 2);
                return this.balance >= 10;
        }
        return this.balance >= 10; //Unreachable, uet needed.
    }

    /**
     *
     * getHandTotal: @return the hand total score. If the payer has one or more
     * aces , this should return the highest HARD total that is less than 21. So
     * for example ACE, THREE should return 14. ACE, THREE, TEN should return
     * 14. ACE, ACE, TWO, THREE should return 17. ACE, ACE, TEN should return
     * 12. NEED WORK ON THIS!
     */
    @Override
    public int getHandTotal() {
        ArrayList<Integer> vals = this.playerHand.handCalcArr();
        Collections.sort(vals);
        int arrSize = vals.size();
        int dist = Math.abs(vals.get(0) - 21);
        int index = 0;
        if (arrSize == 1)
        {
            return vals.get(0);
        }
        for (int i = 1; i < arrSize; i++) {
            int closeDist = Math.abs(vals.get(i) - 21);
             if (vals.get(i) < 21 && closeDist <= dist){
                index = i;
                dist = closeDist;
            }
        }
        System.out.println("score Hand Result: " + vals.get(index));
        
        return vals.get(index);
    }

    /**
     *
     * blackjack: @return true if the current hand is a black jack (ACE, TEN or
     * PICTURE CARD)
     */
    @Override
    public boolean blackjack() {
        Iterator<Card> it = this.playerHand.iterator();
        Card a = it.next();
        Card b = it.next();
        if(isBlackjack(a,b)){
            this.isBlackjack = true;
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isBlackJack(){
        return this.isBlackjack;
    }

    /**
     *
     * isBust: @return true if the current hand is bust
     */
    @Override
    public boolean isBust() {
        return this.playerHand.isOver(21);
    }

    /**
     *
     * getHand: @return the current hand
     */
    @Override
    public Hand getHand() {
        return this.playerHand;
    }

    /**
     * viewDealerCard. This method allows the dealer to show the player their
     * card.
     *
     * @param c Dealers first card
     */
    @Override
    public void viewDealerCard(Card c) {
        this.dealerCard = c;
    }

    /**
     * viewCards: This method allows the dealer to show all the cards that were
     * played after a hand is finished. If the player is card counting, they
     * will need this info
     */
    @Override
    public void viewCards(List<Card> cards) {
        this.dealerHand = cards;
    }

    /**
     * newDeck. This method is called by the dealer to tell them the deck has
     * been reshuffled
     */
    @Override
    public void newDeck() {
        this.newDeck = true;
    }
    

}
