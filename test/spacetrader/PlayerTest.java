/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import spacetrader.data.Item;
import spacetrader.data.ShipType;

import spacetrader.models.Player;
import spacetrader.models.Skill;
import spacetrader.models.Ship;

/**
 *
 * @author Pbale95 - Philip Bale
 */
public class PlayerTest {

    private Player player;

    public PlayerTest() {
    }

    @Before
    public void setUp() {
        player = new Player("TestPlayer", 1000,
                new Ship(ShipType.MOSQUITO), 1, 2, 3, 4, 5);
        player.setSkill(Skill.ENGINEER, 200);
        for (int i = 0; i < 10; i++) {
            player.getShip().addCargo(Item.values()[i], i);
        }
    }

    @After
    public void tearDown() {
        player = null;
    }

    @Test
    public void testPlayerAtrributes() {
        assertEquals(1, player.getSkill(Skill.PILOT).getPoints());
        assertEquals(2, player.getSkill(Skill.FIGHTER).getPoints());
        assertEquals(3, player.getSkill(Skill.TRADER).getPoints());
        assertEquals(200, player.getSkill(Skill.ENGINEER).getPoints());
        assertEquals(5, player.getSkill(Skill.INVESTOR).getPoints());
        assertEquals(ShipType.MOSQUITO, player.getShip().getType());
        assertEquals("TestPlayer", player.getName());
        // Account for the combinatorics of the loop
        assertEquals(46, player.getShip().getCurrentCargo());
    }
}
