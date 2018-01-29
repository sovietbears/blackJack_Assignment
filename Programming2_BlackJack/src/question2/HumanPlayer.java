
package question2;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import question1.Card;
import question1.Hand;

/**
 * This class will allow a human player to interact with the blackjackTable class
 * Extends BasicPlayer class
 * @author Daniel Carey
 */
public class HumanPlayer extends BasicPlayer implements Serializable {
    
    private int balance;
    private Hand playerHand;
    private int bet;
    private List<Card> dealerHand;
    private Card dealerCard;
    private boolean newDeck;
    private boolean isBlackjack;
    
    
    
    public HumanPlayer(){
        this.playerHand = new Hand();
        this.balance = 200;
    }
    
    @Override
    public int makeBet(){
        System.out.println("Please place a bet. Be warned maximum is 500.");
        Scanner sc = new Scanner(System.in);
        int userBet = sc.nextInt();
        return userBet;
    }
    
    public boolean hit(){
        System.out.println("Would you like another card?\n"
                + "1. Yes\n"
                + "2. No\n");
        Scanner sc = new Scanner(System.in);
        int userHit = sc.nextInt();
        boolean errorCheck = true;
        while(errorCheck){
            switch(userHit){
                case 1:
                    errorCheck = false;
                    return true;
                case 2:
                    errorCheck = false;
                    return false;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        }
        return false; //Unreachable 
    }
    
    @Override
    public boolean settleBet(int p) {    
        switch(p){
            case -1:
                balance -= bet;
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
        return this.balance >= 1; //Unreachable, uet needed.
    }
    
    
}
