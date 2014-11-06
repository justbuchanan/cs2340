/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader.data.random_events;

import spacetrader.data.Item;
import spacetrader.data.RandomEvent;
import spacetrader.models.Player;
import spacetrader.models.Ship;

/**
 * An event where the police search your vehicle for drugs and guns.
 *
 * @author justbuchanan
 */
public class PoliceSearch extends RandomEvent {
    public PoliceSearch() {
        super(0.3, "Police Search");
    }

    /**
     * If the player has narcotics or firearms, removes them.
     *
     * @param player The player the event happened to.
     * @return A description of the encounter
     */
    @Override
    public String apply(Player player) {
        Ship ship = player.getShip();
        int gunsAmount = ship.countItemInCargo(Item.FIREARMS);
        int drugsAmount = ship.countItemInCargo(Item.NARCOTICS);

        if (gunsAmount + drugsAmount > 0) {
            ship.removeCargo(Item.FIREARMS, gunsAmount);
            ship.removeCargo(Item.NARCOTICS, drugsAmount);

            return "The police searched your ship and confiscated "
            + gunsAmount + " firearms and " + drugsAmount
            + " units of narcotics.";
        } else {
            return "The police searched your ship and found no illegal goods. "
                    + "Carry on now.";
        }
    }
}