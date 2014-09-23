package spacetrader;

import java.util.ArrayList;
import java.util.List;

/**
 * Player comes to marketplace to buys and sells goods
 * 
 * @author Bao
 */
public class Marketplace {
    
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
        //dummy code
        return 10*(item.getValue()+1);
    }
}
