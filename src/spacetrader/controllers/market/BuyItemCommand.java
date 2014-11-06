package spacetrader.controllers.market;

import spacetrader.data.Item;
import spacetrader.models.Marketplace;
import spacetrader.models.Player;

/**
 * @author Bao Vu
 * @version 1.0
 *
 */
public class BuyItemCommand extends AbstractCommand {
    
    private Player thePlayer;
    private Marketplace theMarketplace;
    private Item theItem;
    private int theQuantity;

    public BuyItemCommand(Player p, Marketplace m, Item i, int q) {
        thePlayer = p;
        theMarketplace = m;
        theItem = i;
        theQuantity = q;
    }

    /* (non-Javadoc)
     * @see AbstractCommand#doIt()
     */
    @Override
    public boolean doIt() {
        int newBalance = thePlayer.getBalance() - theQuantity*theMarketplace.getBuyPrice(theItem);
        if (newBalance >= 0 && thePlayer.getShip().getCurrentCargo() + theQuantity <= thePlayer.getShip().getMaxCargo()) {
            theMarketplace.buy(theItem, theQuantity);
            thePlayer.getShip().addCargo(theItem, theQuantity);
            thePlayer.setBalance(newBalance);
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see AbstractCommand#undoIt()
     */
    @Override
    public boolean undoIt() {
        theMarketplace.buy(theItem, -theQuantity);
        thePlayer.getShip().addCargo(theItem, -theQuantity);
        thePlayer.setBalance(thePlayer.getBalance() + theQuantity*theMarketplace.getBuyPrice(theItem));
        return true;
    }

}
