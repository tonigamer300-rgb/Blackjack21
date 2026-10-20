

public class Card {

    public enum Suit {
        Hearts, Diamonds, Clubs, Spades
    }

    public enum Rank {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace
    }
    
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        if(suit == null || rank == null) {
            throw new IllegalArgumentException("Suit and rank can not be null");
        }
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        switch (rank) {
            case Jack:
            case Queen:
            case King:
                return 10;
            case Ace:
                return 11;
            default:
                return rank.ordinal() + 2;
        }
    }

    @Override 
    public String toString() {
        return rank + " of " + suit;
    }
}
