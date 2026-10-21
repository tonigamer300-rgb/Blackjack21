import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum State {
        Waiting,
        Player_Turns,
        Round_Over
    }

    private final List<Player> players;
    private final Hand dealerHand;

    private Deck deck;
    private int currentPlayerIndex;
    private State state;

    public Game() {
        players = new ArrayList<>();
        dealerHand = new Hand();

        deck = new Deck();
        currentPlayerIndex = -1;
        state = State.Waiting;
    }

    public void addPlayer(Player player) {
        if (state != State.Waiting) {
            throw new IllegalStateException("Players can only join before first round");
        }

        if (player == nul) {
            throw new IllegalArgumentException("Player can not be null");
        }

        if (players.size() >= 4) {
            throw new IllegalStateException("The game is full");
        }

        //Rethink this to get unique player id
        for (Player existing : players) {
            if (existing.getId().equals(player.getId())) {
                throw new IllegalArgumentException("Player ID allready exists");
            }
        }

        players.add(player);
    }

    public void startRound() {
        if (players.size() < 2 || player.size() > 4) {
            throw new IllegalStateException("The game requires at least 2 players");
        }

        if (state == State.Player_Turns) {
            throw new IllegalStateException("The current round is still running");
        }
    

        deck = new Deck();
        dealerHand.clear();
        currentPlayerIndex = -1;

        for (Player player : players) {
            player.newRound();
        }

        //Dealing cards to all players
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getHand().addCard(deck.drawCard());
            }

            dealerHand.addCard(deck.drawCard());
        }

        state = State.Player_Turns;
        advanceTurn();
    }

    public void hit() {
        Player player = getCurrentPlayer();

        if(player == null || !player.canHit()) {
            throw new IllegalStateException("You can not hit");
        }

        player.getHand().addCard(deck.drawCard());

        //Verifying if it s blackjack or bust
        if(!player.canHit()) {
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

        while(nextIndex < players.size()) {
            Player player = players.get(nextIndex);

            if(player.canHit()) {
                currentPlayerIndex = nextIndex;
                return;
            }

            nextIndex++;
        }

        //All players turn finished
        currentPlayerIndex = -1;
        playDealerTurn();
    }

    private void playDealerTurn() {
        while (dealerHand.getValue() < 17 && !dealerHand.Bust() && !dealerHand.hasTwentyOne()) {
            dealerHand.addCard(deck.drawCard());
        }

        state = State.Round_Over;
    }

    public List<String> getResults() {
        if (state != State.Round_Over) {
            throw new IllegalStateException("The round has not finished");
        }

        List<String> results = new ArrayList<>();

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
        return dealerHand;
    }

    public State getState() {
        return state;
    }

}