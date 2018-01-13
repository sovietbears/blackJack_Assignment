package programming2_blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import programming2_blackjack.Card.Rank;
import programming2_blackjack.Card.Suit;
import programming2_blackjack.Card.compareAscending;

/**
 * A class that represents the players hand that contains cards 
 * @author Daniel Carey
 */

public class Hand implements Serializable, Iterable<Card> {
    private ArrayList<Card> handArr;
    private ArrayList<Integer> cardRankCnt;
    private ArrayList<Integer> handVal;
    static final long serialVersionUID = 102;
    
    
    /**
     * Constructor initializing an empty hand
     */
    public Hand(){
        this.handArr = new ArrayList<>();
        this.cardRankCnt = new ArrayList<>();
        this.handVal = new ArrayList<>();
    }
    
    /**
     * Constructor that adds array of cards to a hand and
     * stores vales for each card in an array. Additionally it will calculate
     * all the possible total values of the current hand.
     * @param cards of cards to be added

     */
    public Hand(Card [] cards){
        this();
        for (Card card : cards) {
            addCard(card);
        }
    }
    
    
    /**
     * Constructor that adds two hands together
     * @param h hand you wish to add
     */
    public Hand(Hand h){
        this();
        addHand(h);
    }
    
    
    /**
     * Method that fills in the cardRankCnt array with Integers that represent 
 cards from handArr
     */
    private void cardRankCntArr(){
        ArrayList<Integer> cardRankCntArr = new ArrayList<>();
        long freq;
        for (Card c : this.handArr) {
            freq = handArr.stream().filter( a -> a.getRank() == c.getRank() ).count();
            cardRankCntArr.add((int)freq);
        }
        this.cardRankCnt = cardRankCntArr;
    }
    
    /**
     * Method that calculates the total value(s) of the cardRankCnt ArrayList containing 
 Integers which represent cards in hand. Also it checks if any Aces are 
     * present in the array and calculates alternative values that can be made 
     * with current hand. Then inserts these into handVal ArrayList
     */
    private void handCalcArr(){ 
        ArrayList<Integer> cardValArr = new ArrayList<>();
        int sum = 0;
        int aceCnt = 0;
        int rank = 0;
        for (Card c : handArr) {
            rank = c.getRank().getValue();
            if (c.getRank() == Rank.ACE) {
                aceCnt++;
            }
            sum += rank;
        } 
        cardValArr.add(sum);
        int tempSum = sum;
        for (int i = 1; i <= aceCnt; i++) {
            cardValArr.add(tempSum - (i * 10));
            tempSum = sum;
        }
        this.handVal = cardValArr;
    }
    
    /**-----------------WIP---------------------------------
     * Initial toString code. Might need to be changed for better presentation.
     * @return 
     */
    @Override
    public String toString() {
        String result = "";
        int cnt = 0;
        for (Card card : handArr) {
            result += card.toString() + ", Card rank frequency: " 
                    + cardRankCnt.get(cnt++) + "\n";           
        }
        result += "\nHand values(s):  ";
        for(Integer i : handVal){
            result += i.toString() + ", ";
        }
        result = result.substring(0, result.length() - 2);
        result += "\n";
       
        return result;
    }
    
    /**
     * Method to add a single card to the hand
 cardRankCnt and handVal will be updated
     * @param card Card object that you want to add to the hand 
     */
    public void addCard(Card card){
        this.handArr.add(card);
        this.cardRankCntArr();
        this.handCalcArr();
        
    }
    
    /**
     * Method to add a collection of cards to the hand
 cardRankCnt and handVal will be updated
     * @param cards ArrayList containing Card objects
     */
    public void addCollection(Collection<Card> cards){
        this.handArr.addAll(cards);
        this.cardRankCntArr();
        this.handCalcArr();        
        
    }
    
    /**
     * Method to add a hand to the current hand
 cardRankCnt and handVal will be updated
     * @param hand Hand object containing ArrayList of Cards
     */
    public void addHand(Hand hand){
        this.handArr.addAll(hand.handArr);
        this.cardRankCntArr();
        this.handCalcArr(); 
    }
    
