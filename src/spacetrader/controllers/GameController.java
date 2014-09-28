package spacetrader.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.animation.FillTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import spacetrader.*;
import spacetrader.data.Item;
import spacetrader.data.Resource;
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
    
    @FXML private Pane mapPane;
    @FXML private Canvas mapCanvas;
    @FXML private TableView<SolarSystem> ssTable;
    @FXML private Label flightDistance, fuelLeft, fuelRequired;
    
    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    private List<SolarSystem> solarSystems;
    private SolarSystem mySS;
    private Marketplace myMarket;
    
    @FXML Parent root;
    @FXML private Canvas canvas;
    @FXML private ProgressBar fuelGauge;
    @FXML private Pane topPane;
    
//<editor-fold defaultstate="collapsed" desc="MAIN CANVAS DRAWING">
    /**
     * Draws a planet on the canvas
     *
     * @param gc graphics context
     * @param x x-coordinate of the center
     * @param y y-coordinate of the center
     * @param r radius
     */
    private void drawPlanet(GraphicsContext gc, int x, int y, int r) {
        gc.setFill(Color.BLACK);
        r = (int)(r*(1 + mySS.getTechLevel().getValue()/5.0));
        int d = 2*r;
        System.out.println(mySS.getTechLevel().getValue());
        gc.fillOval(x-r, y-r, d, d);
        gc.setFill(new RadialGradient(0, 0, 0.3, 0.3, 1, true,
                CycleMethod.REFLECT,
                new Stop(0.0, mySS.getPrimaryColor()),
                new Stop(1.0, mySS.getSecondaryColor())));
        gc.fillOval(x-r, y-r, d, d);
    }
    
    /**
     * Fills the canvas with planets
     */
    private void fillCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawPlanet(gc, 400, 300, 50);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="MARKETPLACE">
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
    /**
     * Stops the marketplace from being draw
     * @param event
     */
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
    }
    /**
     * Initializes the purchase screen
     */
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
    /**
     * Initializes the sell screen
     */
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
    /**
     * Increases the quantity to purchase by 1
     * @param event
     */
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
    /**
     * Decreases the quantity to purchase by 1
     * @param event
     */
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
    /**
     * Attempts to execute a purchase of goods.
     * @param event
     */
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
    /**
     * Clears the quantity from the purchase screen
     */
    private void clearBuyWindow() {
        int price = myMarket.getBuyPrice(Item.values()[buyList.getSelectionModel().getSelectedIndex()]);
        buyPrice.setText(String.valueOf(price));
        buyQuantity.setText("1");
        buyBalance.setText(String.valueOf(myPlayer.getBalance()));
        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText())*Integer.parseInt(buyPrice.getText());
        buyAfterBalance.setText(String.valueOf(newBalance));
    }
    /**
     * Re-initializes the purchase screen
     */
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
    /**
     * Increases the number of goods to sell by 1
     * @param event
     */
    @FXML
    private void increaseSellQuantity(ActionEvent event) {
        int q = Integer.parseInt(sellQuantity.getText());
        q++;
        sellQuantity.setText(q + "");
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));
    }
    /**
     * Decreases the number of goods to sell by 1
     * @param event
     */
    @FXML
    private void decreaseSellQuantity(ActionEvent event) {
        if (Integer.parseInt(sellQuantity.getText()) != 1) {
            int q = Integer.parseInt(sellQuantity.getText());
            q--;
            sellQuantity.setText(q + "");
        }
    }
    /**
     * Attempts to sell cargo
     * @param event
     */
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
    /**
     * resets the sell quantity of the purchase screen
     */
    private void clearSellWindow() {
        int price = myMarket.getSellPrice(Item.values()[sellList.getSelectionModel().getSelectedIndex()]);
        sellPrice.setText(String.valueOf(price));
        sellQuantity.setText("1");
        sellBalance.setText(String.valueOf(myPlayer.getBalance()));
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText())*Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));
    }
    /**
     * Recalculates and resets the sell list's items
     */
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
    /**
     * Sets cargo text screen
     */
    private void displayCargo() {
        cargo.setText("Cargo: " + myPlayer.getShip().getCurrentCargo() + "/" + myPlayer.getShip().getMaxCargo());
    }
    /**
     * Opens the map
     * @param event
     */
    @FXML
    private void openMap(ActionEvent event) {
        mapPane.setVisible(true);
        drawMap();
        fuelLeft.setText(String.valueOf(myPlayer.getShip().getFuelReading()));
        createSSTable();
        //ssTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> showSelectedSS(newValue));
        ssTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SolarSystem>() {
        public void changed(ObservableValue<? extends SolarSystem> ov, 
            SolarSystem oldval, SolarSystem newValue) {
            showSelectedSS(newValue);
        }});
    }
    /**
     * Closes the map
     * @param event
     */
    @FXML
    private void closeMap(ActionEvent event) {
        mapPane.setVisible(false);
    }
    /**
     * Creates SolarSystem table
     */
    private void createSSTable() {
        ObservableList<SolarSystem> universe = FXCollections.observableArrayList(myUniverse.getSolarSystems());
        ssTable.setItems(universe);
        
        TableColumn<SolarSystem, String> nameCol = new TableColumn<>("System");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn<SolarSystem, Integer> xCol = new TableColumn<>("x");
        xCol.setCellValueFactory(new PropertyValueFactory("x"));
        TableColumn<SolarSystem, Integer> yCol = new TableColumn<>("y");
        yCol.setCellValueFactory(new PropertyValueFactory("y"));
        TableColumn<SolarSystem, Resource> resourceCol = new TableColumn<>("Resource");
        resourceCol.setCellValueFactory(new PropertyValueFactory("resource"));
        TableColumn<SolarSystem, TechLevel> techLevelCol = new TableColumn<>("Tech Level");
        techLevelCol.setCellValueFactory(new PropertyValueFactory("techLevel"));
        
        ssTable.getColumns().setAll(nameCol, xCol, yCol, resourceCol, techLevelCol);
    }
    /**
     * Draws the selected SolarSystem
     * @param ss
     */
    private void showSelectedSS(SolarSystem ss) {
        if (ss != null) {
            drawMap();
            GraphicsContext gc = mapCanvas.getGraphicsContext2D();
            gc.setFill(Color.RED);
            gc.fillOval(ss.getX()*2-2, ss.getY()*2-2, 4, 4);
        }
    }
    /**
     * Draws the Universe
     */
    private void drawMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        for (SolarSystem ss: myUniverse.getSolarSystems()) {
            int x = ss.getX();
            int y = ss.getY();
            gc.setFill(Color.WHITE);
            gc.fillOval(x*2-1, y*2-1, 2, 2);
        }
        gc.setFill(Color.AQUA);
        gc.fillOval(mySS.getX()*2-2, mySS.getY()*2-2, 4, 4);
    }
    /**
     * Occurs when jump drive is activated
     * @param event
     */
    @FXML
    private void activateJumpDrive(ActionEvent event) {
        enterLightTunnel();
    }
    /**
     * Occurs when player travels to new system
     */
    private void enterLightTunnel() {
        topPane.setVisible(true);
        Label l1 = new Label("Travelling at warp speed...");
        Rectangle rect = new Rectangle(0, 0, 800, 600);
        topPane.getChildren().add(rect);
        topPane.getChildren().add(l1);
        
        FillTransition transition = new FillTransition(Duration.millis(3000), rect, Color.BLACK, Color.WHITE);
        transition.setCycleCount(4);
        transition.setAutoReverse(true);
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                topPane.setVisible(false);
                mapPane.setVisible(false);
                topPane.getChildren().clear();
            }
        }
        );
    }
    /**
     * Opens the universe map
     */
    @FXML
    private void openInteractiveMap() {
        ScrollPane sp = new ScrollPane();
        Canvas c = new Canvas();
        int size = 1500;
        c.setHeight(size);
        c.setWidth(size);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, c.getHeight(), c.getWidth());
        gc.setFill(Color.WHITE);
        int r = 10;
        int ratio = (int) c.getHeight()/myUniverse.getHeight();
        
        gc.setStroke(Color.gray(0.2));
        for (int x = 0; x < size; x = x + 50) {
            gc.strokeLine(x, 0, x, size);
            gc.strokeLine(0, x, size, x);
        }
        
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int r1 = rand.nextInt(3)+1;
            gc.fillOval(rand.nextInt(size), rand.nextInt(size), r1, r1);
        }
        
        for (SolarSystem ss: myUniverse.getSolarSystems()) {
            gc.fillOval(ratio*ss.getX()-r, ratio*ss.getY()-r, 2*r, 2*r);
        }
        
        //Set canvas onClick event
        c.setOnMouseClicked(new EventHandler<MouseEvent>(){
          @Override
          public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    closeInteractiveMap();
                }
            }
          }
 
        });
        sp.setPrefSize(800, 600);
        sp.setPannable(true);
        sp.setContent(c);
        topPane.getChildren().add(sp);
        topPane.setVisible(true);
    }
    
    private void closeInteractiveMap() {
        topPane.getChildren().clear();
        topPane.setVisible(false);
    }
    /**
     * Closes the GameController, goes to title screen
     * @param e
     */
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
        mapPane.setVisible(false);
        topPane.setVisible(false);
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
