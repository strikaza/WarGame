import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class JsonDataLoader {
    public static List<Card> load(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonDataLoader.class.getClassLoader().getResourceAsStream(filepath);
        JsonNode root = mapper.readTree(is);

        List<Card> cards = new ArrayList<>();

        Iterator<JsonNode> iterator = root.elements();
        while (iterator.hasNext()) {
            JsonNode nextNode = iterator.next();
            cards.add(new Card(
                    nextNode.get("suit").textValue(),
                    nextNode.get("value").textValue(),
                    nextNode.get("numericValue").intValue()
            ));
        }

//        cards.sort(Comparator.comparing(Card::getValue));
        return cards;
    }
}