    /**
     * Removes a card object if present
 cardRankCnt and handVal will be updated
     * @param card Card object that you want to remove
     * @return boolean value, true if card was removed, false if card was not
     * found in collection.
     */
    public boolean removeCard(Card card){
        if (this.handArr.contains(card)){
            this.handArr.remove(card);
            this.cardRankCntArr();
            this.handCalcArr(); 
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Removes all the cards from hand
 cardRankCnt and handVal will be updated
     * @param h hand that you want to clear
     * @return boolean value, true if collection is not 
     * empty and all cards have been removed, false if collection is already 
     * empty.
     */
    public boolean removeCollection(Hand h){
        if (!(h.handArr.isEmpty())) {
            h.handArr.clear();
            this.cardRankCntArr();
            this.handCalcArr();
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Removes a card at specific index and returns that card
     * @param pos index of the card object that you want removed
     * @return Card object that was at the index you have specified.
     */
    public Card removeCardFromPos(int pos){
        Card card = this.handArr.get(pos);
        this.handArr.remove(pos);
        this.cardRankCntArr();
        this.handCalcArr();
        return card;
    }
    
    
        //--------------Iterator Commands Start---------------------
    @Override
    public Iterator<Card> iterator() {
        boolean isSorted;
        return new Iterator<Card>() {
            private final Iterator<Card> it = handArr.iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Card next() {
               return it.next();
            }
            
        };
    }
//--------------Iterator Commands End-----------------------
    
    public void sortDecending(){
        Collections.sort(handArr);
    }
    
    public void sortAcending(){
        Comparator compAcending = new compareAscending();
        Collections.sort(handArr, compAcending);
    }
    
    
    
    
    public static void main(String[] args) {
        Deck d = new Deck();
        d.shuffle();
        System.out.println("Deck: \n" + d.toString() + "Size: " + d.size());
        Card dealtCards [] = new Card[2];
        Card card = d.deal();
        dealtCards [0] = card;
        dealtCards [1] = d.deal();
        
        ArrayList<Card> cTest = new ArrayList<>();
        cTest.add(d.deal());
        cTest.add(d.deal());
        
        System.out.println(Arrays.toString(dealtCards));
        
        Hand h1 = new Hand();
        Hand h2 = new Hand(dealtCards);
        Hand h4 = new Hand(dealtCards);
        Hand h3 = new Hand(h2);
        System.out.println("\nTesting Constructor 2: h2");
        System.out.println(h2.toString());
        
        System.out.println("\nTesting Constructor 3 and adding cards: h3");
        h3.addCard(card);
        h3.addCard(d.deal());
        System.out.println(h3.toString());
        
        System.out.println("\nTesting adding collection: h3");
        h3.addCollection(cTest);
        System.out.println(h3.toString());
        
        System.out.println("\nTesting adding hands: h3");
        h3.addHand(h2);
        System.out.println(h3.toString());
        
        //Testing default iterator
        Iterator<Card> it = h3.iterator();
        System.out.println("\n\nNormal Iterator test start\n");
        while (it.hasNext()) {
            Card card2 = it.next();
            System.out.println("Card: " + card2);
        }
        
//        System.out.println("\nTesting removing cards: h3");
//        h3.removeCard(card);
//        h3.removeCard(card);
//        System.out.println(h3.toString());
//        
//        System.out.println("\nTesting removing collection: h3");
//        h3.removeCollection(h3);
//        System.out.println(h3.toString());
//        h3.addCard(card);
//        h3.addCard(d.deal());
//        System.out.println(h3.toString());
//        
//        System.out.println("\nTesting removing card from hand with index: h3");
//        Card card2 = h3.removeCardFromPos(0);
//        System.out.println("Current hand: \n" + h3.toString()
//        + "\nCard that was removed from h3: " + card2);
        
        System.out.println("\nTesting using Card compareTo for sorting. h3");
        h3.sortDecending();
        System.out.println(h3.toString());
        
        System.out.println("\nTesting using Card CompareAscending for sorting. h3");
        h3.sortAcending();
        System.out.println(h3.toString());
        
        //Testing default iterator
        Iterator<Card> it2 = h3.iterator();
        System.out.println("\n\nNormal Iterator test start\n");
        while (it2.hasNext()) {
            Card card2 = it2.next();
            System.out.println("Card: " + card2);
        }
        
    }


    
}
