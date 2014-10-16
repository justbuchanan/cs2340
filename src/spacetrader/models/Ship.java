package spacetrader.models;

import spacetrader.data.Item;
import spacetrader.data.ShipType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * holds ship information
 *
 * @author Bao
 */
public class Ship {

    private List<Integer> cargo;
    private int fuel;
    private ShipType type;

    public Ship(ShipType type) {
        cargo = new ArrayList<Integer>(Collections.nCopies(Item.values().length, 0));
        this.type = type;
        //Freebies: 1 cargo bay of water. Yummy!
        addCargo(Item.WATER, 1);
        //Let's fill up your fuel tank
        fuel = type.getFuel();
    }

    /**
     * Add item to cargo
     *
     * @param item
     * @param quantity
     */
    public void addCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q + quantity);
    }

    /**
     * Remove item from cargo
     *
     * @param item
     * @param quantity
     */
    public void removeCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q - quantity);
    }
    
    /**
     * Counts the number of the given item type in cargo
     * 
     * @param item The item
     * @return the number of @item in cargo
     */
    public int countItemInCargo(Item item) {
        return cargo.get(item.getValue());
    }

    /**
     * Gets cargo list
     *
     * @return
     */
    public List<Integer> getCargo() {
        return cargo;
    }

    /**
     * Sets cargo list
     *
     * @param cargo
     */
    public void setCargo(List<Integer> cargo) {
        this.cargo = cargo;
    }

    /**
     * Gets the max carrying capability of the ship
     *
     * @return maximum number of cargo bays
     */
    public int getMaxCargo() {
        return type.getCargoBay();
    }

    /**
     * Gets the total number of cargo bays
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
     * Gets fuel capacity of the ship
     *
     * @return amount of fuel
     */
    public int getFuelCapacity() {
        return type.getFuel();
    }

    /**
     * Fills the fuel tank fully
     */
    public void refill() {
        this.fuel += type.getFuel();
    }

    /**
     * Refills fuel tank with a specific amount of fuel
     *
     * @param fuel
     */
    public void refill(int fuel) {
        this.fuel += fuel;
    }

    /**
     * Gets type of ship
     *
     * @return ship type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * Sets type of ship
     *
     * @param type
     */
    public void setType(ShipType type) {
        this.type = type;
    }

}
