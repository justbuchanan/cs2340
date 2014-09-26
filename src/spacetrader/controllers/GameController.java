package spacetrader.controllers;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import spacetrader.*;
import spacetrader.data.Item;
import spacetrader.data.TechLevel;
import spacetrader.models.*;
import spacetrader.models.Universe;

/**
 * Game screen controller
 *
 * @author Bao
 */
public class GameController implements Initializable {

    private Main application;
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE DECLARATIONS">
    @FXML Pane marketPane;
    @FXML Label coordinates;
    @FXML TabPane marketTabPane;
    @FXML Label buyPrice, buyQuantity, buyBalance, buyAfterBalance;
    @FXML Label sellPrice, sellQuantity, sellBalance, sellAfterBalance;
    @FXML ListView buyList, sellList;
    @FXML Label error, cargo;
    @FXML Pane buyPane, sellPane;
    private ObservableList<String> buyItems, sellItems;
//</editor-fold>
    
    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    private List<SolarSystem> solarSystems;
    private SolarSystem mySS;
    private Marketplace myMarket;
    
    @FXML private Canvas canvas;
    @FXML private ProgressBar fuelGauge;
    
    /**
     * Draws a planet on the canvas
     * 
     * @param gc graphics context
     * @param x x-coordinate of the center
     * @param y y-coordinate of the center
     */
    private void drawPlanet(GraphicsContext gc, int x, int y, int r) {
        gc.setFill(new RadialGradient(0, 0, 0.3, 0.3, 1, true,
               CycleMethod.REFLECT,
               new Stop(0.0, Color.WHITE),
               new Stop(1.0, Color.CADETBLUE)));
        gc.fillOval(x-r, y-r, r*2, r*2);
    }
    
