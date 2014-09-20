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
