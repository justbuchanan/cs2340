package spacetrader.models;

import spacetrader.data.Item;
import spacetrader.data.ShipType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * holds ship information.
 *
 * @author Bao
 */
public class Ship {

    private List<Integer> cargo;
    private int fuel;
    private ShipType type;
    private int hullStrength;
    private int weaponSlots;
    private int shieldSlots;
    private int gadgetSlots;
    private int extraCargo;
    
    /**
     * Creates a new ship with initial type given and full fuel
     * @param type ship type
     */
    public Ship(ShipType type) {
        this(type, type.getFuel());
    }
    
    /**
     *  Creates a new ship with type and amount of fuel given
     * @param type ship type
     * @param fuel amount of fuel
     */
    public Ship(ShipType type, int fuel) {
        cargo = new ArrayList<Integer>(Collections.nCopies(Item.values().length,
                0));
        this.type = type;
        //Freebies: 1 cargo bay of water. Yummy!
        addCargo(Item.WATER, 1);
        //Let's fill up your fuel tank
        this.fuel = fuel;
        this.hullStrength = type.getHullStrength();
        this.weaponSlots = type.getWeaponSlots();
        this.shieldSlots = type.getShieldSlots();
        this.gadgetSlots = type.getGadgetSlots();
        extraCargo = 0;
    }

    /**
     * Adds item to cargo.
     *
     * @param item
     * @param quantity
     */
    public void addCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q + quantity);
    }

    /**
     * Removes item from cargo.
     *
     * @param item
     * @param quantity
     */
    public void removeCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q - quantity);
    }

    /**
     * Counts the number of the given item type in cargo.
     *
     * @param item The item
     * @return the number of @item in cargo
     */
    public int countItemInCargo(Item item) {
        return cargo.get(item.getValue());
    }

    /**
     * Gets cargo list.
     *
     * @return
     */
    public List<Integer> getCargo() {
        return cargo;
    }

    /**
     * Sets cargo list.
     *
     * @param cargo
     */
    public void setCargo(List<Integer> cargo) {
        this.cargo = cargo;
    }

    /**
     * Gets the max carrying capability of the ship.
     *
     * @return maximum number of cargo bays
     */
    public int getMaxCargo() {
        return type.getCargoBay() + extraCargo;
    }

    /**
     * Gets the total number of cargo bays.
     *
     * @return current number of cargo bays
     */
    public int getCurrentCargo() {
        int sum = 0;
        for (int i : cargo) {
            sum += i;
        }
        return sum;
    }

    /**
     * Gets the amount of fuel left
     *
     * @return amount of fuel
     */
    public int getFuelReading() {
        return fuel;
    }

    /**
     * Gets fuel capacity of the ship.
     *
     * @return amount of fuel
     */
    public int getFuelCapacity() {
        return type.getFuel();
    }

    /**
     * Fills the fuel tank fully.
     */
    public void refill() {
        this.fuel += type.getFuel();
    }

    /**
     * Refills fuel tank with a specific amount of fuel.
     *
     * @param fuel
     */
    public void refill(int fuel) {
        this.fuel += fuel;
    }

    /**
     * Gets type of ship.
     *
     * @return ship type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * Sets type of ship.
     *
     * @param type
     */
    public void setType(ShipType type) {
        this.type = type;
    }

    /**
     * Gets hull strength.
     * @return hull strength
     */
    public int getHullStrength() {
        return hullStrength;
    }

    /**
     * Sets hull strength.
     * @param hullStrength
     */
    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }

    /**
     * Gets weapon slots
     * @return weapon slots
     */
    public int getWeaponSlots() {
        return weaponSlots;
    }

    /**
     * Gets shield slots
     * @return shield slots
     */
    public int getShieldSlots() {
        return shieldSlots;
    }
    
    /**
     * Gets gadget slots
     * @return gadget slots
     */
    public int getGadgetSlots() {
        return gadgetSlots;
    }
    
    /**
     * Sets weapon slot number
     * @param weaponSlots 
     */
    public void setWeaponSlots(int weaponSlots) {
        this.weaponSlots = weaponSlots;
    }

    /**
     * Sets shield slot number
     * @param shieldSlots 
     */
    public void setShieldSlots(int shieldSlots) {
        this.shieldSlots = shieldSlots;
    }

    /**
     * Sets gadget slot number
     * @param gadgetSlots 
     */
    public void setGadgetSlots(int gadgetSlots) {
        this.gadgetSlots = gadgetSlots;
    }
    
    /**
     * Fills one weapon slot
     */
    public void fillWeapon() {
        weaponSlots--;
    }
    
    /**
     * Fills one shield slot
     */
    public void fillShield() {
        shieldSlots--;
    }
    
    /**
     * Fills one gadget slot
     */
    public void fillGadget() {
        gadgetSlots--;
    }
    
    /**
     * Adds the extra cargo slots to the ship
     * @param extraCargo 
     */
    public void setExtraCargo(int extraCargo) {
        this.extraCargo = extraCargo;
    }

}
