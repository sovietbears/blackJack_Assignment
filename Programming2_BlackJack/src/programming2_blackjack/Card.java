package programming2_blackjack;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Daniel Carey This class will hold the template for the card that will
 * be used in the game of Blackjack. Consists of enums to represent a card. As
 * well as getters, compareTo, toString, sum, isBlackjack and main methods
 */
public class Card implements Serializable, Comparable<Card> {

    static final long serialVersionUID = 101L;

    /**
     * enum that contains fields for suit of the card
     */
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    /**
     *enum that contains fields for the rank of the card. In addition contains
     * values of the cards and a method that allows to retrieve the value of the
     * previous enum field in the list.
     */
    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
        final int value; // value of the Rank

        Rank(int value) {
            this.value = value;
        }
        
        /**
         * Method to get the value in integer form of the Rank
         * @return the int value of rank
         */
        public int getValue(){
            return this.value;
        }

        /**
         * @param r is the Rank of the card This method will retrieve the
         * previous value in the list of Rank enum
         * @return rank's previous value
         */
        public static Rank prevRank(Rank r) {
            switch (r) {
                case TWO:
                    return Rank.ACE;
                case THREE:
                    return Rank.TWO;
                case FOUR:
                    return Rank.THREE;
                case FIVE:
                    return Rank.FOUR;
                case SIX:
                    return Rank.FIVE;
                case SEVEN:
                    return Rank.SIX;
                case EIGHT:
                    return Rank.SEVEN;
                case NINE:
                    return Rank.EIGHT;
                case TEN:
                    return Rank.NINE;
                case JACK:
                    return Rank.TEN;
                case QUEEN:
                    return Rank.JACK;
                case KING:
                    return Rank.QUEEN;
                case ACE:
                    return Rank.KING;
            }
            return Rank.TWO; //Unreachable, yet required.
        }
    }
    
    //initialize variables
    private Suit suit;
    private Rank rank;
    
    
    //Class contructor
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * ***********WIP***********************
     * *****TESTING NEEDED******************
     * @param other another object that you want to compare
     * against current object
     * Custom comparator method that will first compare by rank then by suit
     * @return 
     */
    @Override
    public int compareTo(Card other){
//        if (other.rank.ordinal() < this.rank.ordinal()) {
//            return -1;
//        }
//        else if (other.rank.ordinal() > this.rank.ordinal()){
//            return 1;
//        }
          int i = Integer.compare(other.rank.ordinal(), this.rank.ordinal());
        if (i != 0){
            return i;
        }
        return Integer.compare(other.suit.ordinal(), this.suit.ordinal());
        
    }
    
    
    //*********************TESTING NEEDED***********************
    //custom toString method
    @Override
    public String toString() {
        return "Suit: " + suit + "\nRank: " + rank + "\n";
    }
    
    /** 
     * ********************TESTING NEEDED***********************
     * @return the suit of the card 
     */
    public Suit getSuit(){
        return this.suit;
    }
    
    /**
     * *******************TESTING NEEDED*************************
     * @return the rank of the card
     */
    public Rank getRank(){
        return this.rank;
    }
    
    /**
     * ****************TESTING NEEDED*****************************
     * Method to calculate the sum of two cards
     * @param a first card object
     * @param b second card object
     * @return the sum of two cards that we passed in
     */
    public static int sum(Card a, Card b){
        return a.rank.getValue() + b.rank.getValue();
    }
    
    /**
     * *****************TESTING NEEDED*****************************
     * This method determines if the two cards fulfil the blackjack combo,
     * this is done using the sum() method.
     * @param a first card object
     * @param b second card object
     * @return returns true if the two cards have an Ace as one card
     * and card with a value of 10 as the other
     */
    public static boolean isBlackjack(Card a, Card b){
        return sum(a,b) == 21;
    }
    
    
    /**
     * ***********************WIP*********************************
     * Nested static comparator class that will be used to sort cards into 
     * descending order by rank
     */
//    public static class compareAscending implements Comparator{
//        public int compare(Card a, Card b){
//            return (b.rank.compareTo(b.rank));
//        }
//    }
    /**
     * **********************WIP***********************************
     * Nested static comparator class that will be used to sort cards into
     * ascending order of suit then rank
     */
//    public static class compareSuit implements Comparator{
//        public int compare(Object a, Object b){
//            
//        }
//    }

    /**
     * main method of the class used for testing
     */
    public static void main(String[] args) {
        Suit suitTest = Suit.DIAMONDS;
        Rank rankTest = Rank.TEN;

        System.out.println(" Coolest suit is: " + suitTest);
        System.out.println(" Favorite rank is: " + rankTest);
        System.out.println(" All Possible suits are: ");
        for (Suit s : Suit.values()) {
            System.out.println(s);
        }
        System.out.println(" All possible ranks are: ");
        for (Rank r : Rank.values()) {
            System.out.println(r);
        }
        System.out.println("Previous value of: " + rankTest + " is: " +
               Rank.prevRank(rankTest));
        System.out.println("My rank is: " + rankTest + " and it's valie is: " +
                rankTest.getValue());
        
        System.out.println("Ordinal position of suit: " + suitTest +
                " is: " + suitTest.ordinal());
        
        

    }

}
