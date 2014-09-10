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
 * FXML Controller class
 *
 * @author Zephyr
 */
public class ConfigController implements Initializable {

    private Main application;
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @FXML
    private void handleOKAction(ActionEvent event) {
        application.closeConfig();
    }
    
    @FXML
    private void handleCancelAction(ActionEvent event) {
        application.closeConfig();
    }
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
