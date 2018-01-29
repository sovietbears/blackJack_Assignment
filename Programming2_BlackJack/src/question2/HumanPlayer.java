
package question2;


import java.io.Serializable;
import java.util.Scanner;


/**
 * This class will allow a human player to interact with the blackjackTable class
 * Extends BasicPlayer class
 * @author Daniel Carey
 */
public class HumanPlayer extends BasicPlayer implements Serializable{
    
    public HumanPlayer(){
        super();
    }
    
    @Override
    public int makeBet(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Your current balance is: " + this.getBalance());
        System.out.println("Please place a bet. Be warned maximum is 500."
                + " Minimum is 1.");
        int userBet = sc.nextInt();
        while(true){
            if(this.getBalance() < userBet){
                System.out.println("Your bet: " + userBet + " is greater than "
                        + "your current balance. Please try again.");
                userBet = sc.nextInt();
            }
            else if(userBet > 500 || userBet < 0){
                System.out.println("Please enter a bet between 1 to 500.");
                userBet = sc.nextInt();
            }
            else{
                this.setBet(userBet);
                return userBet;
            }
            
        }
    }
    
    @Override
    public boolean hit(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dealer's first card: " + this.dealerCard.toString());
        System.out.println("Your current hand: " + this.getHand().toString());
        System.out.println("Would you like another card?\n"
                + "1. Yes\n"
                + "2. No\n");
        int userHit = sc.nextInt();
        while(true){
            switch (userHit) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
            userHit = sc.nextInt();
        }
    }
    
}
