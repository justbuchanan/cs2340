/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.HashMap;
import java.util.Map;

/**
 * holds ship information
 * 
 * @author Bao
 */
public class Ship {
    private Map<String, Integer> cargo;
    private String type;
    
    public Ship(String type) {
        cargo = new HashMap<>();
        this.type = type;
    }
    
}
