package spacetrader.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import spacetrader.data.Item;
import spacetrader.data.ShipType;

/**
 * holds ship information
 * 
 * @author Bao
 */
public class Ship {
    private List<Integer> cargo;
    private ShipType type;
    
    public Ship(ShipType type) {
        cargo = new ArrayList<Integer>(Collections.nCopies(Item.values().length, 0));
        //Freebies
        addCargo(Item.WATER, 1);
        this.type = type;
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
     * Get cargo list
     * @return 
     */
    public List<Integer> getCargo() {
        return cargo;
    }

    /**
     * Set cargo list
     * @param cargo 
     */
    public void setCargo(List<Integer> cargo) {
        this.cargo = cargo;
    }

    /**
     * Get the max carrying capability of the ship
     * @return maximum number of cargo bays
     */
    public int getMaxCargo() {
        return type.getCargoBay();
    }
    
    /**
     * Get the total number of cargo bays
     * @return current number of cargo bays
     */
    public int getCurrentCargo() {
        int sum = 0;
        for (int i: cargo) {
            sum += i;
        }
        return sum;
    }
}
