import java.util.Scanner;

/**
 * Main class for the Trading Card Inventory System (TCIS)
 * Handles the command line interface and main program flow
 *
 * @author Theodore Garcia
 * @version 1.0
 */
public class TradingCardInventorySystem {
    private Collection collection;
    private Binder[] binders;
    private Deck[] decks;
    private int binderCount;
    private int deckCount;
    private Scanner scanner;

    /**
     * Constructor for TradingCardInventorySystem
     * Initializes the collection, arrays for binders and decks, and scanner
     */
    public TradingCardInventorySystem() {
        this.collection = new Collection();
        this.binders = new Binder[50];
        this.decks = new Deck[50];
        this.binderCount = 0;
        this.deckCount = 0;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu with available options
     */
    public void displayMainMenu() {
        System.out.println("\n=== Trading Card Inventory System ===");
        System.out.println("1. Add a Card");

        if (collection.hasCards()) {
            System.out.println("2. Increase/Decrease Card Count");
            System.out.println("3. Display a Card");
            System.out.println("4. Display Collection");
        }

        if (binderCount == 0) {
            System.out.println("5. Create a new Binder");
        } else {
            System.out.println("5. Manage Binders");
        }

        if (deckCount == 0) {
            System.out.println("6. Create a new Deck");
        } else {
            System.out.println("6. Manage Decks");
        }

        System.out.println("0. Exit");
    }

    public boolean hasCardsInCollection() {
        return collection.hasCards();
    }
    /**
     * Adds a new card to the collection via manual input
     */
    public Card addCard(char ver) {
        System.out.print("Enter card name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Card name cannot be empty.");
            return null;
        }

        // for normal adding of cards
        if (collection.cardExists(name) && ver == 'C') {
            System.out.println("Card '" + name + "' already exists in the collection.");
            System.out.print("Do you want to increase the count instead? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                collection.increaseCardCount(name);
                System.out.println("Card count increased successfully!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
            return null;
            // for trade adding of cards
        } else if (collection.cardExists(name) && ver == 'T') {
            System.out.println("Card '" + name + "' already exists in the collection.");
            return collection.getAvailableCard(name);
        }

        String rarity;
        System.out.println("Select rarity:");
        System.out.println("1. Common");
        System.out.println("2. Uncommon");
        System.out.println("3. Rare");
        System.out.println("4. Legendary");

        int choiceRar = getIntInput("Enter choice (1-4): ");

        if (choiceRar == 1) {
            rarity = Card.Rarity.COMMON;
        } else if (choiceRar == 2) {
            rarity = Card.Rarity.UNCOMMON;
        } else if (choiceRar == 3) {
            rarity = Card.Rarity.RARE;
        } else if (choiceRar == 4) {
            rarity = Card.Rarity.LEGENDARY;
        } else {
            rarity = null;
        }

        if (rarity == null) {
            System.out.println("Invalid rarity. Card not added.");
            return null;
        }

        String variant;
        if (rarity.equals(Card.Rarity.COMMON) || rarity.equals(Card.Rarity.UNCOMMON)) {
            variant = Card.Variant.NORMAL;
        } else {
            System.out.println("Select variant:");
            System.out.println("1. Normal");
            System.out.println("2. Extended-art");
            System.out.println("3. Full-art");
            System.out.println("4. Alt-art");

            int choiceVar = getIntInput("Enter choice (1-4): ");

            if (choiceVar == 1) {
                variant = Card.Variant.NORMAL;
            } else if (choiceVar == 2) {
                variant = Card.Variant.EXTENDED_ART;
            } else if (choiceVar == 3) {
                variant = Card.Variant.FULL_ART;
            } else if (choiceVar == 4) {
                variant = Card.Variant.ALT_ART;
            } else {
                variant = null;
            }

            if (variant == null) {
                System.out.println("Invalid variant. Card not added.");
                return null;
            }
        }

        double value = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter card value: $");
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        if (value < 0) {
            System.out.println("Card value cannot be negative. Card not added.");
            return null;
        }

        Card card = new Card(name, rarity, variant, value);

        return card;
    }

    /**
     * Manages increasing or decreasing card count
     */
    public void manageCardCount() {
        System.out.println("\n=== Manage Card Count ===");

        collection.displayCollection();

        System.out.print("Enter card name: ");
        String name = scanner.nextLine().trim();

        if (!collection.cardExists(name)) {
            System.out.println("Card not found in collection.");
            return;
        }

        System.out.println("1. Increase count");
        System.out.println("2. Decrease count");
        int choice = getIntInput("Enter choice (1-2): ");

        if (choice == 1) {
            collection.increaseCardCount(name);
            System.out.println("Card count increased successfully!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else if (choice == 2) {
            collection.removeCard(name);
            System.out.println("Card count decreased successfully!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else {
            System.out.println("Invalid choice.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }

    }

    /**
     * Gets integer input from user with validation
     *
     * @param prompt the prompt to display
     * @return the integer input
     */
    public int getIntInput(String prompt) {
        int input = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        return input;
    }
    // Getters
    public Collection getCollection() {
        return this.collection;
    }

    public Binder[] getBinders() {
        return this.binders;
    }

    public Deck[] getDecks() {
        return this.decks;
    }

    public int getBinderCount() {
        return this.binderCount;
    }

    public int getDeckCount() {
        return this.deckCount;
    }

    // Setters (adjusters kinda)
    public void addBinderCount(int count) {
        this.binderCount += count;
    }
    public void addDeckCount(int count) {
        this.deckCount += count;
    }
}
