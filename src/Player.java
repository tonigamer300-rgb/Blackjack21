public class Player {
    private final String id;
    private final String name;
    private final Hand hand;

    private boolean hasStood;
    private boolean connected;

    public Player(String id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("Player ID can not be empty");
        }

        if (name == null) {
            throw new IllegalArgumentException("Player must select a name");
        }

        this.id = id;
        this.name = name;
        this.hand = new Hand();

        this.hasStood = false;
        this.connected = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean hasStood() {
        return hasStood;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean canHit() {
        return !hasStood
                && !hand.Bust()
                && !hand.hasTwentyOne();
    }

    public void stand() {
        hasStood = true;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void newRound() {
        hand.clear();
        hasStood = false;
    }

    @Override 
    public String toString() {
        return name;
    }
}
