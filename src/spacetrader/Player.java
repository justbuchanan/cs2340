/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.Arrays;

/**
 *
 * @author Zephyr
 */
public class Player {
    private String name;
    private int[] points;
    
    /**
     * Player constructor
     * 
     * @param n
     * @param p
     */
    public Player(String n, int[] p) {
        this.name = n;
        this.points = Arrays.copyOf(p,5);
    }

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
    
    /**
     * Returns player's info in a readable format
     * 
     * @return player's info
     */
    public String toString() {
        return String.format("Name:\t%s\nPilot:\t%s\nFighter:\t%s\nTrader:\t%s\nEngineer:\t%s\nInvestor:\t%s", name, points[0], points[1], points[2], points[3], points[4]);
    }
}
