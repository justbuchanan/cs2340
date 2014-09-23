package spacetrader;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX main application
 * 
 * @author Bao
 */
public class Main extends Application {
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Space Trader by Vanguard");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setResizable(false);
            gotoWelcome();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Opens configuration screen
     */
    void openConfig() {
        gotoConfig();
    }
    
    /**
     * Closes configuration screen
     */
    void closeConfig() {
        gotoWelcome();
    }
    
    /**
     * Goes to configuration screen
     */
    private void gotoConfig() {
        try {
            ConfigController config = (ConfigController) replaceSceneContent("config.fxml");
            config.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * Goes to welcome (starting) screen
     */
    private void gotoWelcome() {
        try {
            WelcomeController Welcome = (WelcomeController) replaceSceneContent("welcome.fxml");
            Welcome.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
    /**
     * Goes to game screen
     */
    void gotoGame(Player myPlayer, Universe myUniverse) {
        try {
            GameController game = (GameController) replaceSceneContent("game.fxml");
            game.setApp(this);
            game.config(myPlayer, myUniverse);
            /*TestController test = (TestController) replaceSceneContent("test.fxml");
            test.setApp(this);*/
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Replaces scene content
     * 
     * @param target fxml
     * @return controller
     * @throws Exception 
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
}
