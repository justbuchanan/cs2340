package spacetrader.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spacetrader.Main;
import spacetrader.models.Player;

/**
 * Welcome screen controller
 *
 * @author Bao
 */
public class WelcomeController implements Initializable {

    private Main application;

    /**
     * Links to main application
     *
     * @param application
     */
    public void setApp(Main application) {
        this.application = application;
    }

    /**
     * Handles new game requests
     *
     * @param event
     */
    @FXML
    private void handleStartAction(ActionEvent event) {
        application.openConfig();
    }

    /**
     * Loads a saved game
     *
     * @param event
     */
    @FXML
    private void handleLoadAction(ActionEvent event) {
        /*DatabaseController db = new DatabaseController();
        Player p = db.getPlayer();
        if (p == null) {
            System.out.println("No saved data available");
        } else {
            System.out.println(p);
        }*/
    }

    /**
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
