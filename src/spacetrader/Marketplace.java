/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.ArrayList;
import java.util.List;

/**
 * Player comes to marketplace to buys and sells goods
 * 
 * @author Bao
 */
public class Marketplace {
    public List<LineItem> myItemList;
    
    public Marketplace(SolarSystem mySS) {
        myItemList = new ArrayList<>();
        //generator here
    }
}
