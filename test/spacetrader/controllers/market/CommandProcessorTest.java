/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader.controllers.market;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spacetrader.data.Item;
import spacetrader.data.Resource;
import spacetrader.data.TechLevel;
import spacetrader.models.Marketplace;
import spacetrader.models.Player;
import spacetrader.models.SolarSystem;

/**
 * This JUnit tests the command processor to ensure the buy item command
 * works properly
 * @author Bao
 */
public class CommandProcessorTest {
    private Player myPlayer;
    private Marketplace myMarket;
    private SolarSystem mySS;
    
    /**
     * Creates player and marketplace objects needed for the tests
     */
    @Before
    public void setUp() {
        myPlayer = new Player("Tester", new int[]{0, 0, 0, 0, 0});
        myPlayer.setBalance(10000);
        mySS = new SolarSystem();
        mySS.setResource(Resource.DESERT);
        mySS.setTechLevel(TechLevel.MEDIEVAL);
        myMarket = mySS.setMP();
    }
    
    /**
     * This test checks the following:
     * Market price is correct
     * Total = price * balance
     * Player's balance is deducted
     * Item is added to the ship
     * 
     * It also test undo and redo commands
     */
    @Test
    public void testAddCommand() {
        CommandProcessor cp = AbstractCommand.INSTANCE;
        Item item = Item.WATER;
        //Resets the cargo bays to zero
        myPlayer.getShip().getCargo().set(Item.WATER.getValue(), 0);
        int quantity = 3;
        BuyItemCommand cmd = new BuyItemCommand(myPlayer, myMarket, item, quantity);
        int initialBalance = myPlayer.getBalance();
        cp.doCommand(cmd);
        assertEquals("Balance was not deducted correctly", initialBalance - myMarket.getBuyPrice(item)*quantity, myPlayer.getBalance());
        assertEquals("Item was not added", new Integer(3), myPlayer.getShip().getCargo().get(item.getValue()));
        cp.undoCommand();
        assertEquals("Undo command did not restore player's balance", initialBalance, myPlayer.getBalance());
        assertEquals("Item was not removed", new Integer(0), myPlayer.getShip().getCargo().get(item.getValue()));
        cp.redoCommand();
        assertEquals("Redo command did not execute correctly", initialBalance - myMarket.getBuyPrice(item)*quantity, myPlayer.getBalance());
        assertEquals("Item was not added", new Integer(3), myPlayer.getShip().getCargo().get(item.getValue()));
    }
    
}
