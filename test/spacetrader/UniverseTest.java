package spacetrader;

import java.util.List;

//import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import spacetrader.models.SolarSystem;
import spacetrader.models.Universe;

public class UniverseTest {

    private Universe uni;
    private int uniWidth;
    private int uniHeight;
    private int numSS;
    public UniverseTest() {}
    @Before
    public void setUp() {
	uniWidth = 3;
	uniHeight = 4;
	numSS = 5;
        //width 3, height 4, 5 SolarSystems
	uni = Universe.generateUniverse(uniWidth, uniHeight, numSS);
    }
    @Test
    public void testUni() {
	assertEquals(false, (uni == null));
    }
    @Test
    public void testUniWidth() {
	assertEquals(uniWidth, uni.getWidth());
    }
    @Test
    public void testUniHeight() {
	assertEquals(uniHeight, uni.getHeight());
    }
    @Test
    public void testUniNumSS() {
	List<SolarSystem> list = uni.getSolarSystems();
	//check that the correct number of SolarSystems are created
	assertEquals(numSS, list.size());
    }
    @Test
    public void testUniSS() {
	List<SolarSystem> list = uni.getSolarSystems();
	//check that SolarSystem names and locations are correctly recognized
	for (int i = 0; i < list.size(); i++) {
            SolarSystem ss = list.get(i);
            assertEquals(true, uni.hasSolarSystemName(ss.getName()));
            assertEquals(true, uni.hasSolarSystemAt(ss.getX(), ss.getY()));
	}
    }
    @Test
    public void testUniSSLocations() {
        //invalid SS location
	assertEquals(false, uni.hasSolarSystemAt(0, -1));
        //invalid SS location
	assertEquals(false, uni.hasSolarSystemAt(-1, 0));
        //invalid SS location
	assertEquals(false, uni.hasSolarSystemAt(uniWidth, 1));
        //invalid SS location
	assertEquals(false, uni.hasSolarSystemAt(1, uniHeight));
    }
}
