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

/**
 * Welcome screen controller
 * 
 * @author Bao
 */
public class WelcomeController implements Initializable {
    
    private Main application;
    
    /**
     * Links to main application
     * @param application 
     */
    public void setApp(Main application){
        this.application = application;
    }
    
    @FXML
    private void handleStartAction(ActionEvent event) {
        application.openConfig();
    }
    
    @FXML
    private void handleLoadAction(ActionEvent event) {
        //do nothing
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
