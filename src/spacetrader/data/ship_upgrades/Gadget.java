package spacetrader.data.ship_upgrades;

/**
 * Enum for gadget upgrades on ships.
 * @author Griffith
 */
public enum Gadget {

    INVENTORY("5 Extra Cargo Slots", 5000, 1, true),
    NAV("Navigation System", 3000, 5, true),
    AUTOREPAIR("Auto-Repair", 10000, 7, true),
    TARGET("Targetting System", 7000, 6, true),
    CLOAK("Cloaking Device", 2000, 7, false);

    private String name;
    private int price;
    private int techLevel;
    private boolean statBoost;

    private Gadget(String name, int price, int techLevel, boolean statBoost) {
        this.name = name;
        this.price = price;
        this.techLevel = techLevel;
        this.statBoost = statBoost;
    }

}
