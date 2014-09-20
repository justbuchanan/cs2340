/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Game screen controller
 *
 * @author Bao
 */
public class GameController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML DECLARATIONS">
    @FXML Pane marketPane;
    @FXML Label buyQuantity, currentBalance, afterBalance;
//</editor-fold>
    
    private Main application;
    
    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE PANE OPEN/CLOSE HANDLERS">
    @FXML
    private void openMarketplace(ActionEvent event) {
        //Put marketplace generated code here...
        //Marketplace myMarket = new Marketplace(SolarSystem);
        
        //Display and set up marketPane
        marketPane.setVisible(true);
        buyQuantity.setText("1");
        currentBalance.setText(String.valueOf(myPlayer.getBalance()));
        //Display ItemList in the table
    }
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BUY WINDOW HANDLERS">
    @FXML
    private void increaseBuyQuantity(ActionEvent event) {
        int q = Integer.parseInt(buyQuantity.getText());
        q++;
        buyQuantity.setText(q + "");
    }
    @FXML
    private void decreaseBuyQuantity(ActionEvent event) {
        if (Integer.parseInt(buyQuantity.getText()) != 1) {
            int q = Integer.parseInt(buyQuantity.getText());
            q--;
            buyQuantity.setText(q + "");
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="SELL WINDOW HANDLERS">
    @FXML
    private void handleSellQuantityUp(ActionEvent event) {
        //...
    }
    @FXML
    private void handleSellQuantityDown(ActionEvent event) {
        //...
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="CONTROLLER INITIALIZATION">
    /**
     * Links to main application
     * @param application
     */
    public void setApp(Main application) {
        this.application = application;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        //Hide all popup panes
        marketPane.setVisible(false);
    }
    
    /**
     * Gets configuration info
     * 
     * @param p Player
     * @param u Universe
     */
    public void config(Player p, Universe u) {
        myPlayer = p;
        myUniverse = u;
    }
//</editor-fold>
}
