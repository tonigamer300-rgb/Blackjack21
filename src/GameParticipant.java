

public abstract class GameParticipant {
    private final String name;
    private final Hand hand;

    public GameParticipant(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void newRound() {
        hand.clear();
    }
}
