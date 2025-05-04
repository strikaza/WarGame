import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@NoArgsConstructor
@AllArgsConstructor
@Data
public class Deck {
    public List<Card> cards;

    public Deck() throws IOException {
        cards = JsonDataLoader.load("CardDeck.json");
        Collections.shuffle(cards);
    }

//    public void splitDeck()
}
