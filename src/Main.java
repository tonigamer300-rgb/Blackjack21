import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game();

        //Implement TEST players
        Player player1 = new Player("1",  "Test player 1");
        Player player2 = new Player("2", "Test player 2");

        game.addPlayer(player1);
        game.addPlayer(player2);

        game.startRound();

        System.out.println("BLACKJACK TEST LOGIC TERMINAL");
        System.out.println();

        while(game.getState() == Game.State.Player_Turns){
            Player currentPlayer = game.getCurrentPlayer();

            if (currentPlayer == null) {
                break;
            }

            System.out.println("--------------------");
            System.out.println(currentPlayer.getName() + "'s turn");
            System.out.println("Your cards: " + currentPlayer.getHand());
            System.out.println("Your total: " + currentPlayer.getHand().getValue());

            System.out.println();
            System.out.println("1. Hit");
            System.out.println("2. Stand");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    game.hit();
                    break;
                
                case "2":
                    game.stand();
                    break;

                default:
                    System.out.println("Invalid choice! Press 1 to HIT or 2 to STAND ");
                    continue;

            }

            System.out.println();

        }

        //Displaying Results
        System.out.println("TEST RUN RESULTS: ");
        System.out.println();
        System.out.println();
        System.out.println("Dealer's cards: " + game.getDealerHand());
        System.out.println("Dealer's total: " + game.getDealerHand().getValue());
        System.out.println();

        for(Player player:game.getPlayers()) {
            System.out.println(player.getName() + ": " + player.getHand());
        }

        System.out.println();

        for(String result:game.getResults()) {
            System.out.println(result);
        }

        System.out.println();

        scanner.close();

    }
    
}
