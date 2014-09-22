package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    @FXML TabPane marketTabPane;
    @FXML Label buyPrice, buyQuantity, buyBalance, buyAfterBalance;
    @FXML Label sellPrice, sellQuantity, sellBalance, sellAfterBalance;
    @FXML ListView buyList, sellList;
//</editor-fold>
    
    private Main application;
    
    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    private List<SolarSystem> solarSystems;
    private SolarSystem mySS;
    private Marketplace myMarket;
    private ObservableList<String> buyItems, sellItems;
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE PANE OPEN/CLOSE HANDLERS">
    @FXML
    private void openMarketplace(ActionEvent event) {
        //Put marketplace generated code here...
        myMarket = new Marketplace(mySS);
        
        //Display and set up marketPane
        marketPane.setVisible(true);
        resetBuyList();
        resetSellList();
        clearSellWindow();
        clearBuyWindow();
                
        marketTabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                resetBuyList();
                resetSellList();
                clearSellWindow();
                clearBuyWindow();
            }
            }
        );
        //List<Integer> marketList = marketList.getPrice();
       
    }
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
    }
    
    private void initBuyWindow() {
        List<String> marketListS = new ArrayList<String>(Item.values().length);
        for (int i = 0; i < Item.values().length; i++) {
            marketListS.add(Item.values()[i].getName() + " (" + myMarket.getPrice(Item.values()[i]) + " credits)");
        }
        buyItems = FXCollections.observableArrayList(marketListS);
        buyList.setItems(buyItems);
        
        buyList.getSelectionModel().selectFirst();
        clearBuyWindow();
        buyList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        int price = myMarket.getPrice(Item.values()[buyList.getSelectionModel().getSelectedIndex()]);
                        buyPrice.setText(String.valueOf(price));
                        buyQuantity.setText("1");
                        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*price;
                        buyAfterBalance.setText(String.valueOf(newBalance));
            }
        });
    }
    
    private void initSellWindow() {
        List<Integer> cargo = myPlayer.getShip().getCargo();
        //Create cargo list
        List<String> cargoS = new ArrayList<String>(Item.values().length);
        for (int i = 0; i < Item.values().length; i++) {
            cargoS.add(cargo.get(i) + " " + Item.values()[i].getName());
        }
        sellItems = FXCollections.observableArrayList(cargoS);
        sellList.setItems(sellItems);
        sellList.getSelectionModel().selectFirst();
        clearSellWindow();
        
        sellList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        int price = myMarket.getPrice(Item.values()[sellList.getSelectionModel().getSelectedIndex()]);
                        sellPrice.setText(String.valueOf(price));
                        sellQuantity.setText("1");
                        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*price;
                        sellAfterBalance.setText(String.valueOf(newBalance));
            }
        });
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BUY WINDOW HANDLERS">
    @FXML
    private void increaseBuyQuantity(ActionEvent event) {
        int q = Integer.parseInt(buyQuantity.getText());
        q++;
        buyQuantity.setText(q + "");
        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
        buyAfterBalance.setText(String.valueOf(newBalance));
    }
    @FXML
    private void decreaseBuyQuantity(ActionEvent event) {
        if (Integer.parseInt(buyQuantity.getText()) != 1) {
            int q = Integer.parseInt(buyQuantity.getText());
            q--;
            buyQuantity.setText(q + "");
            int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
            buyAfterBalance.setText(String.valueOf(newBalance));
        }
    }
    @FXML
    private void buy(ActionEvent event) {
        int newBalance = Integer.parseInt(buyAfterBalance.getText());
        if (newBalance >= 0) {
            int itemIndex = buyList.getSelectionModel().getSelectedIndex();
            int quantity = Integer.parseInt(buyQuantity.getText());
            //List<Integer> cargo = myPlayer.getShip().getCargo();
            myPlayer.getShip().addCargo(Item.values()[itemIndex], quantity);
            myPlayer.setBalance(newBalance);
            clearBuyWindow();
        }
    }
    private void clearBuyWindow() {
        int price = myMarket.getPrice(Item.values()[buyList.getSelectionModel().getSelectedIndex()]);
        buyPrice.setText(String.valueOf(price));
        buyQuantity.setText("1");
        buyBalance.setText(String.valueOf(myPlayer.getBalance()));        
        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
        buyAfterBalance.setText(String.valueOf(newBalance));
    }
    private void resetBuyList() {
        for (int i = 0; i < Item.values().length; i++) {
            buyItems.set(i, Item.values()[i].getName() + " (" + myMarket.getPrice(Item.values()[i]) + " credits)");
        }
        buyList.setItems(buyItems);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="SELL WINDOW HANDLERS">
    @FXML
    private void increaseSellQuantity(ActionEvent event) {
        int q = Integer.parseInt(sellQuantity.getText());
        q++;
        sellQuantity.setText(q + "");
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));
    }
    @FXML
    private void decreaseSellQuantity(ActionEvent event) {
        if (Integer.parseInt(sellQuantity.getText()) != 1) {
            int q = Integer.parseInt(sellQuantity.getText());
            q--;
            sellQuantity.setText(q + "");
        }
    }
    @FXML
    private void sell(ActionEvent event) {        
        List<Integer> cargo = myPlayer.getShip().getCargo();
        int itemIndex = sellList.getSelectionModel().getSelectedIndex();
        int quantity = Integer.parseInt(sellQuantity.getText());
        if (cargo.get(itemIndex) >= quantity) {
            int newBalance = Integer.parseInt(sellAfterBalance.getText());
            myPlayer.getShip().removeCargo(Item.values()[itemIndex], quantity);
            myPlayer.setBalance(newBalance);
            resetSellList();
            clearSellWindow();
        }
    }
    private void clearSellWindow() {        
        int price = myMarket.getPrice(Item.values()[sellList.getSelectionModel().getSelectedIndex()]);
        sellPrice.setText(String.valueOf(price));
        sellQuantity.setText("1");
        sellBalance.setText(String.valueOf(myPlayer.getBalance()));        
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));      
    }
    private void resetSellList() {
        List<Integer> cargo = myPlayer.getShip().getCargo();
        for (int i = 0; i < Item.values().length; i++) {
            sellItems.set(i, cargo.get(i) + " " + Item.values()[i].getName());
        }
        sellList.setItems(sellItems);
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
                + "Resource: " + mySS.getResource() + "\n"
                + "Tech Level: " + mySS.getTechLevel()
        );
        
        //MARKETPLACE INITIALIZATION
        myMarket = new Marketplace(mySS);
        initBuyWindow();
        initSellWindow();
    }
//</editor-fold>
}
