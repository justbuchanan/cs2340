package spacetrader;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * Game screen controller
 *
 * @author Bao
 */
public class GameController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML DECLARATIONS">
    @FXML Pane marketPane;
    @FXML Label coordinates;
    @FXML Label buyQuantity, currentBalance, afterBalance;
    @FXML ListView list;
//</editor-fold>
    
    private Main application;
    
    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    private List<SolarSystem> solarSystems;
    private SolarSystem mySS;
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE PANE OPEN/CLOSE HANDLERS">
    @FXML
    private void openMarketplace(ActionEvent event) {
        //Put marketplace generated code here...
        //Marketplace myMarket = new Marketplace(mySS);
        
        //Display and set up marketPane
        marketPane.setVisible(true);
        buyQuantity.setText("1");
        currentBalance.setText(String.valueOf(myPlayer.getBalance()));
        //Display ItemList
        ObservableList<String> items = FXCollections.observableArrayList (
            "Water $30", "Furs $250", "Food", "Ore", "Games", "Firearms", "Medicine", "Machines", "Narcotics", "Robots");
        list.setItems(items);
        /*
        list.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        label.setText(new_val);
                        label.setTextFill(Color.web(new_val));
            }
        });*/
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
        Random rand = new Random();
        
        myPlayer = p;
        myUniverse = u;
        
        //Randomly select a solar system as starting point
        solarSystems = myUniverse.getSolarSystems();
        mySS = solarSystems.get(rand.nextInt(solarSystems.size()));
        
        coordinates.setText("Coordinates: (" + mySS.getX() + ", " + mySS.getY() + ")\n"
                + "Solar System: " + mySS.getName() + "\n"
                + "Tech Level: " + mySS.getResource() + "\n"
                + "Government: " + mySS.getTechLevel()
        );
    }
//</editor-fold>
}
