package spacetrader.models;

import java.util.HashMap;
import java.util.HashSet;
import java.lang.Exception;

import spacetrader.data.Item;
import spacetrader.data.RadicalEvent;
import spacetrader.models.SolarSystem;

/**
 * Player comes to marketplace to buys and sells goods
 * 
 * @author Bao
 */
public class Marketplace {
    
    //  track which items are available and in what quantity
    private HashMap<Item, Integer> availableItems;
    
    //  The radical events that currently affect this MarketPlace
    private HashSet<RadicalEvent> currentRadicalEvents;
    
    public Marketplace(SolarSystem mySS) {
        //generator here
    }
    
    /**
     * returns the price of an item in the current solar system's market
     * returns -1 if the item is unobtainable in the current solar system
     * 
     * @param item
     * @return price
     */
    public int getPrice(Item item) {
        if (availableItems.containsKey(item)) {
            int price = item.getBasePrice();
            
            //  if a radical event is taking place that affects this item, apply the increase in cost
            if (currentRadicalEvents.contains(item.getIe())) {
                price += item.getIe().getPriceIncrease();
            }
            
            return price;
        } else {
            return -1;
        }
    }
    
    /**
     * Query item availability
     * 
     * @param item
     * @return Quantity of @item available
     */
    public int getQuantity(Item item) {
        if (availableItems.containsKey(item)) {
            return availableItems.get(item);
        } else {
            return 0;
        }
    }
    
    
    /**
     * Buy items from the MarketPlace
     * 
     * @param item
     * @param quantity - The number of this item to buy
     * @return The cost of the purchase
     * @throws Exception if there's an attempt to buy too many items
     */
    public int buy(Item item, int quantity) throws Exception {
        if (availableItems.get(item) < quantity) {
            throw new Exception("Request to buy more items than available.");
        } else {
            availableItems.put(item, availableItems.get(item) - quantity);
            return getPrice(item) * quantity;
        }
    }
    
    /**
     * Sell items back to the MarketPlace
     * 
     * @param item
     * @param quantity
     * @return The amount of money received in the transaction
     */
    public int sell(Item item, int quantity) {
        //  make sure the item exists in our records
        if (!availableItems.containsKey(item)) {
            availableItems.put(item, 0);
        }
        
        //  update the quantity
        availableItems.put(item, availableItems.get(item) + quantity);
        
        //  calculate the transaction total
        return getPrice(item) * quantity;
    }
    
    /**
     * Add a radical event to the MarketPlace
     * 
     * @param event 
     */
    public void addRadicalEvent(RadicalEvent event) {
        currentRadicalEvents.add(event);
    }
    
    /**
     * Remove a radical event from the MarketPlace
     * 
     * @param event 
     */
    public void removeRadicalEvent(RadicalEvent event) {
        currentRadicalEvents.remove(event);
    }
    
}
