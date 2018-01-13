package programming2_blackjack;

import java.util.ArrayList;

/**
 * A class that represents the players hand that contains cards 
 * @author Daniel Carey
 */
public class Hand {
    private ArrayList<Card> handArr;
    private ArrayList<Integer> cardVal;
    private ArrayList<Integer> handVal;
    
    public Hand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        this.handArr = hand;
    }
    
    public Hand(ArrayList<Card> anotherHand){
        ArrayList<Card> hand = new ArrayList<Card>();
        this.handArr = hand;
        this.handArr = anotherHand;
//        if (this.handArr.isEmpty()){
//            this.handArr = anotherHand;
//        }
//        else{
//            ArrayList<Card> allHand     = new ArrayList<Card>();
//            allHand.addAll(this.handArr);
//            allHand.addAll(anotherHand);
//        }
    }
    
    public Hand(Hand h){
        ArrayList<Card> hand        = h.handArr;
        ArrayList<Card> hand2       = handArr;
        ArrayList<Card> allHand     = new ArrayList<Card>();
        allHand.addAll(hand);
        allHand.addAll(hand2);

    }
    
    public static void handCalc(Hand h){
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        h.handArr.stream().forEach((card) -> {
            cardVal.add(card.getRank().getValue());
        });
        
        ArrayList<Integer> handVal = new ArrayList<Integer>();
        int sum = 0;
        int aceCnt = 0;
        for (Integer num : handVal) {
            if (num == 11) {
                aceCnt++;
            }
            sum =+ num;
        } 
        handVal.add(sum);
        for (int i = 1; i <= aceCnt; i++) {
            handVal.add(sum - (i * 10));
        }
    }

    @Override
    public String toString() {
        String result = "";
        int cnt = 0;
        for (Card card : handArr) {
            result += card.toString() + ", Card Value: " + cardVal.get(cnt++);
        }
        result += "\n Hand values(s):  ";
        result = handVal.stream().map((_item) -> handVal.toString() + ", ").reduce(result, String::concat);
        return result;
    }
    
    
    
    
    public static void main(String[] args) {
        Deck d = new Deck();
        //d.shuffle();
        System.out.println("Deck: \n" + d.toString() + "Size: " + d.size());
        ArrayList<Card> dealtCards = new ArrayList<Card>();
        dealtCards.add(d.deal());
        dealtCards.add(d.deal());
//        System.out.println(dealtCards.toString());
        
        Hand h1 = new Hand();
        Hand h2 = new Hand(dealtCards); 
        Hand h3 = new Hand();
        System.out.println(h2.toString());
        
        
    }
    
}
