# War Game Simulation

This project simulates the classic card game "War" in a deterministic and fully automated fashion—no player interaction is required. The game is implemented in Java and includes logic to detect repeating states (cycles), making it suitable for understanding game loop behavior and infinite recursion risks in state-based systems.

## 🃏 Game Rules

1. A standard shuffled deck of cards is split evenly between two players.
2. Each round:
  - Players reveal the top card of their decks.
  - The higher card wins both cards and places them at the bottom of the winner's deck.
  - If there’s a tie (equal cards), a "war" is initiated:
    - Each player places one card face down, and one card face up.
    - The face-up cards are compared; the winner takes all cards.
    - If the new face-up cards are also tied, the war continues recursively.
3. The game ends when:
  - One player has all the cards.
  - A repeating state (loop) is detected.
  - A maximum number of rounds (10,000) is reached to prevent infinite loops.

## ♻️ Loop Detection

To prevent infinite loops, the simulation uses a combination of:
- **State hashing** using `String.hashCode()` to efficiently track previously seen deck states.
- **State equality check** to ensure no false positives due to hash collisions.
- The game halts with a stalemate message if a repeated state is detected.

## ✅ Features

- Deterministic: No input required once started.
- Loop-safe: Memoization avoids infinite cycles.
- Readable state output to understand game progression and termination.
- Proper handling of edge cases (e.g., insufficient cards during a war).
- Modular design: Game logic split into understandable functions.

## 📋 Sample Output

```
Winner: player2
Final state:
Player 1: 
Player 2: 5,2,14,6,13,4,...
```

## 📁 File Expectations

- `CardDeck.json`: A standard 52-card deck represented in JSON format.
- `Card.java`: Class representing individual cards with numeric value.
- `JsonDataLoader.java`: Utility for loading the deck from JSON.

## 🧪 Testing Ideas

Test scenarios include:
- Normal games with varying shuffle seeds.
- Hand-crafted decks that loop.
- Decks that result in immediate wins.
- Empty deck edge cases and war exhaustion conditions.

## 🚀 Running the Game

Run the main class `WarGame`:

```bash
./gradlew bootRun
```

Or use your IDE's run button on `WarGame.java`.

## 🛠️ Tech Stack

- Java 17+
- Gradle
- JUnit (for optional testing)
- JSON (card deck input)

## 🧠 Notes

- Uses a configurable max-round limit (`MAX_ROUNDS = 10000`) to cap simulation time.
- Hash-based loop detection is optimized for reasonable deck sizes like 52 cards.