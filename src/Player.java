public class Player extends GameParticipant {
    private final String id;
    private boolean hasStood;
    private boolean connected;

    public Player(String id, String name) {
        super(name);

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player ID cannot be empty.");
        }

        this.id = id;
        this.hasStood = false;
        this.connected = true;
    }

    public String getId() {
        return id;
    }

    public boolean hasStood() {
        return hasStood;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean canHit() {
        return !hasStood
                && !getHand().Bust()
                && !getHand().hasTwentyOne();
    }

    public void stand() {
        hasStood = true;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public void newRound() {
        super.newRound();
        hasStood = false;
    }

    @Override
    public String toString() {
        return getName();
    }
}
