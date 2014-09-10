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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Zephyr
 */
public class ConfigController implements Initializable {
     
    @FXML
    Label p0;
    @FXML
    Label p1;    
    @FXML
    Label p2;    
    @FXML
    Label p3;    
    @FXML
    Label p4;   
    @FXML
    Button p0_up;
    @FXML
    Button p1_up;
    @FXML
    Button p2_up;
    @FXML
    Button p3_up;
    @FXML
    Button p4_up;
    @FXML
    Button p0_down;
    @FXML
    Button p1_down;
    @FXML
    Button p2_down;
    @FXML
    Button p3_down;
    @FXML
    Button p4_down;
    @FXML
    Label remain;
    @FXML
    Pane messagePane;
    @FXML
    TextField name;
    @FXML
    Label name_error;

    private Main application;
    private int[] points = new int[5];
    private final int MAX_POINTS = 16;
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @FXML
    private void handleOKAction(ActionEvent event) {
        if (name.getText().equals("")) {
            name_error.setText("Name field is blank.");
        } else
        messagePane.setVisible(true);
    }
    
    @FXML
    private void handleCancelAction(ActionEvent event) {
        application.closeConfig();
    }
        
    @FXML
    private void handleUp(ActionEvent event) {
        if (getRemainingPoints() > 0) {
            if (event.getSource().equals(p0_up)) {
                p0.setText(++points[0] + "");
            } else if (event.getSource().equals(p1_up)) {
                p1.setText(++points[1] + "");
            } else if (event.getSource().equals(p2_up)) {
                p2.setText(++points[2] + "");
            } else if (event.getSource().equals(p3_up)) {
                p3.setText(++points[3] + "");
            } else if (event.getSource().equals(p4_up)) {
                p4.setText(++points[4] + "");
            }
            //Show current remaining points (one less)
            showRemainingPoints();
        }
        //Debug
        //System.out.printf("%d %d %d %d %d - %d%n", points[0], points[1], points[2], points[3], points[4], sum);
    }
    
    @FXML
    private void handleDown(ActionEvent event) {
        if (event.getSource().equals(p0_down)) {
            p0.setText((points[0]==0)?"0":--points[0] + "");
        } else if (event.getSource().equals(p1_down)) {
            p1.setText((points[1]==0)?"0":--points[1] + "");
        } else if (event.getSource().equals(p2_down)) {
            p2.setText((points[2]==0)?"0":--points[2] + "");
        } else if (event.getSource().equals(p3_down)) {
            p3.setText((points[3]==0)?"0":--points[3] + "");
        } else if (event.getSource().equals(p4_down)) {
            p4.setText((points[4]==0)?"0":--points[4] + "");
        }
        showRemainingPoints();
    }
    
    @FXML
    private void handleGoToHomeScreen(ActionEvent event) {
        application.closeConfig();
    }
   
    private int getRemainingPoints() {
        int sum = 0;
        for (int point: points) {
            sum += point;
        }
        return MAX_POINTS - sum;
    }
    
    /**
     * Show remaining points in Label remain
     */
    private void showRemainingPoints() {
        remain.setText(getRemainingPoints() + "");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messagePane.setVisible(false);
        showRemainingPoints();
    }    
    
}
