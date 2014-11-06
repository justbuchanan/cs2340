package spacetrader.models;

import java.util.HashMap;
import spacetrader.data.ship_upgrades.Shield;
import spacetrader.data.ship_upgrades.Weapon;
import spacetrader.data.ship_upgrades.Gadget;
import spacetrader.data.ShipType;

/**
 * A little something to make all of our lives WAY easier in terms of handling
 * upgrades and ship availability.
 *
 * @author Griffith
 */
public class ShipYard {

    private SolarSystem ss;
    private HashMap<ShipType, Integer> availableShips;
    private HashMap<Shield, Integer> availableShield;
    private HashMap<Weapon, Integer> availableWeapon;
    private HashMap<Gadget, Integer> availableGadget;

    public ShipYard(SolarSystem ss) {
        this.ss = ss;
        availableShips = new HashMap<>();
        availableShield = new HashMap<>();
        availableWeapon = new HashMap<>();
        availableGadget = new HashMap<>();
        setAvailabilities();
    }

    private void setAvailabilities() {
        for (ShipType s : ShipType.values()) {
            if (s.getMinTechLevel() >= ss.getTechLevel().getValue()) {
                availableShips.put(s, s.getPrice());
            }
            
        }
        for (Shield sh : Shield.values()) {
            if (sh.getTechLevel() >= ss.getTechLevel().getValue()) {
                availableShield.put(sh, sh.getPrice());
            }
        }
        for (Weapon w : Weapon.values()) {
            if (w.getTechLevel() >= ss.getTechLevel().getValue()) {
                availableWeapon.put(w, w.getPrice());
            }
        }
        for (Gadget g : Gadget.values()) {
            if (g.getTechLevel() >= ss.getTechLevel().getValue()) {
                availableGadget.put(g, g.getPrice());
            }
        }
    }

    public HashMap getAvailableShips() {
        return availableShips;
    }

    public HashMap getAvailableShield() {
        return availableShield;
    }

    public HashMap getAvaiableWeapon() {
        return availableWeapon;
    }

    public HashMap getAvailableGadget() {
        return availableGadget;
    }

}
