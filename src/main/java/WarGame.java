import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;

public class WarGame {

    private static final int MAX_ROUNDS = 10000;

    public static void main(String[] args) throws IOException {
        WarGame warGame = new WarGame();
        List<Card> cards = JsonDataLoader.load("CardDeck.json");
        Collections.shuffle(cards);

        Deque<Card> player1 = new ArrayDeque<>();
        Deque<Card> player2 = new ArrayDeque<>();

        warGame.initPlayerDecks(cards, player1, player2);

        Map<Integer, String> seenStates = new HashMap<>();
        int rounds = 0;
        String winner = "";

        while (!player1.isEmpty() && !player2.isEmpty() && rounds < MAX_ROUNDS) {
            String stateStr = summarizeState(player1, player2);
            int hash = stateStr.hashCode();

            if (seenStates.containsKey(hash)) {
                String state = seenStates.get(hash);
                if (state.equals(stateStr)) {
                    System.out.println("Stalemate detected due to repeating state: \n" + state);
                    return;
                }
            } else {
                seenStates.put(hash, stateStr);
            }

            winner = warGame.playRound(player1, player2);
            rounds++;
        }

        warGame.printResult(winner, rounds, player1, player2);
    }

    private static String summarizeState(Deque<Card> player1, Deque<Card> player2) {
        return "Player 1: " + serializeDeck(player1) + "\n" + "Player 2: " + serializeDeck(player2);
    }

    private static String serializeDeck(Deque<Card> deck) {
        return deck.stream()
                .map(c -> Integer.toString(c.numericValue))
                .collect(Collectors.joining(","));
    }

    public String playRound(Deque<Card> player1, Deque<Card> player2) {
        Card p1Card = player1.pollFirst();
        Card p2Card = player2.pollFirst();
        List<Card> cardsInBattle = new ArrayList<>();

        cardsInBattle.add(p1Card);
        cardsInBattle.add(p2Card);

        if (p1Card.numericValue > p2Card.numericValue) {
            player1.addAll(cardsInBattle);
            return "player1";
        } else if (p1Card.numericValue < p2Card.numericValue) {
            player2.addAll(cardsInBattle);
            return "player2";
        } else {
            return resolveWar(player1, player2, cardsInBattle);
        }
    }

    private String resolveWar(Deque<Card> player1, Deque<Card> player2, List<Card> cardsInBattle) {
        while (true) {
            if (player1.size() <= 1) return "player2";
            if (player2.size() <= 1) return "player1";

            cardsInBattle.add(player1.pollFirst()); // face down
            cardsInBattle.add(player2.pollFirst()); // face down

            Card p1Card = player1.pollFirst();
            Card p2Card = player2.pollFirst();

            cardsInBattle.add(p1Card);
            cardsInBattle.add(p2Card);

            if (p1Card.numericValue > p2Card.numericValue) {
                player1.addAll(cardsInBattle);
                return "player1";
            } else if (p1Card.numericValue < p2Card.numericValue) {
                player2.addAll(cardsInBattle);
                return "player2";
            }
            // else continue loop for another war round
        }
    }

    public void initPlayerDecks(List<Card> cards, Deque<Card> player1, Deque<Card> player2) {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                player1.add(cards.get(i));
            } else {
                player2.add(cards.get(i));
            }
        }
    }

    private void printResult(String winner, int rounds, Deque<Card> player1, Deque<Card> player2) {
        if (winner.isEmpty()) {
            if (rounds >= MAX_ROUNDS) {
                System.out.println("Stalemate: Exceeded maximum number of rounds.");
            } else {
                winner = player1.size() > player2.size() ? "player1" : "player2";
            }
        }
        System.out.println("Winner: " + winner);
        System.out.println("Final state:");
        System.out.println("Player 1: " + serializeDeck(player1));
        System.out.println("Player 2: " + serializeDeck(player2));
    }
}
