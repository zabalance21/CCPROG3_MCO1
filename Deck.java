/**
 * Represents a deck that can hold up to 10 unique cards
 * No duplicate card names are allowed in a deck
 *
 * @author Student Names
 * @version 1.0
 */
public class Deck {
    private String name;
    private Card[] cards;
    private int cardCount;
    private static final int MAX_CAPACITY = 10;

    /**
     * Constructor for Deck
     *
     * @param name the name of the deck
     */
    public Deck(String name) {
        this.name = name;
        this.cards = new Card[MAX_CAPACITY];
        this.cardCount = 0;
    }

    /**
     * Gets the name of the deck
     *
     * @return the deck name
     */
    public String getName() {
        return name;
    }

    /**
     * Displays all cards in the deck sorted alphabetically
     */
    public void displayCards() {
        if (cardCount == 0) {
            System.out.println("Deck '" + name + "' is empty.");
            return;
        }

        // Create array of card names for sorting
        String[] names = new String[cardCount];
        for (int i = 0; i < cardCount; i++) {
            names[i] = cards[i].getName();
        }

        // Simple bubble sort
        for (int i = 0; i < cardCount - 1; i++) {
            for (int j = 0; j < cardCount - 1 - i; j++) {
                if (names[j].compareToIgnoreCase(names[j + 1]) > 0) {
                    String temp = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = temp;
                }
            }
        }

        System.out.println("Cards in deck '" + name + "' (" + cardCount + "/" + MAX_CAPACITY + "):");
        for (int i = 0; i < cardCount; i++) {
            System.out.println((i + 1) + ". " + names[i]);
        }
    }

    /**
     * Adds a card to the deck if it doesn't already contain a card with the same name
     *
     * @param card the card to add
     * @return true if card was added successfully, false if deck is full or card name already exists
     */
    public boolean addCard(Card card) {
        if (cardCount >= MAX_CAPACITY) {
            return false;
        }

        // Check for duplicate names
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().equals(card.getName())) {
                return false; // Duplicate name not allowed
            }
        }

        cards[cardCount] = card;
        cardCount++;
        return true;
    }

    /**
     * Removes a card from the deck by name
     *
     * @param name the name of the card to remove
     * @return the removed card, or null if not found
     */
    public Card removeCard(String name) {
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().equals(name)) {
                Card removedCard = cards[i];

                // Shift remaining cards
                for (int j = i; j < cardCount - 1; j++) {
                    cards[j] = cards[j + 1];
                }
                cards[cardCount - 1] = null;
                cardCount--;

                return removedCard;
            }
        }
        return null;
    }

    /**
     * Gets a card from the deck by name
     *
     * @param name the name of the card
     * @return the card if found, null otherwise
     */
    public Card getCard(String name) {
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().equals(name)) {
                return cards[i];
            }
        }
        return null;
    }

    /**
     * Gets all cards in the deck
     *
     * @return array of all cards in the deck
     */
    public Card[] getAllCards() {
        Card[] allCards = new Card[cardCount];
        for (int i = 0; i < cardCount; i++) {
            allCards[i] = cards[i];
        }
        return allCards;
    }

    /**
     * Checks if the deck contains a card with the given name
     *
     * @param name the name of the card to check
     * @return true if deck contains card with this name, false otherwise
     */
    public boolean hasCard(String name) {
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the deck is full
     *
     * @return true if deck is full, false otherwise
     */
    public boolean isFull() {
        return cardCount >= MAX_CAPACITY;
    }

    /**
     * Checks if the deck is empty
     *
     * @return true if deck is empty, false otherwise
     */
    public boolean isEmpty() {
        return cardCount == 0;
    }

    /**
     * Gets the current number of cards in the deck
     *
     * @return the number of cards
     */
    public int getCardCount() {
        return cardCount;
    }
}