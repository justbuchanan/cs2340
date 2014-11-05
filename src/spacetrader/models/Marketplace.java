package spacetrader.models;

import spacetrader.data.Item;
import spacetrader.data.RadicalEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Player comes to marketplace to buy and sells goods
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
        for (int i = 0; i < Item.values().length; i++) {
            priceList.put(Item.values()[i], getPrice(Item.values()[i]));
        }
    }

    /**
     * returns the price of an item in the current solar system's market.
     *
     * @param item
     * @return price
     */
    public int getPrice(Item item) {
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
        return priceList.get(item);
    }

    /**
     * returns the price of an item in the current solar system's market
     *
     * @param item
     * @return selling price
     */
    public int getSellPrice(Item item) {
        return (int) (getBuyPrice(item) * DEPRECIATION_FACTOR);
    }

    /**
     * Check whether the item is available to buy
     *
     * @param item
     * @return buy-able
     */
    public boolean isBuyable(Item item) {
        if (this.solarSystem.getTechLevel().getValue() >= item.getMTLP() && 
                availableItems.get(item) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the item is sellable
     *
     * @param item
     * @return isSellable
     */
    public boolean isSellable(Item item) {
        if (this.solarSystem.getTechLevel().getValue() >= item.getMTLU()) {
            return true;
        }
        return false;
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
