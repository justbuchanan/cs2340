package spacetrader.models;

import spacetrader.data.Government;
import spacetrader.data.Item;
import spacetrader.data.RadicalEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Player comes to marketplace to buy and sells goods.
 *
 * @author Bao
 */
public class Marketplace {

    //  track which items are available and in what quantity
    private HashMap<Item, Integer> availableItems;
    private HashMap<Item, Integer> priceList;

    //  The radical events that currently affect this MarketPlace
    private HashSet<RadicalEvent> currentRadicalEvents;

    private SolarSystem solarSystem;
    private Random rand = new Random();

    private final double DEPRECIATION_FACTOR = .9;

    public Marketplace(SolarSystem mySS) {
        solarSystem = mySS;
        availableItems = mySS.getAvailableItems();
        currentRadicalEvents = new HashSet<>();
        priceList = new HashMap<>();
        for (Item value : Item.values()) {
            priceList.put(value, getPrice(value));
        }
    }

    /**
     * returns the price of an item in the current solar system's market.
     *
     * @param item
     * @return price
     */
    private int getPrice(Item item) {
        int price = item.getBasePrice();

        //  if a radical event is taking place that affects this item, apply the
        //  increase in cost
        if (currentRadicalEvents.contains(item.getIE())) {
            price += item.getIE().getPriceIncrease();
        }

        //  the price increases per tech level
        price += (this.solarSystem.getTechLevel().getValue() - item.getMTLP())
                * item.getIPL();
        boolean head = rand.nextBoolean();
        int variance = rand.nextInt(item.getVar() + 1);
        if (head) {
            price += item.getBasePrice() * variance / 10;
        } else {
            price -= item.getBasePrice() * variance / 10;
        }
        if (price < 0) {
            price = 0;
        }

        return price;
    }

    /**
     * returns the buying price of an item in the current solar system's market
     *
     * @param item
     * @return buying price
     */
    public int getBuyPrice(Item item) {
    	int cost = priceList.get(item);
    	//adjust price based on Government
    	switch (solarSystem.getGov()) {
    	case DICTATORSHIP:
    		cost = (cost * 9) / 10;
    		break;
    	case SOCIALIST:
    		if (item == Item.MEDICINE) cost = (cost * 3) / 4;
    		break;
    	}
        //return priceList.get(item);
        return cost;
    }

    /**
     * returns the price the Player gets from selling the Item in this MarketPlace
     *
     * @param item
     * @return selling price
     */
    public int getSellPrice(Item item) {
    	int cost = (int)(getBuyPrice(item) * DEPRECIATION_FACTOR);
        // adjust the price based on the Government of this system
     /*   if (solarSystem.getGov() == Government.CAPITALIST && item == Item.ORE) {
        	cost *= 2;
        }*/
        switch (solarSystem.getGov()) {
        case CAPITALIST:
        	if (item == Item.ORE) cost *= 2;
        	break;
        case CONFEDERACY:
        	if (item == Item.GAMES) cost *= 2;
        	break;        	
        case CORPORATE:
        	if (item == Item.ROBOTS) cost *= 2;
        	break;
        case CYBERNETIC: 
        	if (item == Item.ROBOTS || item == Item.MACHINES) cost = (cost * 3) / 2;
        	break;
        case DEMOCRACY:
        	if (item == Item.GAMES || item == Item.NARCOTICS) cost *= 2;
        	break;
        case FASCIST:
        	if (item == Item.MACHINES) cost *= 2;
        	break;
        case FEUDAL:
        	if (item == Item.FIREARMS) cost *= 2;
        	break;
        case MILITARY:
        	if (item == Item.ROBOTS) cost *= 2;
        	break;
        case MONARCHY:
        	if (item == Item.MEDICINE) cost *= 2;
        	break;
        case TECHNOCRACY:
        	if (item == Item.WATER) cost *= 2;
        	break;
        case THEOCRACY:
        	if (item == Item.NARCOTICS) cost *= 2;
        	break;        	
        }
        
        return cost;
        //return (int) (getBuyPrice(item) * DEPRECIATION_FACTOR);
    }

    /**
     * Check whether the item is available to buy
     *
     * @param item
     * @return buy-able
     */
    public boolean isBuyable(Item item) {
        return this.solarSystem.getTechLevel().getValue() >= item.getMTLP() && 
                availableItems.get(item) > 0;
    }

    /**
     * Check whether the item is sellable
     *
     * @param item
     * @return isSellable
     */
    public boolean isSellable(Item item) {
    	if (solarSystem.getGov() == Government.SATORI && item == Item.FIREARMS) return false;    	
        return this.solarSystem.getTechLevel().getValue() >= item.getMTLU();
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
     */
    public int buy(Item item, int quantity) {
        availableItems.put(item, availableItems.get(item) - quantity);
        return getBuyPrice(item) * quantity;
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
        return getSellPrice(item) * quantity;
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
