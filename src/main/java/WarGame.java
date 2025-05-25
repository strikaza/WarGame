import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class WarGame {
    public static void main(String[] args) throws IOException {
        List<Card> cards = JsonDataLoader.load("CardDeck.json");
        Collections.shuffle(cards);

        WarGameEngine engine = new WarGameEngine(cards);
        engine.playGame();
    }
}