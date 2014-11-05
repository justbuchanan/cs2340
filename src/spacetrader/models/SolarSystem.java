package spacetrader.models;

import javafx.scene.paint.Color;
import spacetrader.data.Item;
import spacetrader.data.Resource;
import spacetrader.data.TechLevel;

import java.util.HashMap;

/**
 * @author Michael Represents a single solar system. For now, this means a
 *         planet.
 */
public class SolarSystem {

    private String name;
    private int x;
    private int y;
    private Resource resource;
    private TechLevel techLevel;
    private HashMap<Item, Integer> availableItems;
    private Marketplace mp;
    private Color primaryColor;

    final int QUANTITIES[] = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30};

    /**
     * Creates a solar system with some initial amount of resources.
     */
    public SolarSystem() {

        availableItems = new HashMap<>();
        for (int i = 0; i < QUANTITIES.length; i++) {
            availableItems.put(Item.values()[i], QUANTITIES[i]);
        }
    }

    /**
     * Gets name of this SolarSystem.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of this SolarSystem.
     *
     * @param _name
     */
    public void setName(String _name) {
        name = _name;
    }

    /**
     * gets x coordinate.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * sets x coordinate.
     *
     * @param _x
     */
    public void setX(int _x) {
        this.x = _x;
    }

    /**
     * gets y coordinate.
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * sets y coordinate.
     *
     * @param _y
     */
    public void setY(int _y) {
        this.y = _y;
    }

    /**
     * gets resource.
     *
     * @return
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * sets resource.
     *
     * @param _resource
     */
    public void setResource(Resource _resource) {
        resource = _resource;
        primaryColor = Color.WHITE;
        switch (resource) {
            case NOSPECIALRESOURCES:
                primaryColor = Color.color(0.9, 0.9, 0.9);
                break;
            case MINERALRICH:
                primaryColor = Color.color(0.97, 0.9, 0.39);
                break;
            case MINERALPOOR:
                primaryColor = Color.color(0.9, 0.9, 0.9);
                break;
            case DESERT:
                primaryColor = Color.color(1, 0.6, 0.0);
                break;
            case LOTSOFWATER:
                primaryColor = Color.color(0.0, 0.39, 0.93);
                break;
            case RICHSOIL:
                primaryColor = Color.color(0.51, 0.19, 0.0);
                break;
            case POORSOIL:
                primaryColor = Color.color(0.42, 0.25, 0.15);
                break;
            case RICHFAUNA:
                primaryColor = Color.color(0.0, 0.77, 0.0);
                break;
            case LIFELESS:
                primaryColor = Color.color(0.3, 0.3, 0.3);
                break;
            case WEIRDMUSHROOMS:
                primaryColor = Color.color(0.46, 0.20, 0.0);
                break;
            case LOTSOFHERBS:
                primaryColor = Color.color(0.17, 0.95, 0.0);
                break;
            case ARTISTIC:
                primaryColor = Color.color(0.57, 0.0, 0.89);
                break;
            case WARLIKE:
                primaryColor = Color.color(1, 0.0, 0.0);
        }
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    /**
     * gets technology level.
     *
     * @return
     */
    public TechLevel getTechLevel() {
        return techLevel;
    }

    /**
     * sets technology level.
     *
     * @param _techLevel
     */
    public void setTechLevel(TechLevel _techLevel) {
        techLevel = _techLevel;
    }

    /**
     * String representation of the SolarSystem.
     *
     * @return descriptive String
     */
    @Override
    public String toString() {
        return name + "(" + x + "," + y + ") " + resource.name() + " "
                + techLevel.name();
    }

    /**
     * Gets a map of items available on the solar system.
     *
     * @return
     */
    public HashMap<Item, Integer> getAvailableItems() {
        return availableItems;
    }

    /**
     * Sets what items are available on the solar systems.
     *
     * @param availableItems
     */
    public void setAvailableItems(HashMap<Item, Integer> availableItems) {
        this.availableItems = availableItems;
    }

    /**
     * Gets the marketplace of the solar system.
     *
     * @return
     */
    public Marketplace getMP() {
        return mp;
    }

    /**
     * Sets the marketplace of the solar system.
     *
     * @return
     */
    public Marketplace setMP() {
        return mp = new Marketplace(this);
    }
}
