package spacetrader.models;

import java.util.HashMap;
import spacetrader.data.Item;
import spacetrader.data.TechLevel;
import spacetrader.data.Resource;

/**
 * @author Michael
 * Represents a single solar system. For now, this means a planet.
 */
public class SolarSystem {
	private String name;
	private int x;
	private int y;
	private Resource resource;
	private TechLevel techLevel;
	private HashMap<Item, Integer> availableItems;
	private Marketplace mp;
        
    /**
     * Creates a solar system with some initial amount of resources
     */
    public SolarSystem() {
        int quantity[] = {30,30,30,30,30,30,30,30,30,30};
        availableItems = new HashMap<>();
        for (int i = 0; i < quantity.length; i++) {
            availableItems.put(Item.values()[i], quantity[i]);
        }
    }
	
    /**
     * Gets name of this SolarSystem
     * @return name
     */
    public String getName() { return name; }

    /**
     * Sets name of this SolarSystem
     * @param _name
     */
    public void setName(String _name) { name = _name; }

    /**
     * gets x coordinate
     * @return
     */
    public int getX() { return x; }

    /**
     * sets x coordinate
     * @param _x
     */
    public void setX(int _x) { this.x = _x; }

    /**
     * gets y coordinate
     * @return
     */
    public int getY() { return y; }

    /**
     * sets y coordinate
     * @param _y
     */
    public void setY(int _y) { this.y = _y; }

    /**
     * gets resource
     * @return
     */
    public Resource getResource() { return resource; }

    /**
     * sets resource
     * @param _resource
     */
    public void setResource(Resource _resource) { resource = _resource; }

    /**
     * gets technology level
     * @return
     */
    public TechLevel getTechLevel() { return techLevel; }

    /**
     * sets technology level
     * @param _techLevel
     */
    public void setTechLevel(TechLevel _techLevel) { techLevel = _techLevel; }
	/**
	 * String representation of the SolarSystem
	 * @return descriptive String
	 */
    @Override
    public String toString() {
            return name+"("+x+","+y+") "+resource.name()+" "+techLevel.name();
    }

    /**
     * Gets a map of items available on the solar system
     * @return 
     */
    public HashMap<Item, Integer> getAvailableItems() {
        return availableItems;
    }

    /**
     * Sets what items are available on the solar systems
     * @param availableItems 
     */
    public void setAvailableItems(HashMap<Item, Integer> availableItems) {
        this.availableItems = availableItems;
    }

    /**
     * Gets the marketplace of the solar system
     * @return 
     */
    public Marketplace getMP() {
        return mp;
    }
    
    /**
     * Sets the marketplace of the solar system
     * @return 
     */
    public Marketplace setMP() {
        return mp = new Marketplace(this);
    }
}
