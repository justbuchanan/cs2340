package spacetrader.data;

/**
 * New and improved upgrade system.
 *
 * @author Griffith
 */
public enum Upgrade {
    
    INVENTORY("5 Extra Cargo Slots", 5000, 1, UPGRADE_TYPE.Gadget),
    NAV("Navigation System", 3000, 5, UPGRADE_TYPE.Gadget),
    AUTOREPAIR("Auto-Repair", 10000, 7, UPGRADE_TYPE.Gadget),
    TARGET("Targetting System", 7000, 6, UPGRADE_TYPE.Gadget),
    CLOAK("Cloaking Device", 2000, 7, UPGRADE_TYPE.Gadget),
    ENERGY("Energy Shield", 3000, 6, UPGRADE_TYPE.Shield),
    REFLECTIVE("Reflective Shield", 5000, 7, UPGRADE_TYPE.Shield),
    PULSE("Pulse Laser", 1000, 5, UPGRADE_TYPE.Weapon),
    BEAM("Beam Laser", 2500, 6, UPGRADE_TYPE.Weapon),
    MILITARY("Military Laser", 5000, 7, UPGRADE_TYPE.Weapon);

    private String name;
    private int price;
    private int techLevel;
    private UPGRADE_TYPE type;
    public enum UPGRADE_TYPE{Weapon, Shield, Gadget};

    private Upgrade(String name, int price, int techLevel, UPGRADE_TYPE type) {
        this.name = name;
        this.price = price;
        this.techLevel = techLevel;
        this.type = type;
    }
    
    public int getTechLevel() {
        return techLevel;
    }
    
    public int getPrice() {
        return price;
    }
    
    public UPGRADE_TYPE getType() {
        return type;
    }
    
    public String typeToString() {
        if (type == UPGRADE_TYPE.Weapon) {
            return "Weapon";
        } else if (type == UPGRADE_TYPE.Shield) {
            return "Shield";
        } else if (type == UPGRADE_TYPE.Gadget) {
            return "Gadget";
        }
        return " ";
    }

}

