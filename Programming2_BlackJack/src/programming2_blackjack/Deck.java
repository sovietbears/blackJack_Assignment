package programming2_blackjack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import programming2_blackjack.Card.Rank;
import programming2_blackjack.Card.Suit;

/**
 * Class Deck simulates the deck of cards that are needed for card games
 * @author Daniel Carey
 */
public class Deck implements Serializable, Iterable<Card> {
    
    static final long serialVersionUID = 112;
    private final  LinkedList<Card> cardList;
            
    /**
     * Constructor to initialise an empty collection of cards
     */
    public Deck(){ 
        LinkedList<Card> cardList = new LinkedList<Card>();
        this.cardList = cardList;
        
        //for each suit 
        for  (Suit s : Suit.values()){
            //for each rank
            for(Rank r : Rank.values()){
                Card c = new Card(s, r);
                this.cardList.add(c);
                
            }
        }

    }

    @Override
    public String toString() {
        String result = "";
        Iterator<Card> it = this.cardList.iterator();
        while (it.hasNext()) {
            Card card = it.next();
            result += card.toString();         
        }
        return result;
}
    
    
    
    /**
     * Method to shuffle the deck by moving cards to last 
     * element in random order
     */
    public void shuffle(){
        for (int i = 0; i < this.cardList.size(); i++)
        {
            int card = (int)(Math.random()*(52-i));
            this.cardList.addLast(this.cardList.remove(card));
        }
    }
    
    /**
     * This method will return the first Card object from the list
     * then remove it from the LinkedList
     * @return first card from the list
     */
    public Card deal()
    {
       Card card = this.cardList.getFirst();
       this.cardList.removeFirst();
       
       return card;
    }
    
    /**
     * Method that will return the number of cards left in the deck
     * @return number of cards in the deck
     */
    public int size()
    {
        int size = this.cardList.size();
        return size;
    }
    
    /**
     * Method to reset the deck
     */
    public void newDeck()
    {
        this.cardList.removeAll(cardList);
        
            //for each suit 
            for  (Suit s : Suit.values()){
                //for each rank
                for(Rank r : Rank.values()){
                    Card c = new Card(s, r);
                    this.cardList.add(c);
                
            }
        }
    }
    
    //--------------Iterator Commands Start---------------------
    @Override
    public Iterator<Card> iterator() {
        return new Iterator<Card>() {
            private final Iterator<Card> it = cardList.iterator();
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

    
    /**
     * Method to initialize nested Iterator
     * @return Iterator
     */
    public Iterator<Card> iteratorSecondCard()
    {
        return new SecondCardIterator(cardList);
    }

    /**
     * Nested class iterator that will traverse the cards by going to 
     * every other card.
     * @param args 
     */
    private class SecondCardIterator implements Iterator<Card>{
        
        
        private final List<Card> deck; //holds list of cards in the deck
        private int currCard = 0; //Keeps track of the current index of card
        
        //Assigns given list to the deck variable
        public SecondCardIterator(LinkedList<Card> card){
            this.deck = card; 
        }
        

        @Override
        public boolean hasNext() {
            return currCard < deck.size() - 2; //Checks if iteration can go on
        }

        @Override
        public Card next() {
                return deck.get(currCard+=2); //method to return every other card
        }
        
        
    }
    
    
    /** WARNING ADD MORE COMMENTS TO EXPLAIN YOURSELF!!
     * Method that will replace cards in the current Deck with results from 
     * SecondCardIterator method and write the Object's data to ser file
     */
    public void writeSecondIteratorToSer (){
        String filename = "secondItr.ser";
        Iterator<Card> normIt = this.cardList.iterator();
        Iterator<Card> it = this.cardList.descendingIterator();
        Card card = it.next();
        System.out.println("Starting card: " + card);
        while(normIt.hasNext()){
            if (card.equals(normIt.next()) && it.hasNext()) {
               
            }
            else if (!(card.equals(normIt.next())) && it.hasNext()){
                normIt.remove();
            }
            else {
                normIt.remove();
            }
        }
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    


    
    
    

    
    public static void main(String[] args) {
        //Testing Deck constructor
        Deck dTest = new Deck();
        System.out.println("LinkedList representing Deck contains: ");
        System.out.println(dTest.toString());
        
        //Shuffle Testing
        System.out.println("\n\nAfter shuffle.\n\n");
        dTest.shuffle();
        System.out.println(dTest.toString());
        
        //deal method testing
        System.out.println("First card is: " + dTest.cardList.getFirst());
        System.out.println("First Card dealt is: " + dTest.deal());
        System.out.println("Check that if the card was removed: "
                + dTest.cardList.size() + " First card is: " 
                + dTest.cardList.getFirst());
        
        //size method test
        
        
        //newDeck method test
        dTest.newDeck();
        System.out.println(dTest.toString());
        System.out.println("Size method: " + dTest.size());
        
        //Testing custom nested SecodnCardIterator
        System.out.println("\n\nSecondCardIterator test start\n");
        Iterator<Card> iterator = dTest.iteratorSecondCard();
        while(iterator.hasNext()){
            Card card = iterator.next();
            System.out.println("Card: " + card);
        }
        
        //Testing default iterator
        Iterator<Card> iterator2 = dTest.cardList.iterator();
        System.out.println("\n\nNormal Iterator test start\n");
        while (iterator2.hasNext()) {
            Card card = iterator2.next();
            System.out.println("Card: " + card);
        }
        //Testing Serialization of Deck object using SecondCardIterator
        dTest.writeSecondIteratorToSer();
        System.out.println("Ser test: " + dTest.toString() + " Size: "
                + dTest.size());
        try{
            FileInputStream fis = new FileInputStream("secondItr.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            dTest = (Deck)in.readObject();
            in.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        
        }
        System.out.println("Ser test after read in: " + dTest.toString());
    }

}
