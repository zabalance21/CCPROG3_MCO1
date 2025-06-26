import java.util.Scanner;

public class BinderManager {
    /**
     * Manages existing binders
     */
    public void manageBinders(TradingCardInventorySystem tcis) {
        boolean inBinderMenu = true;

        while (inBinderMenu) {
            System.out.println("\n=== Manage Binders ===");
            System.out.println("1. Create a new Binder");
            System.out.println("2. Delete a Binder");
            System.out.println("3. Add Card to Binder");
            System.out.println("4. Remove Card from Binder");
            System.out.println("5. Trade Card");
            System.out.println("6. View Binder");
            System.out.println("0. Go back to Main Menu");

            int choice = tcis.getIntInput("Enter your choice: ");

            if (choice == 1) {
                createBinder(tcis);
            } else if (choice == 2) {
                deleteBinder(tcis);
            } else if (choice == 3) {
                addCardToBinder(tcis);
            } else if (choice == 4) {
                removeCardFromBinder(tcis);
            } else if (choice == 5) {
                tradeCard(tcis);
            } else if (choice == 6) {
                viewBinder(tcis);
            } else if (choice == 0) {
                inBinderMenu = false;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Creates a new binder
     */
    public void createBinder(TradingCardInventorySystem tcis) {
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        System.out.println("\n=== Create New Binder ===");
        System.out.print("Enter binder name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Binder name cannot be empty.");
            return;
        }

        for (int i = 0; i < tcis.getBinderCount(); i++) {
            if (binders[i].getName().equals(name)) {
                System.out.println("Binder with this name already exists.");
                return;
            }
        }

        binders[binderCount] = new Binder(name);
        tcis.addBinderCount(1);
        System.out.println("Binder created successfully!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Deletes a binder and returns cards to collection
     */
    public void deleteBinder(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Delete Binder ===");
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        if (binderCount == 0) {
            System.out.println("No binders to delete.");
            return;
        }

        displayBinders(tcis);

        System.out.print("Enter binder name to delete: ");
        String name = scanner.nextLine().trim();

        for (int i = 0; i < binderCount; i++) {
            if (binders[i].getName().equals(name)) {
                Card[] binderCards = binders[i].getAllCards();
                for (int j = 0; j < binderCards.length; j++) {
                    if (binderCards[j] != null) {
                        tcis.getCollection().addCard(binderCards[j]);
                    }
                }

                for (int j = i; j < binderCount - 1; j++) {
                    binders[j] = binders[j + 1];
                }
                binders[binderCount - 1] = null;
                tcis.addBinderCount(-1);

                System.out.println("Binder deleted successfully! All cards returned to collection.");
                return;
            }
        }

        System.out.println("Binder not found.");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Displays all binders
     */
    public void displayBinders(TradingCardInventorySystem tcis) {
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();
        System.out.println("Available Binders:");
        for (int i = 0; i < binderCount; i++) {
            System.out.println("- " + binders[i].getName());
        }
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Adds a card to a binder
     */
    public void addCardToBinder(TradingCardInventorySystem tcis) {
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        System.out.println("\n=== Add Card to Binder ===");
        if (binderCount == 0) {
            System.out.println("No binders available.");
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

        displayBinders(tcis);

        System.out.print("Enter binder name: ");
        String binderName = scanner.nextLine().trim();

        Binder selectedBinder = null;
        int found = 0;
        for (int i = 0; i < binderCount && found == 0; i++) {
            if (binders[i].getName().equals(binderName)) {
                selectedBinder = binders[i];
                found = 1;
            }
        }

        if (selectedBinder == null) {
            System.out.println("Binder not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (selectedBinder.isFull()) {
            System.out.println("Binder is full. Cannot add more cards.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        tcis.getCollection().displayAvailableCards();

        System.out.print("Enter card name to add: ");
        String cardName = scanner.nextLine().trim();

        Card card = tcis.getCollection().getAvailableCard(cardName);
        if (card != null) {
            selectedBinder.addCard(card);
            tcis.getCollection().removeCard(cardName);
            System.out.println("Card added to binder successfully!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else {
            System.out.println("Card not available in collection.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Removes a card from a binder
     */
    public void removeCardFromBinder(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Remove Card from Binder ===");
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        if (binderCount == 0) {
            System.out.println("No binders available.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayBinders(tcis);

        System.out.print("Enter binder name: ");
        String binderName = scanner.nextLine().trim();

        Binder selectedBinder = null;
        int found = 0;
        for (int i = 0; i < binderCount && found == 0; i++) {
            if (binders[i].getName().equals(binderName)) {
                selectedBinder = binders[i];
                found = 1;
            }
        }

        if (selectedBinder == null) {
            System.out.println("Binder not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (selectedBinder.isEmpty()) {
            System.out.println("Binder is empty.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        selectedBinder.displayCards();

        System.out.print("Enter card name to remove: ");
        String cardName = scanner.nextLine().trim();

        Card card = selectedBinder.removeCard(cardName);
        if (card != null) {
            tcis.getCollection().addCard(card);
            System.out.println("Card removed from binder and returned to collection!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else {
            System.out.println("Card not found in binder.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Handles trading cards
     */
    public void tradeCard(TradingCardInventorySystem tcis) {
        System.out.println("\n=== Trade Card ===");
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        // Display available binders
        System.out.println("Available binders:");
        for (int i = 0; i < binderCount; i++) {
            if (binders[i] != null) {
                System.out.println("- " + binders[i].getName());
            }
        }

        if (tcis.getBinderCount() == 0) {
            System.out.println("No binders available for trading.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Select binder
        System.out.print("Enter binder name: ");
        String binderName = scanner.nextLine().trim();

        Binder selectedBinder = null;
        int found = 0;
        for (int i = 0; i < binderCount && found == 0; i++) {
            if (binders[i].getName().equals(binderName)) {
                selectedBinder = binders[i];
                found = 1;
            }
        }

        if (selectedBinder == null) {
            System.out.println("Binder not found.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        if (selectedBinder.isEmpty()) {
            System.out.println("Binder is empty. No cards to trade.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Select outgoing card
        selectedBinder.displayCards();
        System.out.print("Enter name of card to trade away: ");
        String outgoingCardName = scanner.nextLine().trim();
        Card outgoingCard = selectedBinder.getCard(outgoingCardName);

        if (outgoingCard == null) {
            System.out.println("Card not found in binder.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Create incoming card
        System.out.println("Enter details of the card you want to receive:");
        Card incomingCard;
        do{
            incomingCard = tcis.addCard('T');
        } while (incomingCard == null);

        // Compare values of cards
        double valueDifference = Math.abs(incomingCard.getTotalValue() - outgoingCard.getTotalValue());

        if (valueDifference >= 1.0) {
            System.out.printf("Warning: Value difference is $%.2f\n", valueDifference);
            System.out.print("Do you want to proceed with the trade? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("yes")) {
                tcis.getCollection().addCard(outgoingCard);
                System.out.println("Trade cancelled.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return;
            }
        }

        // If everything successful, execute trade
        selectedBinder.removeCard(outgoingCardName);
        selectedBinder.addCard(incomingCard);
        System.out.println("Trade completed successfully!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Views contents of a binder
     */
    public void viewBinder(TradingCardInventorySystem tcis) {
        System.out.println("\n=== View Binder ===");
        Scanner scanner = new Scanner(System.in);
        Binder[] binders = tcis.getBinders();
        int binderCount = tcis.getBinderCount();

        if (binderCount == 0) {
            System.out.println("No binders available.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        displayBinders(tcis);

        System.out.print("Enter binder name: ");
        String binderName = scanner.nextLine().trim();

        for (int i = 0; i < binderCount; i++) {
            if (binders[i].getName().equals(binderName)) {
                binders[i].displayCards();
                return;
            }
        }

        System.out.println("Binder not found.");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
