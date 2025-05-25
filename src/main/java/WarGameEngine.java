import java.util.*;
import java.util.stream.Collectors;

public class WarGameEngine {
    private static final int MAX_ROUNDS = 10000;

    private final Deque<Card> player1 = new ArrayDeque<>();
    private final Deque<Card> player2 = new ArrayDeque<>();
    private final Map<Integer, String> seenStates = new HashMap<>();
    private final List<Card> cards;

    public WarGameEngine(List<Card> cards) {
        this.cards = cards;
        initPlayerDecks();
    }

    public void playGame() {
        int rounds = 0;
        String winner = "";

        while (!player1.isEmpty() && !player2.isEmpty() && rounds < MAX_ROUNDS) {
            String stateStr = summarizeState();
            int hash = stateStr.hashCode();

            if (seenStates.containsKey(hash)) {
                String state = seenStates.get(hash);
                if (state.equals(stateStr)) {
                    System.out.println("Stalemate due to repeating state: \n" + state);
                    return;
                }
            } else {
                seenStates.put(hash, stateStr);
            }

            winner = playRound();
            rounds++;
        }

        printResult(winner, rounds);
    }

    private void initPlayerDecks() {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                player1.add(cards.get(i));
            } else {
                player2.add(cards.get(i));
            }
        }
    }

    private String playRound() {
        Card p1Card = player1.pollFirst();
        Card p2Card = player2.pollFirst();
        List<Card> cardsInBattle = new ArrayList<>(List.of(p1Card, p2Card));

        if (p1Card.numericValue > p2Card.numericValue) {
            player1.addAll(cardsInBattle);
            return "player1";
        } else if (p1Card.numericValue < p2Card.numericValue) {
            player2.addAll(cardsInBattle);
            return "player2";
        } else {
            return resolveWar(cardsInBattle);
        }
    }

    private String resolveWar(List<Card> cardsInBattle) {
        while (true) {
            if (player1.size() <= 1) return "player2";
            if (player2.size() <= 1) return "player1";

            cardsInBattle.add(player1.pollFirst()); // face down
            cardsInBattle.add(player2.pollFirst());

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
        }
    }

    private String summarizeState() {
        return "Player 1: " + serializeDeck(player1) + "\n" + "Player 2: " + serializeDeck(player2);
    }

    private String serializeDeck(Deque<Card> deck) {
        return deck.stream()
                .map(c -> Integer.toString(c.numericValue))
                .collect(Collectors.joining(","));
    }

    private void printResult(String winner, int rounds) {
        if (winner.isEmpty() && rounds >= MAX_ROUNDS) {
            System.out.println("Stalemate: Exceeded maximum number of rounds.");
        } else {
            System.out.println("Winner: " + (winner.isEmpty() ? "undecided" : winner));
        }
        System.out.println("Rounds played: " + rounds);
        System.out.println("Final state:");
        System.out.println("Player 1: " + serializeDeck(player1));
        System.out.println("Player 2: " + serializeDeck(player2));
    }
}
