package spacetrader.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spacetrader.Main;
import spacetrader.database.DbMethods;
import spacetrader.models.Player;
import spacetrader.models.Universe;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Welcome screen controller.
 *
 * @author Bao
 */
public class WelcomeController implements Initializable {

    private Main application;

    /**
     * Links to main application.
     *
     * @param application
     */
    public void setApp(Main application) {
        this.application = application;
    }

    /**
     * Handles new game requests.
     *
     * @param event
     */
    @FXML
    private void handleStartAction(ActionEvent event) {
        application.openConfig();
    }

    /**
     * Loads a saved game.
     *
     * @param event
     */
    @FXML
    private void handleLoadAction(ActionEvent event) {
        DbMethods db = new DbMethods();
        Player p = db.load();
        if (p == null) {
            System.out.println("No saved data available");
        } else {
            System.out.println(p);
        }

        application.gotoGame(p, Universe.generateUniverse(100, 100,
                application.NUMBER_OF_SOLAR_SYSTEMS));
    }

    /**
     * Initializes the controller
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
