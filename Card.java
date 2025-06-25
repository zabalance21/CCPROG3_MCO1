/**
 * Represents a trading card with name, rarity, variant, and value
 *
 * @author Theodore Garcia
 * @version 1.0
 */
import java.util.Scanner;

public class Card {
    public static class Rarity {
        public static final String COMMON = "Common";
        public static final String UNCOMMON = "Uncommon";
        public static final String RARE = "Rare";
        public static final String LEGENDARY = "Legendary";
    }

    public static class Variant {
        public static final String NORMAL = "Normal";
        public static final String EXTENDED_ART = "Extended-art";
        public static final String FULL_ART = "Full-art";
        public static final String ALT_ART = "Alt-art";
    }

    private String name;
    private String rarity;
    private String variant;
    private double baseValue;

    /**
     * Constructor for Card
     *
     * @param name      the name of the card (unique identifier)
     * @param rarity    the rarity of the card
     * @param variant   the variant of the card
     * @param baseValue the base value of the card in dollars
     */
    public Card(String name, String rarity, String variant, double baseValue) {
        this.name = name;
        this.rarity = rarity;
        this.variant = variant;
        this.baseValue = baseValue;
    }

    /**
     * Gets the name of the card
     *
     * @return the card name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the rarity of the card
     *
     * @return the card rarity
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * Gets the variant of the card
     *
     * @return the card variant
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Gets the base value of the card
     *
     * @return the base value in dollars
     */
    public double getBaseValue() {
        return baseValue;
    }
    /**
     * Calculates and returns the total value of the card including variant multiplier
     *
     * @return the total value of the card
     */
    public double getTotalValue() {
        double multiplier = 1.0;

        if (variant.equals(Variant.EXTENDED_ART)) {
            multiplier = 1.5;
        } else if (variant.equals(Variant.FULL_ART)) {
            multiplier = 2.0;
        } else if (variant.equals(Variant.ALT_ART)) {
            multiplier = 3.0;
        }

        return baseValue * multiplier;
    }

    /**
     * Returns detailed information about the card
     *
     * @return detailed card information
     */
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== Card Details ===\n");
        info.append("Name: ").append(name).append("\n");
        info.append("Rarity: ").append(rarity).append("\n");
        info.append("Variant: ").append(variant).append("\n");
        info.append("Base Value: $").append(String.format("%.2f", baseValue)).append("\n");
        info.append("Total Value: $").append(String.format("%.2f", getTotalValue())).append("\n");

        return info.toString();
    }

    /**
     * Checks if this card is equal to another card based on name, rarity, and variant
     *
     * @param other the other card to compare
     * @return true if cards are equal, false otherwise
     */
    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return this.name.equals(other.name) &&
                this.rarity.equals(other.rarity) &&
                this.variant.equals(other.variant);
    }

    /**
     * Returns a string representation of the card
     *
     * @return string representation of the card
     */
    public String toString() {
        return name + " (" + rarity + ", " + variant + ")";
    }
}