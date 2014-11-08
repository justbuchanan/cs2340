package spacetrader.controllers;

import javafx.animation.FillTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;
import spacetrader.Main;
import spacetrader.api.MessageAPI;
import spacetrader.data.*;
import spacetrader.data.Upgrade;
import spacetrader.data.random_events.PirateRaid;
import spacetrader.data.random_events.PoliceSearch;
import spacetrader.data.random_events.TreasureChest;
import spacetrader.data.random_events.WaterLeak;
import spacetrader.database.DbMethods;
import spacetrader.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import spacetrader.controllers.market.AbstractCommand;
import spacetrader.controllers.market.BuyItemCommand;
import spacetrader.controllers.market.CommandProcessor;

/**
 * Game screen controller
 *
 * @author Bao
 */
public class GameController implements Initializable {

    private Main application;
    private CommandProcessor cp  = AbstractCommand.INSTANCE;

    //<editor-fold defaultstate="collapsed" desc="MARKETPLACE DECLARATIONS">
    @FXML
    Pane marketPane;
    @FXML
    Label coordinates, shipInfo, fuelGaugeLabel;
    @FXML
    TabPane marketTabPane;
    @FXML
    Label buyPrice, buyQuantity, buyBalance, buyAfterBalance;
    @FXML
    Label sellPrice, sellQuantity, sellBalance, sellAfterBalance;
    @FXML
    ListView buyList, sellList;
    @FXML
    Label error, cargo;
    @FXML
    Pane buyPane, sellPane;
    private ObservableList<String> buyItems, sellItems;
//</editor-fold>

    @FXML
    private Pane mapPane, shipyardPane, upgradePane;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private TableView<SolarSystem> ssTable;
    @FXML
    private TableView<ShipType> shipyardTable;
    @FXML
    private TableView<Upgrade> upgradeTable;
    @FXML
    private Label syBalance, syShipWorth, syCost, syNewBalance;
    @FXML
    private Label flightDistance, fuelLeft, fuelRequired, fromSS, toSS;
    @FXML
    private Label shipCost, fuelCost, cargoSize, minTechLevel;

    //Player and universe objects are passed from config screen
    private Player myPlayer;
    private Universe myUniverse;
    private List<SolarSystem> solarSystems;
    private SolarSystem mySS;
    private Marketplace myMarket;
    private ShipYard mySy;

    @FXML
    Parent root;
    @FXML
    private Canvas canvas;
    @FXML
    private ProgressBar fuelGauge;
    @FXML
    private Pane topPane;

    //  random events
    @FXML
    private Pane randomEventPane;
    @FXML
    private Label randomEventDescriptionLabel;
    @FXML
    private Button randomEventDismissButton;

//<editor-fold defaultstate="collapsed" desc="MAIN CANVAS DRAWING">

