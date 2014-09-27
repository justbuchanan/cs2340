package spacetrader.models;

import java.util.Arrays;

/**
 * holds player's information
 * 
 * @author Bao
 */
public class Player {
    private String name;
    private int balance;
    private Ship ship;
    private int[] points;
    
    /**
     * Player constructor
     * Constructs a player with name and allocated skill points
     * array length must be 5
     * 
     * @param _name name of the player
     * @param _pointsArr array of skill points
     */
    public Player(String _name, int[] _pointsArr) {
        this.name = _name;
        this.points = Arrays.copyOf(_pointsArr, 5);
        this.ship = new Ship("Gnat");
        this.balance = 1000; //New player starts with 1000 credits
    }

//<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    /**
     * Returns player's name
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets player's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns player's skill points
     * @return
     */
    public int[] getPoints() {
        return points;
    }
    
    /**
     * Allocates player's skill points
     * @param points
     */
    public void setPoints(int[] points) {
        this.points = points;
    }
    /**
     * Gets the Player's balance
     * @return amount of cash Player currently has
     */
    public int getBalance() {
        return balance;
    }
    /**
     * Sets the Player's cash to a new amount
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }
    /**
     * Gets the Player's ship
     * @return
     */
    public Ship getShip() {
        return ship;
    }
    /**
     * Sets the Player's Ship
     * @param ship new vessel
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
//</editor-fold>
    
    /**
     * Returns player's info in a readable format
     * 
     * @return player's info
     */
    public String toString() {
        return String.format("Name:\t%s\nPilot:\t%s\nFighter:\t%s\nTrader:\t%s\nEngineer:\t%s\nInvestor:\t%s", name, points[0], points[1], points[2], points[3], points[4]);
    }
}
