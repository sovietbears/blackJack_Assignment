package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import question1.Card;

/**
 * This class represents a player who can use simple counting techniques to give
 * himself advantage throughout the game.
 *
 * @author Daniel Carey
 */
public class AdvancedPlayer extends BasicPlayer implements Serializable {

    private int cardCnt = 0;

    public AdvancedPlayer() {
        super();
    }

    @Override
    public int makeBet() {
        if (this.newDeck == true) {
            cardCnt = 0;
            this.newDeck = false;
        }
        if (this.handsDealt != null){
            Iterator<Card> it = this.handsDealt.iterator();
            Card c;
            int val;
            while (it.hasNext()) {
                c = it.next();
                System.out.println("Cards Advanced: " + c.toString());
                val = c.getRank().getValue();
                if (val >= 2 && val <= 6) {
                    cardCnt++;
                } else if (val == 10 || val == 11) {
                    cardCnt--;
                }
            }
            System.out.println("Count is: " + cardCnt);
            if (cardCnt < 0) {
                this.bet = 10;
                return this.bet;
            } else {
                this.bet = 10 * cardCnt;
                return this.bet;
            }
        }
        else{
            this.bet = 10;
            return this.bet;
        }
    }

    @Override
    public boolean hit() {
        int dCardVal = this.dealerCard.getRank().getValue();
        int handTotal = this.getHandTotal();
        System.out.println("DEALER CARD VALUE: " + dCardVal);
        switch (softTotal()) {
            case 0:
                break;
            case 1:
                return true;
            case 2:
                return false;
            default:
                break;
        }

        if (dCardVal >= 7) {
            return handTotal < 17;
        } else if (dCardVal <= 6) {
            return handTotal < 12;
        }

        return false;
    }

    public int softTotal() {

        if (this.playerHand.countRank(Card.Rank.ACE) > 0) {
            ArrayList<Integer> vals = this.playerHand.handCalcArr();
            for (Integer val : vals) {
                if (val <= 8) {
                    return 1;
                } else if (val == 9 || val == 10) {
                    return 2;
                }
            }

        } else {
            return 0;
        }

        return 0;
    }

}
