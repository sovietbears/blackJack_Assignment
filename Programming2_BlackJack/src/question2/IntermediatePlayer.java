package question2;

import java.io.Serializable;
import java.util.ArrayList;
import question2.Card.Rank;

/**
 * Class that represents a slightly better version of basicPlayer who will
 * determine on their play by observing the dealer's first card.
 *
 * @author Daniel Carey
 */
public class IntermediatePlayer extends BasicPlayer implements Serializable {

    public IntermediatePlayer() {
        super();
    }

    @Override
    public boolean hit() {
        int dCardVal = this.dealerCard.getRank().getValue();
        int handTotal = this.getHandTotal();
        if (this.playerHand.countRank(Rank.ACE) > 0 && this.playerHand.getHandSize() <= 2) {
            switch (this.softTotal()) {
                case 0:
                    break;
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    break;
            }
        }

        if (dCardVal >= 7) {
            return handTotal < 17;
        } else if (dCardVal <= 6) {
            return handTotal < 12;
        }

        return false;
    }

    public int softTotal() {

        ArrayList<Integer> vals = this.playerHand.handCalcArr();
        for (Integer val : vals) {
            if (val <= 8) {
                return 1;
            } else if (val == 9 || val == 10) {
                return 2;
            }
        }

        return 0;
    }

}
