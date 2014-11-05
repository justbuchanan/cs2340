/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader;

import java.util.HashMap;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spacetrader.data.Item;
import spacetrader.data.Resource;
import spacetrader.data.TechLevel;

import spacetrader.models.SolarSystem;
import spacetrader.models.Marketplace;

/**
 *
 * @author justbuchanan
 */
public class MarketPlaceTest {
    
    private SolarSystem ss;
    
    public MarketPlaceTest() {
    }
    
    @Before
    public void setUp() {
        ss = new SolarSystem();
        ss.setName("tinuj");
        ss.setResource(Resource.LOTSOFHERBS);
        ss.setTechLevel(TechLevel.HIGHTECH);
        HashMap<Item, Integer> items = new HashMap<>();
        ss.setAvailableItems(items);
        ss.setMP();
    }
    
    @After
    public void tearDown() {
        ss = null;
    }

    @Test
    public void testSell() {
        ss.getMP().sell(Item.FIREARMS, 5);
        assertEquals(5, ss.getMP().getQuantity(Item.FIREARMS));
        
        ss.getMP().sell(Item.FIREARMS, 3);
        assertEquals(8, ss.getMP().getQuantity(Item.FIREARMS));
    }
}
