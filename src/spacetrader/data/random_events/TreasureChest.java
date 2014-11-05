/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader.data.random_events;

import spacetrader.data.RandomEvent;
import spacetrader.models.Player;

/**
 * A random event where the player finds money.
 *
 * @author justbuchanan
 */
public class TreasureChest extends RandomEvent {
    
    public TreasureChest() {
        super(0.3, "Treasure Chest");
    }
    
    /**
     * 1000 Credits are added to the player's balance.
     * @param player The player this happened to
     * @return A description of the event
     */
    @Override
    public String apply(Player player) {
        player.setBalance(player.getBalance() + 1000);
        return "You found a treasure chest containing an extra 1000 credits!";
    }
}
