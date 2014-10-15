package spacetrader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import spacetrader.controllers.ConfigController;
import spacetrader.controllers.GameController;
import spacetrader.controllers.WelcomeController;
import spacetrader.models.Player;
import spacetrader.models.Universe;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX main application
 *
 * @author Bao
 */
public class Main extends Application {

    private Stage stage;
    private Stage mapStage;
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;

    /**
     * Starts the Application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Space Trader by Vanguard");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setResizable(false);
            showWelcome();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens configuration screen
     */
    public void openConfig() {
        showConfig();
    }

    /**
     * Closes configuration screen
     */
    public void closeConfig() {
        showWelcome();
    }

    /**
     * Goes to configuration screen
     */
    public void showConfig() {
        try {
            ConfigController config = (ConfigController) replaceSceneContent("views/config.fxml");
            config.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Goes to welcome (starting) screen
     */
    public void showWelcome() {
        try {
            WelcomeController Welcome = (WelcomeController) replaceSceneContent("views/welcome.fxml");
            Welcome.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Goes to game screen
     */
    public void gotoGame(Player myPlayer, Universe myUniverse) {
        try {
            GameController game = (GameController) replaceSceneContent("views/game.fxml");
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
