package spacetrader;

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
	
    /**
     * gets name
     * @return name
     */
    public String getName() { return name; }

    /**
     * sets name
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
	
    @Override
    public String toString() {
            return name+"("+x+","+y+") "+resource.name()+" "+techLevel.name();
    }
	
}