    /**
     * Draws a planet on the canvas
     *
     * @param gc graphics context
     * @param x  x-coordinate of the center
     * @param y  y-coordinate of the center
     * @param r  radius
     */
    private void drawPlanet(GraphicsContext gc, int x, int y, int r) {
        r = (int) (r * (1 + mySS.getTechLevel().getValue() / 5.0));
        int d = 2 * r;
        int b = r / 4;
        gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.REFLECT,
                new Stop(0.0, mySS.getPrimaryColor().deriveColor(0, 1, 1, 0.8)),
                new Stop(1.0, Color.TRANSPARENT)));
        gc.fillOval(x - r - b, y - r - b, d + 2 * b, d + 2 * b);

        gc.setFill(new RadialGradient(0, 0, 0.3, 0.3, 1, true,
                CycleMethod.REFLECT,
                new Stop(0.0, mySS.getPrimaryColor()),
                new Stop(1.0, Color.BLACK)));
        gc.fillOval(x - r, y - r, d, d);

    }

    /**
     * Fills the canvas with planets
     */
    private void fillMainCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawPlanet(gc, 400, 300, 50);

        coordinates.setText("Coordinates: (" + mySS.getX() + ", "
                + mySS.getY() + ")\n" + "Solar System: " + mySS.getName() + "\n"
                + "Government: " + mySS.getGov().toString() + "\n" 
                + "Resource: " + mySS.getResource() + "\n"
                + "Tech Level: " + mySS.getTechLevel()
        );
    }

    /**
     * Displays ship information such as ship type, status, cargo...
     */
    private void displayShipInfo() {
        shipInfo.setText("Ship: " + myPlayer.getShip().getType() + "\n" +
                "Status: Healthy\n" +
                "Cargo: " + myPlayer.getShip().getCurrentCargo() + "/" + myPlayer.getShip().getMaxCargo() + "\n" +
                "Credits: " + myPlayer.getBalance());
    }

    private void updateFuelGauge() {
        fuelGauge.setProgress((double) myPlayer.getShip().getFuelReading() / myPlayer.getShip().getFuelCapacity());
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
     *
     * @param event
     */
    @FXML
    private void closeMarketplace(ActionEvent event) {
        marketPane.setVisible(false);
        displayShipInfo();
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
                        if (myMarket.isBuyable(item)) {
                            buyPane.setVisible(true);
                            int price = myMarket.getBuyPrice(item);
                            buyPrice.setText(String.valueOf(price));
                            buyQuantity.setText("1");
                            int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText()) * price;
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
                            int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText()) * price;
                            sellAfterBalance.setText(String.valueOf(newBalance));
                        } else {
                            sellPane.setVisible(false);
                        }
                    }
                });
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BUY WINDOW HANDLERS">

    /**
     * Increases the quantity to purchase by 1
     *
     * @param event
     */
    @FXML
    private void increaseBuyQuantity(ActionEvent event) {
        int q = Integer.parseInt(buyQuantity.getText());
        Item item = Item.values()[buyList.getSelectionModel().getSelectedIndex()];
        if (myMarket.getQuantity(item) > q) {
            q++;
            buyQuantity.setText(q + "");
            int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText()) * Integer.parseInt(buyPrice.getText());
            buyAfterBalance.setText(String.valueOf(newBalance));
        }
    }

    /**
     * Decreases the quantity to purchase by 1
     *
     * @param event
     */
    @FXML
    private void decreaseBuyQuantity(ActionEvent event) {
        if (Integer.parseInt(buyQuantity.getText()) != 1) {
            int q = Integer.parseInt(buyQuantity.getText());
            q--;
            buyQuantity.setText(q + "");
            int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText()) * Integer.parseInt(buyPrice.getText());
            buyAfterBalance.setText(String.valueOf(newBalance));
        }
    }

    /**
     * Attempts to execute a purchase of goods.
     *
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
                BuyItemCommand cmd = new BuyItemCommand(myPlayer, myMarket, item, quantity);
                cp.doCommand(cmd);
                /*
                myMarket.buy(item, quantity);
                myPlayer.getShip().addCargo(item, quantity);
                myPlayer.setBalance(newBalance);
                */
                displayCargo();
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
     * Undoes the purchases made earlier
     * @param e action event
     */
    @FXML
    public void undoBuy(ActionEvent e) {
        cp.undoCommand();
        displayCargo();
        resetBuyList();
        clearBuyWindow();
    }
    
    
    /**
     * Redoes the purchases that had been undone
     * @param e action event
     */
    @FXML
    public void redoBuy(ActionEvent e) {
        cp.redoCommand();
        displayCargo();
        resetBuyList();
        clearBuyWindow();
    }

    /**
     * Clears the quantity from the purchase screen
     */
    private void clearBuyWindow() {
        int price = myMarket.getBuyPrice(Item.values()[buyList.getSelectionModel().getSelectedIndex()]);
        buyPrice.setText(String.valueOf(price));
        buyQuantity.setText("1");
        buyBalance.setText(String.valueOf(myPlayer.getBalance()));
        int newBalance = myPlayer.getBalance() - Integer.parseInt(buyQuantity.getText()) * Integer.parseInt(buyPrice.getText());
        buyAfterBalance.setText(String.valueOf(newBalance));
    }

    /**
     * Re-initializes the purchase screen
     */
    private void resetBuyList() {
        for (int i = 0; i < Item.values().length; i++) {
            if (myMarket.isBuyable(Item.values()[i])) {
                buyItems.set(i, Item.values()[i].getName() + " (Price: " + myMarket.getBuyPrice(Item.values()[i]) + " credits, Quantity: " + myMarket.getQuantity(Item.values()[i]) + ")");
            } else {
                buyItems.set(i, Item.values()[i].getName() + " is not available");
            }
        }
        buyList.setItems(buyItems);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SELL WINDOW HANDLERS">

    /**
     * Increases the number of goods to sell by 1
     *
     * @param event
     */
    @FXML
    private void increaseSellQuantity(ActionEvent event) {
        int q = Integer.parseInt(sellQuantity.getText());
        q++;
        sellQuantity.setText(q + "");
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText()) * Integer.parseInt(sellPrice.getText());
        sellAfterBalance.setText(String.valueOf(newBalance));
    }

    /**
     * Decreases the number of goods to sell by 1
     *
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
     *
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
        int newBalance = myPlayer.getBalance() + Integer.parseInt(sellQuantity.getText()) * Integer.parseInt(sellPrice.getText());
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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="CARGO HELPER METHODS">

    /**
     * Sets cargo text screen
     */
    private void displayCargo() {
        cargo.setText("Cargo: " + myPlayer.getShip().getCurrentCargo() + "/" + myPlayer.getShip().getMaxCargo());
    }

