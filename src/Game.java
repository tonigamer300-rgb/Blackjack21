
import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum State {
        Waiting,
        Player_Turns,
        Round_Over
    }

    private final List<Player> players;
    private final Dealer dealer;

    private Deck deck;
    private int currentPlayerIndex;
    private State state;

    public Game() {
        players = new ArrayList<>();
        dealer = new Dealer();

        deck = new Deck();
        currentPlayerIndex = -1;
        state = State.Waiting;
    }

    public void addPlayer(Player player) {
        if (state != State.Waiting) {
            throw new IllegalStateException("Players can only join before first round");
        }

        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (players.size() >= 4) {
            throw new IllegalStateException("The game is full");
        }

        for (Player existing : players) {
            if (existing.getId().equals(player.getId())) {
                throw new IllegalArgumentException("Player ID already exists");
            }
        }

        players.add(player);
    }

    public void startRound() {
        if (players.size() < 2 || players.size() > 4) {
            throw new IllegalStateException("The game requires at least 2 players");
        }

        if (state == State.Player_Turns) {
            throw new IllegalStateException("The current round is still running");
        }

        deck = new Deck();
        dealer.newRound();
        currentPlayerIndex = -1;

        for (Player player : players) {
            player.newRound();
        }

        // Deal two cards to each player and the dealer
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getHand().addCard(deck.drawCard());
            }

            dealer.getHand().addCard(deck.drawCard());
        }

        state = State.Player_Turns;
        advanceTurn();
    }

    public void hit() {
        Player player = getCurrentPlayer();

        if (player == null || !player.canHit()) {
            throw new IllegalStateException("You cannot hit");
        }

        player.getHand().addCard(deck.drawCard());

        if (!player.canHit()) {
            advanceTurn();
        }
    }

    public void stand() {
        Player player = getCurrentPlayer();

        if (player == null) {
            throw new IllegalStateException("No active player");
        }

        player.stand();
        advanceTurn();
    }

    private void advanceTurn() {
        int nextIndex = currentPlayerIndex + 1;

        while (nextIndex < players.size()) {
            Player player = players.get(nextIndex);

            if (player.canHit()) {
                currentPlayerIndex = nextIndex;
                return;
            }

            nextIndex++;
        }

        // All players have finished their turns
        currentPlayerIndex = -1;
        playDealerTurn();
    }

    private void playDealerTurn() {
        while (dealer.getHand().getValue() < 17
                && !dealer.getHand().Bust()
                && !dealer.getHand().hasTwentyOne()) {

            dealer.getHand().addCard(deck.drawCard());
        }

        state = State.Round_Over;
    }

    public List<String> getResults() {
        if (state != State.Round_Over) {
            throw new IllegalStateException("The round has not finished");
        }

        List<String> results = new ArrayList<>();

        Hand dealerHand = dealer.getHand();

        for (Player player : players) {
            Hand playerHand = player.getHand();

            if (playerHand.Bust()) {
                results.add(player.getName() + ": Bust - Lose");
            } else if (playerHand.Blackjack() && dealerHand.Blackjack()) {
                results.add(player.getName() + ": Tie");
            } else if (playerHand.Blackjack()) {
                results.add(player.getName() + ": Blackjack - Win");
            } else if (dealerHand.Blackjack()) {
                results.add(player.getName() + ": Lose");
            } else if (dealerHand.Bust()) {
                results.add(player.getName() + ": Win");
            } else if (playerHand.getValue() > dealerHand.getValue()) {
                results.add(player.getName() + ": Win");
            } else if (playerHand.getValue() < dealerHand.getValue()) {
                results.add(player.getName() + ": Lose");
            } else {
                results.add(player.getName() + ": Tie");
            }
        }

        return results;
    }

    public Player getCurrentPlayer() {
        if (state != State.Player_Turns || currentPlayerIndex < 0) {
            return null;
        }

        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Hand getDealerHand() {
        return dealer.getHand();
    }

    public Dealer getDealer() {
        return dealer;
    }

    public State getState() {
        return state;
    }
}
