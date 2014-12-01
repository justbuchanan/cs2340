package spacetrader.data.random_events;

import spacetrader.data.Item;
import spacetrader.data.RandomEvent;
import spacetrader.models.Player;
import spacetrader.models.Ship;

/**
 * A random event where the ship springs a leak.
 *
 * @author justbuchanan
 */
public class WaterLeak extends RandomEvent {
    public WaterLeak() {
        super(0.2, "Water Leak");
    }

    /**
     * Drains all water from the ship's cargo if there's any present.
     * @param player The player this happened to
     * @return A description of the event
     */
    @Override
    public String apply(Player player) {
        Ship ship = player.getShip();
        int waterUnits = ship.countItemInCargo(Item.WATER);

        if (waterUnits > 0) {
            ship.removeCargo(Item.WATER, waterUnits);
            return "Your ship sprung a leak and all of your water is gone";
        } else {
            return "Your ship's water container is leaky, but you didn't have"
                    + "any water";
        }
    }
}