//</editor-fold>
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="JUMP DRIVE">

    /**
     * Opens the map
     *
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
            }
        });
        fromSS.setText(mySS.getName());
    }

    /**
     * Closes the map
     *
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
        TableColumn<SolarSystem, Double> distanceCol = new TableColumn<>("Distance");
        distanceCol.setCellValueFactory(new Callback<CellDataFeatures<SolarSystem, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(CellDataFeatures<SolarSystem, Double> ss) {
                double d = (double) Math.round(Universe.calcDistance(mySS, ss.getValue()) * 1000) / 1000;
                return new ReadOnlyObjectWrapper(d);
            }
        });

        ssTable.getColumns().setAll(nameCol, xCol, yCol, resourceCol, techLevelCol, distanceCol);
        toNearestPlanet();
        
    }

    /**
     * Draws the selected SolarSystem
     * shows flightDistance, fuelLeft, fuelRequired
     *
     * @param targetSS
     */
    private void showSelectedSS(SolarSystem targetSS) {
        if (targetSS != null) {
            drawMap();
            GraphicsContext gc = mapCanvas.getGraphicsContext2D();
            gc.setFill(Color.RED);
            gc.fillOval(targetSS.getX() * 2 - 2, targetSS.getY() * 2 - 2, 4, 4);
            toSS.setText(targetSS.getName());
            flightDistance.setText(Universe.calcDistance(mySS, targetSS) + "");
            fuelLeft.setText(myPlayer.getShip().getFuelReading() + "");
            fuelRequired.setText(Universe.calcFuelRequired(mySS, targetSS) + "");
        }
    }

    /**
     * Draws the Universe
     */
    private void drawMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        for (SolarSystem ss : myUniverse.getSolarSystems()) {
            int x = ss.getX();
            int y = ss.getY();
            gc.setFill(Color.WHITE);
            gc.fillOval(x * 2 - 1, y * 2 - 1, 2, 2);
        }
        gc.setFill(Color.AQUA);
        gc.fillOval(mySS.getX() * 2 - 2, mySS.getY() * 2 - 2, 4, 4);
    }

    /**
     * Occurs when jump drive is activated
     *
     * @param event
     */
    @FXML
    private void activateJumpDrive(ActionEvent event) {
        SolarSystem current = mySS;
        SolarSystem dest = ssTable.getSelectionModel().getSelectedItem();
        if (!(dest == current)) {
            if (Universe.shipCanTravel(myPlayer.getShip(), current, dest)) {
                int fuelUnits = Universe.calcFuelRequired(current, dest);
                myPlayer.getShip().refill(-1 * fuelUnits);
                mySS = dest;
                mySS.setMP();
                myMarket = mySS.getMP();
                enterLightTunnel();

                //  random events
                ////////////////////////////////////////////////////////////////

                //  the chance of an event happening mid-travel is proportional to the distance travelled
                //  here, we normalize by the max travellable distance and create a distance multiplier that we multiply
                //  by individual RandomEvent probabilities
                int w = myUniverse.getWidth(), h = myUniverse.getHeight();
                double maxDist = Math.sqrt(w * w + h * h);
                double distMultiplier = 0.5 + 0.5 * (Universe.calcDistance(current, dest) / maxDist);

                ArrayList<RandomEvent> allEvents = new ArrayList<>();
                allEvents.add(new PirateRaid());
                allEvents.add(new PoliceSearch());
                allEvents.add(new WaterLeak());
                allEvents.add(new TreasureChest());

                //  picture the probabilities of different events as slices on a circular spinner
                //  create a random number @spin between zero and one and see which 'slice' (event) it landed on, if any
                Random rand = new Random();
                double spin = rand.nextFloat();
                double probabilitiesSoFar = 0;
                RandomEvent randEvent = null;
                for (RandomEvent potentialEvent : allEvents) {
                    probabilitiesSoFar += potentialEvent.getProbabilityMultiplier() * distMultiplier;
                    if (spin < probabilitiesSoFar) {
                        randEvent = potentialEvent;
                        break;
                    }
                }


//                randEvent = new PirateRaid();   //  uncomment this to test random event functionality


                //  if an event happened, we apply it and display a description of what happened
                if (randEvent != null) {
                    String eventDesc = randEvent.apply(myPlayer);
                    randomEventDescriptionLabel.setText(eventDesc);
                } else {
                    randomEventDescriptionLabel.setText(null);
                }
                fillMainCanvas();
                updateFuelGauge();
                displayShipInfo();
            } else {
                MessageAPI msgAPI = new MessageAPI(topPane);
                msgAPI.showMessage("The destination is out of range");
            }
        } else {
            MessageAPI msgAPI = new MessageAPI(topPane);
            msgAPI.showMessage("Please choose a system different than your current system.");
        }
    }

    /**
     * When the user presses the 'Ok' button on the random event pane, this gets
     * called to dismiss it.
     */
    @FXML
    private void dismissRandomEventPane(ActionEvent event) {
        randomEventPane.setVisible(false);
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
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
                                     @Override
                                     public void handle(ActionEvent event) {
                                         topPane.setVisible(false);
                                         mapPane.setVisible(false);
                                         topPane.getChildren().clear();

                                         if (randomEventDescriptionLabel.getText() != null) {
                                             randomEventPane.setVisible(true);
                                         }
                                     }
                                 }
        );
    }

    @FXML
    private void toNearestPlanet() {
        ssTable.getSortOrder().add(ssTable.getColumns().get(5));
        SolarSystem nearestSS = ssTable.getItems().get(1);
        showSelectedSS(nearestSS);
        ssTable.getSelectionModel().select(nearestSS);
        ssTable.scrollTo(nearestSS);
    }

    //<editor-fold defaultstate="collapsed" desc="INTERACTIVE MAP">

    /**
     * Opens the universe map
     */
    @FXML
    private void openInteractiveMap() {
        ScrollPane sp = new ScrollPane();
        Pane cPane = new Pane();
        Canvas c = new Canvas();
        final Canvas c1 = new Canvas();
        final int size = 1500;
        c.setHeight(size);
        c.setWidth(size);
        c1.setHeight(size);
        c1.setWidth(size);
        GraphicsContext gc = c.getGraphicsContext2D();
        final GraphicsContext gc1 = c1.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, c.getHeight(), c.getWidth());
        gc.setFill(Color.WHITE);
        final int r = 10;
        final int ratio = (int) c.getHeight() / myUniverse.getHeight();

        gc.setStroke(Color.gray(0.2));
        for (int x = 0; x < size; x = x + 50) {
            gc.strokeLine(x, 0, x, size);
            gc.strokeLine(0, x, size, x);
        }

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int r1 = rand.nextInt(3) + 1;
            gc.fillOval(rand.nextInt(size), rand.nextInt(size), r1, r1);
        }

        for (SolarSystem ss : myUniverse.getSolarSystems()) {
            gc.fillOval(ratio * ss.getX() - r, ratio * ss.getY() - r, 2 * r, 2 * r);
        }

        gc.setFill(Color.AQUA);
        gc.fillOval(ratio * mySS.getX() - r, ratio * mySS.getY() - r, 2 * r, 2 * r);
        gc.fillText("You are here", ratio * mySS.getX() + r, ratio * mySS.getY() + r * 2);

        //Set canvas onClick event
        c1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SolarSystem clickedSS = hasPlanetAt((int) mouseEvent.getX(), (int) mouseEvent.getY(), r, ratio);
                if (clickedSS != null) {
                    gc1.clearRect(0, 0, c1.getWidth(), c1.getHeight());
                    System.out.println(clickedSS);
                    gc1.setFill(Color.RED);
                    gc1.fillOval(ratio * clickedSS.getX() - r, ratio * clickedSS.getY() - r, 2 * r, 2 * r);
                    gc1.setFill(Color.WHITE);
                    String description = String.format("%s (%d, %d)\n%s\n%s", clickedSS.getName(), clickedSS.getX(), clickedSS.getY(), clickedSS.getResource(), clickedSS.getTechLevel());
                    int x_des = ratio * clickedSS.getX();
                    int y_des = ratio * clickedSS.getY();
                    if (x_des > size - 100) {
                        x_des -= 100;
                    } else {
                        x_des += r;
                    }
                    if (y_des > size - 100) {
                        y_des -= 50;
                    } else {
                        y_des += 2 * r;
                    }
                    gc1.fillText(description, x_des, y_des);
                }
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        closeInteractiveMap(clickedSS);
                    }
                }
            }

        });
        sp.setPrefSize(800, 600);
        sp.setPannable(true);
        cPane.getChildren().addAll(c, c1);
        sp.setContent(cPane);
        topPane.getChildren().add(sp);
        topPane.setVisible(true);
    }

    private void closeInteractiveMap(SolarSystem ss) {
        topPane.getChildren().clear();
        topPane.setVisible(false);
        if (ss != null) {
            showSelectedSS(ss);
            ssTable.getSelectionModel().select(ss);
            ssTable.scrollTo(ss);
        }
    }

    private SolarSystem hasPlanetAt(int x, int y, int r, int ratio) {
        for (SolarSystem ss : solarSystems) {
            int x1 = ratio * ss.getX();
            int y1 = ratio * ss.getY();
            if (x1 - r < x && x < x1 + r && y1 - r < y && y < y1 + r) {
                return ss;
            }
        }
        return null;
    }

    /**
     * Closes the GameController, goes to title screen
     *
     * @param e
     */
    @FXML
    private void exit(ActionEvent e) {
        new DbMethods().save(application.getPlayer());
        application.showWelcome();
    }


    /**
     * Saves the GameController, goes to title screen
     *
     * @param e
     */
    @FXML
    private void save(ActionEvent e) {
        new DbMethods().save(application.getPlayer());
    }
