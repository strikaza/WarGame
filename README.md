# ♠️ War Card Game Simulator (Java)

This project is a **simulation of the classic card game "War"**, implemented in Java. It demonstrates object-oriented programming, simple data structures, and game logic modeling — all while requiring **no actual player input**. The game is fully deterministic after shuffling the deck, making it ideal for simulation and educational purposes.

---

## 🃏 How the Game Works

- A standard 52-card deck is **shuffled** and **split evenly** between two players.
- Each round, both players draw the **top card** from their decks.
    - The player with the **higher card value** wins the round and collects both cards (added to the bottom of their deck).
    - If the cards are **equal**, a **war** begins.

---

## ⚔️ War Rules

When a tie occurs:
1. Each player places **one card face down** (discarded temporarily).
2. Then each places **one card face up** for comparison.
3. The higher face-up card wins **all cards on the table**.
4. If the face-up cards tie again, the war continues recursively using the same rules.

If a player **doesn’t have enough cards** to continue a war, they **lose** the game.

---

## ✅ Game Characteristics

- 💻 **No user interaction**: The entire game plays out automatically after startup.
- 🎲 **Deterministic** (except initial shuffle): The only randomness is in the initial deck shuffle.
- 🔄 Simulates **every round** until one player wins all cards.
- 🧪 Great for learning simulations, recursion, and state modeling in Java.

---

## 🛠 How to Run

1. Clone or download the project.
2. Ensure you have Java 17+ installed.
3. Run the `main()` method in `WarGame.java` (or `Main.java` if separated).
4. Watch the simulation run and print the winning player.

---

## 📁 Card Deck Format

Cards are loaded from a JSON file (`CardDeck.json`) with the following structure:

```json
{
  "suit": "hearts",
  "value": "K",
  "numericValue": 13
}
```

All cards are assigned a `numericValue` for easy comparison:
- Numbered cards: `2–10`
- Jack: `11`, Queen: `12`, King: `13`, Ace: `14`

---

## 🧠 Educational Value

This simulation is great for:
- Learning Java collections (`Deque`, `List`, `ArrayDeque`)
- Practicing algorithmic thinking
- Modeling game state transitions
- Understanding recursion and termination conditions

---

## 🏁 Sample Output

```text
Battle: 10♠ vs 7♦ → Player 1 wins the round.
Battle: 5♥ vs 5♣ → War!
...
winner: player1
```

---

## 📌 License

MIT or similar — you can modify, extend, or use this simulation in your own learning projects.
