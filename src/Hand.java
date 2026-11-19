import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card can not be null");
        }
        cards.add(card);
    }

    public int getValue() {
        int total = 0;
        int aces = 0;

        for(Card card : cards) {
            total += card.getValue();

            if (card.getRank() == Card.Rank.Ace) {
                aces++;
            }
        }

        //Gestionating Ace value which can be both 1 & 11
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean Bust() {
        return getValue() > 21;
    }
    //Blackjack from first
    public boolean Blackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    public boolean hasTwentyOne() {
        return getValue() == 21;
    }

    public int getCardCount() {
        return cards.size();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public void clear() {
        cards.clear();
    }

    @Override 
    public String toString() {
        return cards.toString() + " Total: " + getValue();
    }

}