import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Card> cards = JsonDataLoader.load("CardDeck.json");
        Collections.shuffle(cards);

//        List<Card> player1 = cards.subList(0, 26);
//        List<Card> player2 = cards.subList(26, cards.size());

        Deque<Card> player1 = new ArrayDeque<>();
        Deque<Card> player2 = new ArrayDeque<>();

        initPlayerDecks(player1, player2);


        System.out.println();
    }

    private void initPlayerDecks(List<Card> cards, Deque<Card> player1, Deque<Card> player2){
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                player1.add(cards.get(i));
            } else {
                player2.add(cards.get(i));
            }
        }
    }
}