//</editor-fold>
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SHIPYARD">

    /**
     * Opens shipyard window
     *
     * @param event
     */
    @FXML
    private void openShipyard(ActionEvent event) {
        shipyardPane.setVisible(true);
        mySy = mySS.getSy();
        createShipyardTable();
        shipyardTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ShipType>() {
            public void changed(ObservableValue<? extends ShipType> ov,
                                ShipType oldval, ShipType newValue) {
                showSelectedShipType(newValue);
            }
        });
    }
    
    /**
     * Creates the table of upgrades.
     */
    private void createUpgradeTable() {
        if (mySS.getTechLevel().getValue() >= 4) {
            List<Upgrade> upgradeList =
                    new ArrayList(mySy.getAllUpgrades().keySet());
            ObservableList<Upgrade> upgrades =
                    FXCollections.observableArrayList(upgradeList);
            upgradeTable.setItems(upgrades);

            TableColumn<Upgrade, String> nameCol =
                    new TableColumn<>("Upgrade Name");
            nameCol.setCellValueFactory(new PropertyValueFactory("name"));
            TableColumn<Upgrade, Integer> priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory("price"));
            TableColumn<Upgrade, String> typeCol = new TableColumn<>("Type of Upgrade");
            typeCol.setCellValueFactory(new PropertyValueFactory("type"));
            
            int PREFERRED_WIDTH = 100;
            nameCol.setMinWidth(PREFERRED_WIDTH);
            priceCol.setMinWidth(PREFERRED_WIDTH);
            typeCol.setMinWidth(PREFERRED_WIDTH);
            upgradeTable.getColumns().setAll(nameCol, priceCol, typeCol);
        }
    }
    /**
     * Creates Shipyard table
     */
    private void createShipyardTable() {
            List<ShipType> shipList =
                    new ArrayList(mySy.getAvailableShips().keySet());
            ObservableList<ShipType> shipTypes =
                    FXCollections.observableArrayList(shipList);

            shipyardTable.setItems(shipTypes);

            TableColumn<ShipType, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory("name"));
            TableColumn<ShipType, Integer> priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory("price"));
            TableColumn<ShipType, Integer> cargoCol =
                new TableColumn<>("Cargo Space");
            cargoCol.setCellValueFactory(new PropertyValueFactory("cargoBay"));
            TableColumn<ShipType, Integer> crewCol = new TableColumn<>("Crew");
            crewCol.setCellValueFactory(new PropertyValueFactory("crew"));
            TableColumn<ShipType, Integer> weaponsCol =
                new TableColumn<>("Weapon Slots");
            weaponsCol.setCellValueFactory(new PropertyValueFactory("weaponSlots"));
            TableColumn<ShipType, Integer> sheildsCol =
                new TableColumn<>("Shield Slots");
            sheildsCol.setCellValueFactory(new PropertyValueFactory("shieldSlots"));
            TableColumn<ShipType, Integer> gadgetsCol =
                new TableColumn<>("Gadget Slots");
            gadgetsCol.setCellValueFactory(new PropertyValueFactory("gadgetSlots"));
            TableColumn<ShipType, Integer> fuelCol =
                new TableColumn<>("Fuel Capacity");
            fuelCol.setCellValueFactory(new PropertyValueFactory("fuel"));
            TableColumn<ShipType, Integer> sizeCol = new TableColumn<>("Size");
            sizeCol.setCellValueFactory(new PropertyValueFactory("size"));

            int PREFERRED_WIDTH = 100;
            nameCol.setMinWidth(PREFERRED_WIDTH);
            priceCol.setMinWidth(PREFERRED_WIDTH);
            cargoCol.setMinWidth(PREFERRED_WIDTH);
            crewCol.setMinWidth(PREFERRED_WIDTH);
            weaponsCol.setMinWidth(PREFERRED_WIDTH);
            sheildsCol.setMinWidth(PREFERRED_WIDTH);
            gadgetsCol.setMinWidth(PREFERRED_WIDTH);
            fuelCol.setMinWidth(PREFERRED_WIDTH);

            shipyardTable.getColumns().setAll(nameCol, priceCol, cargoCol, crewCol,
                    weaponsCol, sheildsCol, gadgetsCol, fuelCol);
    }

    /**
     * Draws the selected ShipType.
     *
     * @param targetShipType
     */
    private void showSelectedShipType(ShipType targetShipType) {
        if (targetShipType != null) {
            drawShip(targetShipType);
            GraphicsContext gc = mapCanvas.getGraphicsContext2D();
            gc.setFill(Color.RED);
            syBalance.setText(myPlayer.getBalance() + " ");
            int shipWorth = myPlayer.getShip().getType().getPrice();
            for (int i = 0; i < myPlayer.getShip().getCargo().size(); i++) {
                if (myPlayer.getShip().getCargo().get(i) != null) {
                    shipWorth += myMarket.getBuyPrice(Item.values()[i]) * myPlayer.getShip().getCargo().get(i);
                }
            }
            shipWorth *= .9;
            syShipWorth.setText(shipWorth + " ");
            syCost.setText(targetShipType.getPrice() + " ");
            syNewBalance.setText((myPlayer.getBalance() + shipWorth - targetShipType.getPrice()) + " ");
        }
    }

    private void showUpgrade(Upgrade targetUpgrade) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        syBalance.setText(myPlayer.getBalance() + " ");
        syCost.setText(targetUpgrade.getPrice() + " ");
        syNewBalance.setText((myPlayer.getBalance() - targetUpgrade.getPrice()) + " ");
    }

    /**
     * Draws the ship
     */
    private void drawShip(ShipType shipType) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        //TODO draw image
        //gc.drawImage(new Image(this.getClass().getResource("img/" + shipType.getImageName()).getPath()), 0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
    }

    /**
     * Closes shipyard window
     *
     * @param event
     */
    @FXML
    private void closeShipyard(ActionEvent event) {
        shipyardPane.setVisible(false);
    }

    /**
     * Opens upgrade table
     * @param event 
     */
    @FXML
    private void openUpgrades(ActionEvent event) {
        shipyardPane.setVisible(false);
        upgradePane.setVisible(true);
        createUpgradeTable();
        upgradeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Upgrade>() {
            public void changed(ObservableValue<? extends Upgrade> o,
                                Upgrade oldType, Upgrade newType) {
                    //showUpgrade(newType);
            }
        });
    }

    /**
     * Handles buying the upgrade
     */
    @FXML
    private void buyUpgrade(ActionEvent event) {
        MessageAPI msgAPI = new MessageAPI(topPane);
        Upgrade select = upgradeTable.getSelectionModel().getSelectedItem();
        if (select.getType() == Upgrade.UPGRADE_TYPE.Weapon) {
            if ((myPlayer.getShip().getWeaponSlots() > 0) &&
                    myPlayer.getBalance() > select.getPrice()) {
                myPlayer.setBalance(myPlayer.getBalance() - select.getPrice());
                myPlayer.getShip().fillWeapon();
                msgAPI.showMessage("Upgrade item is added.\nWeapon slots available: " + myPlayer.getShip().getWeaponSlots()
                        + " / " + myPlayer.getShip().getType().getWeaponSlots() + " total");
                displayShipInfo();
                closeUpgrade(event);
            } else {
                msgAPI.showMessage("Unable to upgrade. Check if you have available slots and enough money.");
            }
        } else if (select.getType() == Upgrade.UPGRADE_TYPE.Shield) {
            if ((myPlayer.getShip().getShieldSlots() > 0) &&
                    myPlayer.getBalance() > select.getPrice()) {
                myPlayer.setBalance(myPlayer.getBalance() - select.getPrice());
                myPlayer.getShip().fillShield();
                msgAPI.showMessage("Upgrade item is added.\nShield slots available: " + myPlayer.getShip().getShieldSlots()
                        + " / " + myPlayer.getShip().getType().getShieldSlots() + " total");
                displayShipInfo();
                closeUpgrade(event);
            } else {
                msgAPI.showMessage("Unable to upgrade. Check if you have available slots and enough money.");
            }
        } else if (select.getType() == Upgrade.UPGRADE_TYPE.Gadget) {
            if ((myPlayer.getShip().getGadgetSlots() > 0) &&
                    myPlayer.getBalance() > select.getPrice()) {
                myPlayer.setBalance(myPlayer.getBalance() - select.getPrice());
                myPlayer.getShip().fillGadget();
                if (select == Upgrade.INVENTORY) {
                    myPlayer.getShip().setExtraCargo(5);
                }
                msgAPI.showMessage("Upgrade item is added.\nGadget slots available: " + myPlayer.getShip().getGadgetSlots()
                        + " / " + myPlayer.getShip().getType().getGadgetSlots() + " total");
                displayShipInfo();
                closeUpgrade(event);
            } else {
                msgAPI.showMessage("Unable to upgrade. Check if you have available slots and enough money.");
            }
        }
    }

    @FXML
    private void closeUpgrade(ActionEvent event) {
        upgradePane.setVisible(false);
        shipyardPane.setVisible(true);
    }
    
    /**
     * Handles Buy Ship button
     *
     * @param event
     */
    @FXML
    private void buyShip(ActionEvent event) {
        MessageAPI msgAPI = new MessageAPI(topPane);
        ShipType shipType = shipyardTable.getSelectionModel().getSelectedItem();
        int shipWorth = myPlayer.getShip().getType().getPrice();
        for (int i = 0; i < myPlayer.getShip().getCargo().size(); i++) {
            if (myPlayer.getShip().getCargo().get(i) != null) {
                shipWorth += myMarket.getBuyPrice(Item.values()[i]) *
                    myPlayer.getShip().getCargo().get(i);
            }
        }

        shipWorth *= .9; // Depreciation

        if (myPlayer.getBalance() + shipWorth < shipType.getPrice()) {
            closeShipyard(event);
            msgAPI.showMessage("Our balance isn't high enough!");
        } else {
            myPlayer.setBalance(myPlayer.getBalance() - shipType.getPrice()
                    + shipWorth);
            myPlayer.setShip(new Ship(shipType));
            fillMainCanvas();
            updateFuelGauge();
            displayShipInfo();
            closeShipyard(event);
        }
    }

    /**
     * Handles Repair Ship button
     *
     * @param event
     */
    @FXML
    private void repairShip(ActionEvent event) {
        if (myPlayer.getBalance() < myPlayer.getShip().getType().getRepairCost()) {
            closeShipyard(event);
            MessageAPI msgAPI = new MessageAPI(topPane);
            msgAPI.showMessage("Our balance isn't high enough!");

        } else if (myPlayer.getShip().getHullStrength() == myPlayer.getShip().getType().getHullStrength()) {
            closeShipyard(event);
            MessageAPI msgAPI = new MessageAPI(topPane);
            msgAPI.showMessage("Our hull is full strength!");
        } else {
            myPlayer.setBalance(myPlayer.getBalance() - myPlayer.getShip().getType().getRepairCost());
            myPlayer.getShip().setHullStrength(myPlayer.getShip().getType().getHullStrength());
            fillMainCanvas();
            updateFuelGauge();
            displayShipInfo();
            closeShipyard(event);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="CONTROLLER INITIALIZATION">

    /**
     * Links to main application
     *
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
        shipyardPane.setVisible(false);
        upgradePane.setVisible(false);
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
        
        //mySS.setTechLevel(TechLevel.INDUSTRIAL);
        //mySS.setResource(Resource.LOTSOFWATER);
        //mySS.setTechLevel(TechLevel.MEDIEVAL);

        fillMainCanvas();
        displayShipInfo();

        //MARKETPLACE INITIALIZATION
        mySS.setMP();
        myMarket = mySS.getMP();
        initBuyWindow();
        initSellWindow();
    }
//</editor-fold>

}