    /**
     * Fills the canvas with planets
     */
    private void fillCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawPlanet(gc, 400, 300, 50);
    }
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE PANE OPEN/CLOSE HANDLERS">
    @FXML
    private void openMarketplace(ActionEvent event) {
        //Put marketplace generated code here...
        myMarket = mySS.getMP();
        
        //Display and set up marketPane
        marketPane.setVisible(true);
        resetBuyList();
        resetSellList();
        clearSellWindow();
        clearBuyWindow();
        displayCargo();
                
        marketTabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                error.setText("");
                resetBuyList();
                resetSellList();
                clearSellWindow();
                clearBuyWindow();
            }
            }
        );
       
    }
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
    }
    
    private void initBuyWindow() {
        List<String> marketListS = new ArrayList<String>(Item.values().length);
        for (int i = 0; i < Item.values().length; i++) {
            marketListS.add(null);
        }
        buyItems = FXCollections.observableArrayList(marketListS);
        buyList.setItems(buyItems);
        
        buyList.getSelectionModel().selectFirst();
        clearBuyWindow();
        buyList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                    error.setText("");
                    Item item = Item.values()[buyList.getSelectionModel().getSelectedIndex()];
                    if(myMarket.isBuyable(item)) {
                        buyPane.setVisible(true);
                        int price = myMarket.getBuyPrice(item);
                        buyPrice.setText(String.valueOf(price));
                        buyQuantity.setText("1");
                        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*price;
                        buyAfterBalance.setText(String.valueOf(newBalance));
                    } else {
                        buyPane.setVisible(false);
                    }
            }
        });
    }
    
    private void initSellWindow() {
        List<Integer> cargo = myPlayer.getShip().getCargo();
        List<String> cargoS = new ArrayList<String>(Item.values().length);
        for (int i = 0; i < Item.values().length; i++) {
            cargoS.add(null);
        }
        sellItems = FXCollections.observableArrayList(cargoS);
        sellList.setItems(sellItems);
        sellList.getSelectionModel().selectFirst();
        clearSellWindow();
        
        sellList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {                    
                    Item item = Item.values()[sellList.getSelectionModel().getSelectedIndex()];
                    if (myMarket.isSellable(item)) {
                        sellPane.setVisible(true);
                        int price = myMarket.getSellPrice(item);
                        sellPrice.setText(String.valueOf(price));
                        sellQuantity.setText("1");
                        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*price;
                        sellAfterBalance.setText(String.valueOf(newBalance));
                    } else {
                        sellPane.setVisible(false);
                    }
            }
        });
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BUY WINDOW HANDLERS">
    @FXML
    private void increaseBuyQuantity(ActionEvent event) {
        int q = Integer.parseInt(buyQuantity.getText());
        Item item = Item.values()[buyList.getSelectionModel().getSelectedIndex()];
        if (myMarket.getQuantity(item) > q) {
            q++;
            buyQuantity.setText(q + "");
            int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
            buyAfterBalance.setText(String.valueOf(newBalance));
        }
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
        Item item = Item.values()[buyList.getSelectionModel().getSelectedIndex()];
        int newBalance = Integer.parseInt(buyAfterBalance.getText());
        error.setText("");
        int quantity = Integer.parseInt(buyQuantity.getText());
        if (newBalance >= 0) {
            if (myPlayer.getShip().getCurrentCargo() + quantity <= myPlayer.getShip().getMaxCargo()) {
                myMarket.buy(item, quantity);
                myPlayer.getShip().addCargo(item, quantity);
                displayCargo();
                myPlayer.setBalance(newBalance);
                resetBuyList();
                clearBuyWindow();
            } else {
                error.setText("Your cargo bays are full");
            }
        } else {
            error.setText("You have insufficient funds");
        }
    }
    private void clearBuyWindow() {
        int price = myMarket.getBuyPrice(Item.values()[buyList.getSelectionModel().getSelectedIndex()]);
        buyPrice.setText(String.valueOf(price));
        buyQuantity.setText("1");
        buyBalance.setText(String.valueOf(myPlayer.getBalance()));        
        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
        buyAfterBalance.setText(String.valueOf(newBalance));
    }
    private void resetBuyList() {
        for (int i = 0; i < Item.values().length; i++) {
            if (myMarket.isBuyable(Item.values()[i])) {
                buyItems.set(i, Item.values()[i].getName() + " (Price: " + myMarket.getBuyPrice(Item.values()[i]) + " credits, Quantity: " + myMarket.getQuantity(Item.values()[i])+")");
            } else {
                buyItems.set(i, Item.values()[i].getName() + " is not available");
            }
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
        Item item = Item.values()[itemIndex];
        int quantity = Integer.parseInt(sellQuantity.getText());
        if (cargo.get(itemIndex) >= quantity) {
            myMarket.sell(item, quantity);
            int newBalance = Integer.parseInt(sellAfterBalance.getText());
            myPlayer.getShip().removeCargo(Item.values()[itemIndex], quantity);
            displayCargo();
            myPlayer.setBalance(newBalance);
            resetSellList();
            clearSellWindow();
        }
    }
    private void clearSellWindow() {        
        int price = myMarket.getSellPrice(Item.values()[sellList.getSelectionModel().getSelectedIndex()]);
        sellPrice.setText(String.valueOf(price));
        sellQuantity.setText("1");
        sellBalance.setText(String.valueOf(myPlayer.getBalance()));        
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));      
    }
    private void resetSellList() {
        List<Integer> cargo = myPlayer.getShip().getCargo();
        for (int i = 0; i < Item.values().length; i++) {
            Item item = Item.values()[i];
            if (myMarket.isSellable(item)) {
                sellItems.set(i, cargo.get(i) + " " + item.getName() + " (Price: " + myMarket.getSellPrice(item) + " credits)");
            } else {
                sellItems.set(i, "Cannot sell " + item.getName() + " on this solar system");
            }
        }
        sellList.setItems(sellItems);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="CARGO HELPER METHODS">
    private void displayCargo() {
        cargo.setText("Cargo: " + myPlayer.getShip().getCurrentCargo() + "/" + myPlayer.getShip().getMaxCargo());
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="MAIN WINDOW COMPONENTS">
    @FXML
    private void exit(ActionEvent e) {
        application.showWelcome();
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
        //mySS.setTechLevel(TechLevel.MEDIEVAL);
        
        fillCanvas();
        
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
