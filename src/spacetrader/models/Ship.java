package spacetrader.models;

import spacetrader.data.Item;

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
    private int cargo_bay_number;
    private String type;
    
    public Ship(String type) {
        cargo = new ArrayList<Integer>(Collections.nCopies(Item.values().length, 0));
        //Freebies
        addCargo(Item.WATER, 1);
        cargo_bay_number = 14;
        this.type = type;
    }
    
    /**
     * Adds items to cargo
     * 
     * @param item
     * @param quantity 
     */
    public void addCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q + quantity);
    } 
    
    /**
     * Removes items from cargo
     * 
     * @param item
     * @param quantity 
     */
    public void removeCargo(Item item, int quantity) {
        int q = cargo.get(item.getValue());
        cargo.set(item.getValue(), q - quantity);
    }
    
    public List<Integer> getCargo() {
        return cargo;
    }

    public void setCargo(List<Integer> cargo) {
        this.cargo = cargo;
    }

    public int getMaxCargo() {
        return cargo_bay_number;
    }

    public void setMaxCargo(int number) {
        this.cargo_bay_number = number;
    }
    
}
