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
import javafx.scene.layout.Pane;

/**
 * Game screen controller
 *
 * @author Bao
 */
public class GameController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML DECLARATIONS">
    @FXML Pane marketPane;
//</editor-fold>
    
    private Main application;
    
    //Player and universe are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    
//<editor-fold defaultstate="collapsed" desc="BUY WINDOW HANDLERS">
    @FXML
    private void openMarketplace(ActionEvent event) {
        marketPane.setVisible(true);
    }
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
    }
    @FXML
    private void increaseBuyQuantity(ActionEvent event) {
        //...
    }
    @FXML
    private void decreaseBuyQuantity(ActionEvent event) {
        //...
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
