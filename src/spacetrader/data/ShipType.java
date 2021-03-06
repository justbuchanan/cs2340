package spacetrader.data;
/**
 * This class details different types of spaceships and their capabilities.
 *
 * @author Bao
 */
public enum ShipType {

    FLEA("Flea", 10, 0, 0, 4, 1, 20, 0, 1, 2000, 5, 2, 25, -1, -1, 0, 1, 0,
            "flea.png"),
    GNAT("Gnat", 15, 1, 0, 4, 1, 14, 1, 2, 10000, 50, 28, 100, 0, 0, 0, 1, 1,
            "gnat.png"),
    FIREFLY("Firefly", 20, 1, 1, 5, 1, 17, 1, 3, 25000, 75, 20, 100, 0, 0, 0,
            1, 1, null),
    MOSQUITO("Mosquito", 15, 2, 1, 5, 1, 13, 1, 5, 30000, 100, 20, 100, 0, 1, 0,
            1, 1, "mosquito.png"),
    BUMBLEBEE("BumbleBee", 25, 1, 2, 6, 2, 15, 2, 7, 60000, 125, 15, 100, 0, 1,
            0, 1, 2, null);

    private final String name;
    private final int cargoBay;
    private final int weaponSlots;
    private final int shieldSlots;
    private final int minTechLevel;
    private final int crew;
    private final int fuel;
    private final int gadgetSlots;
    private final int fuelCost;
    private final int price;
    private final int bounty; 
    private final int occurrence;
    private final int hullStrength;
    private final int police;
    private final int pirate;
    private final int trader;
    private final int size;
    private final int repairCost;
    private final String imageName;

    private ShipType(String name, int cargoBay, int weaponSlots,
            int shieldSlots, int minTechLevel, int crew, int fuel,
            int gadgetSlots, int fuelCost, int price, int bounty,
            int occurrence, int hullStrength, int police, int pirate,
            int trader, int repairCost, int size, String imageName) {
        this.name = name;
        this.cargoBay = cargoBay;
        this.weaponSlots = weaponSlots;
        this.shieldSlots = shieldSlots;
        this.minTechLevel = minTechLevel;
        this.crew = crew;
        this.fuel = fuel;
        this.gadgetSlots = gadgetSlots;
        this.fuelCost = fuelCost;
        this.price = price;
        this.bounty = bounty;
        this.occurrence = occurrence;
        this.hullStrength = hullStrength;
        this.police = police;
        this.pirate = pirate;
        this.trader = trader;
        this.repairCost = repairCost;
        this.size = size;
        this.imageName = imageName;
    }

    /**
     * Gets ship's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * gets number of cargo bays.
     *
     * @return
     */
    public int getCargoBay() {
        return cargoBay;
    }

    /**
     * gets number of weapon slots.
     *
     * @return
     */
    public int getWeaponSlots() {
        return weaponSlots;
    }

    /**
     * gets number of shields this ship can carry.
     *
     * @return
     */
    public int getShieldSlots() {
        return shieldSlots;
    }

    /**
     * gets the min tech level for a planet to sell this type of ship.
     *
     * @return
     */
    public int getMinTechLevel() {
        return minTechLevel;
    }

    /**
     * gets the max number of crew.
     *
     * @return
     */
    public int getCrew() {
        return crew;
    }

    /**
     * Gets the cost per unit of fuel for this ship type.
     *
     * @return
     */
    public int getFuelCost() {
        return fuelCost;
    }

    /**
     * Gets the default shipyard price for this ship.
     *
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets the max units of fuel this ship's tank can hold.
     *
     * @return
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * gets the total number of gadgets that can be installed in this ship.
     *
     * @return
     */
    public int getGadgetSlots() {
        return gadgetSlots;
    }

    /**
     * gets the size of this ship.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * gets the cost to repair 1 unit of damage to this ship.
     *
     * @return
     */
    public int getRepairCost() {
        return repairCost;
    }

    /**
     * Gets the trader attraction rating from this ship.
     *
     * @return
     */
    public int getTrader() {
        return trader;
    }

    /**
     * Gets the pirate attraction rating of this ship.
     *
     * @return
     */
    public int getPirate() {
        return pirate;
    }

    /**
     * Gets the police attraction rating from this ship.
     *
     * @return
     */
    public int getPolice() {
        return police;
    }

    /**
     * Gets the max hitpoints of this ship.
     *
     * @return
     */
    public int getHullStrength() {
        return hullStrength;
    }

    /**
     * Gets the frequency of this ship being used by the AI?
     *
     * @return
     */
    public int getOccurrence() {
        return occurrence;
    }

    /**
     * ?
     *
     * @return
     */
    public int getBounty() {
        return bounty;
    }

    /**
     * Gets the ships image name.
     * @return Image name
     */
    public String getImageName() {
        return imageName;
    }

}
