package spacetrader.data.ship_upgrades;

/**
 * Enum for weapon upgrades on ships.
 * @author Griffith
 */
public enum Weapon {

    PULSE("Pulse Laser", 1000, 5),
    BEAM("Beam Laser", 2500, 6),
    MILITARY("Military Laser", 5000, 7);

    private final String name;
    private final int price;
    private final int techLevel;

    private Weapon(String name, int price, int techLevel) {
        this.name = name;
        this.price = price;
        this.techLevel = techLevel;
    }
}
