import java.util.Scanner;

public class DeckManager {
    private Scanner scanner;

    public DeckManager(Scanner sc){
        this.scanner = sc;
    }
    /*
     * Manages existing decks
     */
    public void manageDecks(TradingCardInventorySystem tcis) {
        boolean inDeckMenu = true;

        while (inDeckMenu) {
            System.out.println("\n=== Manage Decks ===");
            System.out.println("1. Create a new Deck");
            System.out.println("2. Delete a Deck");
            System.out.println("3. Add Card to Deck");
            System.out.println("4. Remove Card from Deck");
            System.out.println("5. View Deck");
            System.out.println("0. Go back to Main Menu");

            int choice = tcis.getIntInput("Enter your choice: ");

            if (choice == 1) {
                createDeck(tcis);
            } else if (choice == 2) {
                deleteDeck(tcis);
            } else if (choice == 3) {
                addCardToDeck(tcis);
            } else if (choice == 4) {
                removeCardFromDeck(tcis);
            } else if (choice == 5) {
                viewDeck(tcis);
            } else if (choice == 0) {
                inDeckMenu = false;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Creates a new deck
     */
    public void createDeck(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Create New Deck ===");
        Scanner scanner = new Scanner(System.in);
        Deck[] decks = tcis.getDecks();
        int deckCount = tcis.getDeckCount();

        System.out.print("Enter deck name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Deck name cannot be empty.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Check if deck name already exists
        for (int i = 0; i < deckCount; i++) {
            if (decks[i].getName().equals(name)) {
                System.out.println("Deck with this name already exists.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return;
            }
        }

        decks[deckCount] = new Deck(name);
        tcis.addDeckCount(1);
        System.out.println("Deck created successfully!");
    }

    /**
     * Deletes a deck and returns cards to collection
     */
    public void deleteDeck(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Delete Deck ===");
        Scanner scanner = new Scanner(System.in);
        Deck[] decks = tcis.getDecks();
        int deckCount = tcis.getDeckCount();

        if (deckCount == 0) {
            System.out.println("No decks to delete.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayDecks(tcis);

        System.out.print("Enter deck name to delete: ");
        String name = scanner.nextLine().trim();

        for (int i = 0; i < deckCount; i++) {
            if (decks[i].getName().equals(name)) {
                Card[] deckCards = decks[i].getAllCards();
                for (int j = 0; j < deckCards.length; j++) {
                    if (deckCards[j] != null) {
                        tcis.getCollection().addCard(deckCards[j]);
                    }
                }

                for (int j = i; j < deckCount - 1; j++) {
                    decks[j] = decks[j + 1];
                }
                decks[deckCount - 1] = null;
                tcis.addDeckCount(-1);

                System.out.println("Deck deleted successfully! All cards returned to collection.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return;
            }
        }

        System.out.println("Deck not found.");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Displays all decks
     */
    public void displayDecks(TradingCardInventorySystem tcis) {
        Deck[] decks = tcis.getDecks();
        int deckCount = tcis.getDeckCount();
        System.out.println("Available Decks:");
        for (int i = 0; i < deckCount; i++) {
            System.out.println("- " + decks[i].getName());
        }
    }

    /**
     * Adds a card to a deck
     */
    public void addCardToDeck(TradingCardInventorySystem tcis) {
        Deck[] decks = tcis.getDecks();
        Scanner scanner = new Scanner(System.in);
        int deckCount = tcis.getDeckCount();
        System.out.println("\n=== Add Card to Deck ===");

        if (deckCount == 0) {
            System.out.println("No decks available.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (!tcis.getCollection().hasAvailableCards()) {
            System.out.println("No available cards in collection.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayDecks(tcis);

        System.out.print("Enter deck name: ");
        String deckName = scanner.nextLine().trim();

        Deck selectedDeck = null;
        int found = 0;
        for (int i = 0; i < deckCount && found == 0; i++) {
            if (decks[i].getName().equals(deckName)) {
                selectedDeck = decks[i];
                found = 1;
            }
        }

        if (selectedDeck == null) {
            System.out.println("Deck not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (selectedDeck.isFull()) {
            System.out.println("Deck is full. Cannot add more cards.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        tcis.getCollection().displayAvailableCards();

        System.out.print("Enter card name to add: ");
        String cardName = scanner.nextLine().trim();

        Card card = tcis.getCollection().getAvailableCard(cardName);
        if (card != null) {
            if (selectedDeck.hasCard(cardName)) {
                System.out.println("Card with this name already exists in deck. Cannot add duplicate.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return;
            }

            selectedDeck.addCard(card);
            tcis.getCollection().removeCard(cardName);
            System.out.println("Card added to deck successfully!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else {
            System.out.println("Card not available in collection.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Removes a card from a deck
     */
    public void removeCardFromDeck(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Remove Card from Deck ===");
        Scanner scanner = new Scanner(System.in);
        Deck[] decks = tcis.getDecks();
        int deckCount = tcis.getDeckCount();

        if (deckCount == 0) {
            System.out.println("No decks available.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayDecks(tcis);

        System.out.print("Enter deck name: ");
        String deckName = scanner.nextLine().trim();

        Deck selectedDeck = null;
        int found = 0;
        for (int i = 0; i < deckCount && found == 0; i++) {
            if (decks[i].getName().equals(deckName)) {
                selectedDeck = decks[i];
                found = 1;
            }
        }

        if (selectedDeck == null) {
            System.out.println("Deck not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (selectedDeck.isEmpty()) {
            System.out.println("Deck is empty.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        selectedDeck.displayCards();

        System.out.print("Enter card name to remove: ");
        String cardName = scanner.nextLine().trim();

        Card card = selectedDeck.removeCard(cardName);
        if (card != null) {
            tcis.getCollection().addCard(card);
            System.out.println("Card removed from deck and returned to collection!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else {
            System.out.println("Card not found in deck.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Views contents of a deck with option to view card details
     */
    public void viewDeck(TradingCardInventorySystem tcis) {
        System.out.println("\n=== View Deck ===");
        Scanner scanner = new Scanner(System.in);
        Deck[] decks = tcis.getDecks();
        int deckCount = tcis.getDeckCount();

        if (deckCount == 0) {
            System.out.println("No decks available.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayDecks(tcis);

        System.out.print("Enter deck name: ");
        String deckName = scanner.nextLine().trim();

        Deck selectedDeck = null;
        int found = 0;
        for (int i = 0; i < deckCount && found == 0; i++) {
            if (decks[i].getName().equals(deckName)) {
                selectedDeck = decks[i];
                found = 1;
            }
        }

        if (selectedDeck == null) {
            System.out.println("Deck not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        selectedDeck.displayCards();

        if (!selectedDeck.isEmpty()) {
            System.out.print("Do you want to view details of a specific card? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                System.out.print("Enter card name: ");
                String cardName = scanner.nextLine().trim();

                Card card = selectedDeck.getCard(cardName);
                if (card != null) {
                    System.out.println(card.getDetailedInfo());
                } else {
                    System.out.println("Card not found in deck.");
                }
            }
        }
    }

}
