/**
 * Represents a binder that can hold up to 20 cards for trading
 *
 * @author Student Names
 * @version 1.0
 */
public class Binder {
    private String name;
    private Card[] cards;
    private int cardCount;
    private static final int MAX_CAPACITY = 20;

    /**
     * Constructor for Binder
     *
     * @param name the name of the binder
     */
    public Binder(String name) {
        this.name = name;
        this.cards = new Card[MAX_CAPACITY];
        this.cardCount = 0;
    }

    /**
     * Gets the name of the binder
     *
     * @return the binder name
     */
    public String getName() {
        return name;
    }

    /**
     * Displays all cards in the binder alphabetically
     */
    public void displayCards() {
        if (cardCount == 0) {
            System.out.println("Binder '" + name + "' is empty.");
            return;
        }

        String[] names = new String[cardCount];
        for (int i = 0; i < cardCount; i++) {
            names[i] = cards[i].getName();
        }

        // bubble sort
        for (int i = 0; i < cardCount - 1; i++) {
            for (int j = 0; j < cardCount - 1 - i; j++) {
                if (names[j].compareToIgnoreCase(names[j + 1]) > 0) {
                    String temp = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = temp;
                }
            }
        }

        System.out.println("Cards in binder '" + name + "' (" + cardCount + "/" + MAX_CAPACITY + "):");
        for (int i = 0; i < cardCount; i++) {
            System.out.println("- " + names[i]);
        }
    }

    /**
     * Adds a card to the binder
     *
     * @param card the card to add
     * @return true if card was added successfully, false if binder is full
     */
    public boolean addCard(Card card) {
        if (cardCount < MAX_CAPACITY) {
            cards[cardCount] = card;
            cardCount++;
            return true;
        }
        return false;
    }

    /**
     * Removes a card from the binder by name
     *
     * @param name the name of the card to remove
     * @return the removed card, or null if not found
     */
    public Card removeCard(String name) {
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().equals(name)) {
                Card removedCard = cards[i];

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
     * Gets a card from the binder by name
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
     * Gets all cards in the binder
     *
     * @return array of all cards in the binder
     */
    public Card[] getAllCards() {
        Card[] allCards = new Card[cardCount];
        for (int i = 0; i < cardCount; i++) {
            allCards[i] = cards[i];
        }
        return allCards;
    }

    /**
     * Checks if the binder is full
     *
     * @return true if binder is full, false otherwise
     */
    public boolean isFull() {
        return cardCount >= MAX_CAPACITY;
    }

    /**
     * Checks if the binder is empty
     *
     * @return true if binder is empty, false otherwise
     */
    public boolean isEmpty() {
        return cardCount == 0;
    }

    /**
     * Gets the current number of cards in the binder
     *
     * @return the number of cards
     */
    public int getCardCount() {
        return cardCount;
    }
}