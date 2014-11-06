package spacetrader.data.ship_upgrades;

/**
 * Enum for shield upgrades on ships.
 * @author Griffith
 */
public enum Shield {

    ENERGY("Energy Shield", 3000, 6),
    REFLECTIVE("Reflective Shield", 5000, 7);

    private String name;
    private int price;
    private int techLevel;

    private Shield(String name, int price, int techLevel) {
        this.name = name;
        this.price = price;
        this.techLevel = techLevel;
    }
    
    public int getTechLevel() {
        return techLevel;
    }
    
    public int getPrice() {
        return price;
    }

}
