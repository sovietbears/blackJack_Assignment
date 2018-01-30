package question2;

import java.io.Serializable;
import java.util.Iterator;
import question2.Card;

/**
 * This class represents a player who can use simple counting techniques to give
 * himself advantage throughout the game.
 *
 * @author Daniel Carey
 */
public class AdvancedPlayer extends IntermediatePlayer implements Serializable {

    private int cardCnt;

    public AdvancedPlayer() {
        super();
        this.cardCnt = 0;
    }

    @Override
    public int makeBet() {
        if (this.newDeck == true) {
            cardCnt = 0;
            this.newDeck = false;
        }
        if (this.handsDealt != null && !this.handsDealt.isEmpty()){
            Iterator<Card> it = this.handsDealt.iterator();
            Card c;
            int val;
            while (it.hasNext()) {
                c = it.next();
                val = c.getRank().getValue();
                if (val >= 2 && val <= 6) {
                    ++cardCnt;
                } else if (val == 10 || val == 11) {
                    --cardCnt;
                }
            }
            if (cardCnt <= 0) {
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
}
