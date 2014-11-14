package spacetrader.data;

/**
 * @author Michael The stage of a solar system's technological development
 */
public enum TechLevel {

    PREAGRICULTURAL(0, "Pre-Agricultural"),
    AGRICULTURAL(1, "Agricultural"),
    MEDIEVAL(2, "Medieval"),
    RENAISSANCE(3, "Renaissance"),
    EARLYINDUSTRIAL(4, "Early Industrial"),
    INDUSTRIAL(5, "Industrial"),
    POSTINDUSTRIAL(6, "Post Industrial"),
    HIGHTECH(7, "High-Tech");

    private int value;
    private String text;

    /**
     *
     * @param value
     * @param text
     */
    private TechLevel(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * Returns the text representation of this TechLevel.
     * @return string representation
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Gets the value of the TechLevel.
     *
     * @return numerical value for this type of TechLevel
     */
    public int getValue() {
        return this.value;
    }
}
