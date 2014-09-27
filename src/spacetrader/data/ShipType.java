package spacetrader.data;

/**
 * This class details different types of spaceships and their capabilities
 * @author Bao
 */
public enum ShipType {
    FLEA("Flea", 10, 0, 0, 0, 1, 20, 4, 1, 2000 , 5  , 2 , 25 , -1, -1, 0, 1, 0),
    GNAT("Gnat", 15, 1, 0, 1, 1, 14 , 5, 2, 10000, 50 , 28, 100, 0 , 0 , 0, 1, 1),
    FIREFLY("Firefly", 20, 1, 1, 1, 1, 17 , 5, 3, 25000, 75 , 20, 100, 0 , 0 , 0, 1, 1),
    MOSQUITO("Mosquito", 15, 2, 1, 1, 1, 13 , 5, 5, 30000, 100, 20, 100, 0 , 1 , 0, 1, 1),
    BUMBLEBEE("BumbleBee", 25, 1, 2, 2, 2, 15 , 5, 7, 60000, 125, 15, 100, 0 , 1 , 0, 1, 2);
    private final String name;
    private final int cargoBay;
    private final int weaponSlots;
    private final int shieldSlots;
    private final int minTechLevel;
    private final int crew;
    private final int fuelCost;
    private final int price;
    private final int fuel;
    private final int gadgetSlots;
    private final int size;
    private final int repairCost;
    private final int trader;
    private final int pirate;
    private final int police;
    private final int hullStrength;
    private final int occurrence;
    private final int bounty;
    
    private ShipType(String name, int cargoBay, int weaponSlots, int shieldSlots, int gadgetSlots,
            int crew, int fuel, int minTechLevel, int fuelCost, int price, int bounty, int occurrence, int hullStrength,
            int police, int pirate, int trader, int repairCost, int size) {
        this.name = name;
        this.cargoBay = cargoBay;
        this.weaponSlots = weaponSlots;
        this.shieldSlots = shieldSlots;
        this.gadgetSlots = gadgetSlots;
        this.crew = crew;
        this.fuel = fuel;
        this.minTechLevel = minTechLevel;
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
    }

    public String getName() {
        return name;
    }

    public int getCargoBay() {
        return cargoBay;
    }

    public int getWeaponSlots() {
        return weaponSlots;
    }

    public int getShieldSlots() {
        return shieldSlots;
    }

    public int getMinTechLevel() {
        return minTechLevel;
    }

    public int getCrew() {
        return crew;
    }

    public int getFuelCost() {
        return fuelCost;
    }

    public int getPrice() {
        return price;
    }

    public int getFuel() {
        return fuel;
    }

    public int getGadgetSlots() {
        return gadgetSlots;
    }

    public int getSize() {
        return size;
    }

    public int getRepairCost() {
        return repairCost;
    }

    public int getTrader() {
        return trader;
    }

    public int getPirate() {
        return pirate;
    }

    public int getPolice() {
        return police;
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public int getBounty() {
        return bounty;
    }
    
}
