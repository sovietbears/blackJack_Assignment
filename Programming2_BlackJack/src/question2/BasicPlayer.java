package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
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

    protected int balance;
    protected Hand playerHand;
    protected  int bet;
    protected List<Card> handsDealt;
    protected Card dealerCard;
    protected boolean newDeck;
    protected boolean isBlackjack;

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
        if (balance >= 10) {
            this.bet = 10;
            return 10;
        } else {
            return 0;
        }

    }

    public void setBet(int n) {
        this.bet = n;
    }

    /**
     * getBet: @return the bet for the current game. This must not exceed the
     * players balance
     *
     * @return
     */
    @Override
    public int getBet() {
        return this.bet;
    }

    /**
     * getBalance: @return the players current total pot.
     *
     * @return
     */
    @Override
    public int getBalance() {
        return this.balance;
    }

    /**
     * hit: this method should determine whether the player wants to take a
     * card. return true if a card is required, false otherwise.
     *
     * @return
     */
    @Override
    public boolean hit() {
        System.out.println("Dealer's Card: " + this.dealerCard.toString());
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
        switch (p) {
            case -1:
                this.balance -= this.bet;
                return this.balance >= 1;
            case 0:
                return this.balance >= 1;
            case 1:
                this.balance += this.bet;
                return this.balance >= 1;
            case 2:
                this.balance += (this.bet * 2);
                return this.balance >= 1;
        }
        return this.balance >= 10; //Unreachable, yet needed.
    }

    /**
     *
     * getHandTotal: @return the hand total score. If the payer has one or more
     * aces , this should return the highest HARD total that is less than 21. So
     * for example ACE, THREE should return 14. ACE, THREE, TEN should return
     * 14. ACE, ACE, TWO, THREE should return 17. ACE, ACE, TEN should return
     * 12. NEED WORK ON THIS!
     *
     * @return
     */
    @Override
    public int getHandTotal() {
        ArrayList<Integer> vals = this.playerHand.handCalcArr();
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
     *
     * blackjack: @return true if the current hand is a black jack (ACE, TEN or
     * PICTURE CARD)
     *
     * @return
     */
    @Override
    public boolean blackjack() {
        Iterator<Card> it = this.playerHand.iterator();
        Card a = it.next();
        Card b = it.next();
        if (isBlackjack(a, b)) {
            this.isBlackjack = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean isBlackJack() {
        return this.isBlackjack;
    }

    /**
     *
     * isBust: @return true if the current hand is bust
     *
     * @return
     */
    @Override
    public boolean isBust() {
        return this.playerHand.isOver(21);
    }

    /**
     *
     * getHand: @return the current hand
     *
     * @return
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
     *
     * @param cards
     */
    @Override
    public void viewCards(List<Card> cards) {
        this.handsDealt = cards;
    }

    /**
     * newDeck. This method is called by the dealer to tell them the deck has
     * been re-shuffled
     */
    @Override
    public void newDeck() {
        this.newDeck = true;
    }

}
