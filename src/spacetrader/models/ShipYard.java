package spacetrader.models;

import java.util.HashMap;
import spacetrader.data.ShipType;
import spacetrader.data.Upgrade;

/**
 * A little something to make all of our lives WAY easier in terms of handling
 * upgrades and ship availability.
 *
 * @author Griffith
 */
public class ShipYard {

    private SolarSystem ss;
    private HashMap<ShipType, Integer> availableShips;
    private HashMap<Upgrade, Integer> allUpgrades;

    /**
     *
     * @param ss
     */
    public ShipYard(SolarSystem ss) {
        this.ss = ss;
        availableShips = new HashMap<>();
        allUpgrades = new HashMap<>();
        setAvailabilities();
    }

    /**
     *
     */
    private void setAvailabilities() {
        for (ShipType s : ShipType.values()) {
            if (s.getMinTechLevel() <= ss.getTechLevel().getValue()) {
                availableShips.put(s, s.getPrice());
            }
        }

        for (Upgrade g : Upgrade.values()) {
            if (g.getTechLevel() <= ss.getTechLevel().getValue()) {
                allUpgrades.put(g, g.getPrice());
            }
        }
    }

    /**
     * Returns list of available ships + their price.
     * @return
     */
    public HashMap getAvailableShips() {
        return availableShips;
    }

    /**
     * Returns list of available upgrades + their price.
     * @return
     */
    public HashMap getAllUpgrades() {
        return allUpgrades;
    }
}
