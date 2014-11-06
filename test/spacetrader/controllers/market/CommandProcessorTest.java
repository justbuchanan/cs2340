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
import spacetrader.models.Marketplace;
import spacetrader.models.Player;

/**
 * Tests for Command Processor
 * @author Bao
 */
public class CommandProcessorTest {
    private Player myPlayer;
    private Marketplace myMarket;
    
    @Before
    public void setUp() {
        myPlayer = new Player("Tester", new int[]{0, 0, 0, 0, 0});
        myPlayer.setBalance(1000);
        myMarket = null;
    }
    @Test
    public void testAddCommand() {
        CommandProcessor cp = AbstractCommand.INSTANCE;
        Item item = Item.FIREARMS;
        int quantity = 3;
        BuyItemCommand cmd = new BuyItemCommand(myPlayer, myMarket, item, quantity);
        cp.doCommand(cmd);
        cp.undoCommand();
        cp.redoCommand();
    }
    
}
