package spacetrader.data.random_events;

import spacetrader.data.Item;
import spacetrader.data.RandomEvent;
import spacetrader.models.Player;
import spacetrader.models.Ship;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A random event where pirates steal some of your cargo.
 *
 * @author justbuchanan
 */
public class PirateRaid extends RandomEvent {
    public PirateRaid() {
        super(0.2, "Pirate Raid");
    }

    /**
     * The pirates steal 3 items from you if you have any cargo.
     *
     * @param player The player this happened to
     * @return A description of the encounter
     */
    @Override
    public String apply(Player player) {
        Ship ship = player.getShip();
        String desc = "You've been raided by pirates!  They stole: ";

        int countToSteal = 3;
        List<Item> itemList = Arrays.asList(Item.values());
        Collections.shuffle(itemList);
        for (Item item : itemList) {
            int count = ship.countItemInCargo(item);
            if (count > 0 && countToSteal > 0) {
                int stolen = Math.min(countToSteal, count);
                ship.removeCargo(item, stolen);
                countToSteal -= stolen;
                desc += stolen + " " + item.name() + " ";
            }
        }

        return desc;
    }
}
