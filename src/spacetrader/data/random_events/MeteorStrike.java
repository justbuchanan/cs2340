/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader.data.random_events;

import spacetrader.data.RandomEvent;
import spacetrader.models.Player;

/**
 *
 * @author justbuchanan
 */
public class MeteorStrike extends RandomEvent {
    public MeteorStrike() {
        super(0.2, "Meteor Strike");
    }

    @Override
    public String apply(Player player) {
        //  FIXME: do something

        return "You were hit by a meteor!!!";
    }
}

