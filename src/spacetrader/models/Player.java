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
     * constructs a player with name and allocated skill points
     * array length must be 5
     * 
     * @param n name of the player
     * @param p array of skill points
     */
    public Player(String n, int[] p) {
        this.name = n;
        this.points = Arrays.copyOf(p,5);
        this.ship = new Ship("Gnat");
        this.balance = 1000; //New player starts with 1000 credits
    }

//<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    /**
     * Returns player's name
     *
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets player's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns player's skill points
     *
     * @return
     */
    public int[] getPoints() {
        return points;
    }
    
    /**
     * Allocates player's skill points
     *
     * @param points
     */
    public void setPoints(int[] points) {
        this.points = points;
    }
        
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Ship getShip() {
        return ship;
    }

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
