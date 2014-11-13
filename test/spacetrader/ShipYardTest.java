package spacetrader;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import spacetrader.data.ShipType;
import spacetrader.data.TechLevel;
import spacetrader.data.Upgrade;
import spacetrader.models.ShipYard;
import spacetrader.models.SolarSystem;

/**
 * JUnit test for the shipyard.
 * @author Griffith
 */
public class ShipYardTest {

    private ShipYard sy1, sy4, sy7;

    @Before
    public void setUp() {
        SolarSystem ss1 = new SolarSystem();
        SolarSystem ss4 = new SolarSystem();
        SolarSystem ss7 = new SolarSystem();
        ss1.setTechLevel(TechLevel.PREAGRICULTURAL);
        ss4.setTechLevel(TechLevel.EARLYINDUSTRIAL);
        ss7.setTechLevel(TechLevel.HIGHTECH);
        sy1 = ss1.getSy();
        sy7 = ss7.getSy();
    }

    @Test
    public void testShipYard() {
        assertTrue(sy1.getAvailableShips().isEmpty());
        assertEquals(0, sy1.getAvailableShips().size());
        assertEquals(0, sy1.getAllUpgrades().size());
        assertEquals(2, sy4.getAvailableShips().size());
        assertTrue(sy4.getAvailableShips().containsKey(ShipType.FLEA));
        assertTrue(sy4.getAllUpgrades().containsKey(Upgrade.INVENTORY));
        assertEquals(5, sy7.getAvailableShips().size());
        assertEquals(10, sy7.getAllUpgrades().size());
        assertTrue(sy7.getAllUpgrades().containsKey(Upgrade.MILITARY));
    }

}
