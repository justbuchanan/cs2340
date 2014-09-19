package spacetrader;

/**
 * @author Michael
 *
 */
public class SolarSystem {
	private String name;
	private int x;
	private int y;
	private Resource resource;
	private TechLevel techLevel;
	
	public String getName() { return name; }
	public void setName(String _name) { name = _name; }
	public int getX() { return x; }
	public void setX(int _x) { this.x = _x; }
	public int getY() { return y; }
	public void setY(int _y) { this.y = _y; }
	public Resource getResource() { return resource; }
	public void setResource(Resource _resource) { resource = _resource; }
	public TechLevel getTechLevel() { return techLevel; }
	public void setTechLevel(TechLevel _techLevel) { techLevel = _techLevel; }
	
	@Override
	public String toString() {
		return name+"("+x+","+y+") "+resource.name()+" "+techLevel.name();
	}
	
}
