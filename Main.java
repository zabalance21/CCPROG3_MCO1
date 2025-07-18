import java.util.Scanner;
/**
 * Main entry point for the Trading Card Inventory System
 * This class serves as the application launcher
 *
 * @author Theodore Garcia
 * @version 1.0
 */
public class Main {
    /**
     * Main method to launch the Trading Card Inventory System
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        TradingCardInventorySystem tcis = new TradingCardInventorySystem();
        BinderManager binderManager = new BinderManager();
        DeckManager deckManager = new DeckManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            tcis.displayMainMenu();
            int choice = tcis.getIntInput("Enter your choice: ");

            if (choice == 1) {
                System.out.println("\n=== Add Card ===");
                Card cardToAdd = tcis.addCard('C');
                if (cardToAdd != null)
                {
                    tcis.getCollection().addCard(cardToAdd);
                    cardToAdd = null;
                    System.out.println("Card added successfully!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                }
            } else if (choice == 2 && tcis.hasCardsInCollection()) {
                tcis.manageCardCount();
            } else if (choice == 3 && tcis.hasCardsInCollection()) {
                tcis.getCollection().displayCard();
            } else if (choice == 4 && tcis.hasCardsInCollection()) {
                tcis.getCollection().displayCollection();
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            } else if (choice == 5) {
               if (tcis.getBinderCount() == 0) {
                    binderManager.createBinder(tcis);
                } else {
                    binderManager.manageBinders(tcis);
                }
            } else if (choice == 6) {
                if (tcis.getDeckCount() == 0) {
                    deckManager.createDeck(tcis);
                } else {
                    deckManager.manageDecks(tcis);
                }
            } else if (choice == 0) {
                running = false;
                System.out.println("Thank you for using Trading Card Inventory System!");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}