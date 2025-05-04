import org.springframework.data.jpa.domain.AbstractAuditable_;

import java.io.IOException;
import java.util.*;

public class WarGame {
    public static void main(String[] args) throws IOException {
        WarGame warGame = new WarGame();
        List<Card> cards = JsonDataLoader.load("CardDeck.json");
        Collections.shuffle(cards);

        Deque<Card> player1 = new ArrayDeque<>();
        Deque<Card> player2 = new ArrayDeque<>();

        warGame.initPlayerDecks(cards, player1, player2);

        String winner = "";
        while (!player1.isEmpty() && !player2.isEmpty()) {
            winner = warGame.play(player1, player2);
        }
        if (winner.isEmpty()) {
            winner = player1.size() > player2.size() ? "player1" : "player2";
        }
        System.out.println("winner: " + winner);
    }

    private String play(Deque<Card> player1, Deque<Card> player2) {
        Card p1Card = player1.pollFirst();
        Card p2Card = player2.pollFirst();
        List<Card> cardsInBattle = new ArrayList<>();

        if (p1Card.numericValue > p2Card.numericValue) {
            cardsInBattle.add(p1Card);
            cardsInBattle.add(p2Card);
            player1.addAll(cardsInBattle); // winner gets cards
            return "player1";
        } else if (p1Card.numericValue < p2Card.numericValue) {
            cardsInBattle.add(p1Card);
            cardsInBattle.add(p2Card);
            player2.addAll(cardsInBattle); // winner gets cards
            return "player2";
        } else {
            // place one face down, and one face up (for followup battle)
            while (p1Card.numericValue == p2Card.numericValue) {
                cardsInBattle.add(p1Card);
                cardsInBattle.add(p2Card);

                // minimum 2 cards will be polled
                if (player1.size() > 1 && player2.size() > 1) {
                    // add face down card for each player
                    cardsInBattle.add(player1.pollFirst());
                    cardsInBattle.add(player2.pollFirst());

                    // next battle cards
                    p1Card = player1.pollFirst();
                    p2Card = player2.pollFirst();
                    if (p1Card.numericValue != p2Card.numericValue) {
                        cardsInBattle.add(p1Card);
                        cardsInBattle.add(p2Card);
                        if (p1Card.numericValue > p2Card.numericValue) {
                            player1.addAll(cardsInBattle);
                        } else {
                            player2.addAll(cardsInBattle);
                        }
                    }
                } else if (player1.size() > 1) { // case if player runs out of cards
                    return "player1";
                } else {
                    return "player2";
                }
            }
        }
        return "";
    }

    private void initPlayerDecks(List<Card> cards, Deque<Card> player1, Deque<Card> player2) {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                player1.add(cards.get(i));
            } else {
                player2.add(cards.get(i));
            }
        }
    }
}
