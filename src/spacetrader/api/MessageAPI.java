package spacetrader.api;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This is an API that will modify a top-level pane from the
 * controller to display message.
 *
 * @author Bao
 */
public class MessageAPI {
    private final Pane pane;
    
    /**
     * @param pane 
     */
    public MessageAPI(Pane pane) {
        this.pane = pane;
        clear();
    }

    /**
     * Displays message.
     * @param msg
     */
    public void showMessage(String msg) {
        StackPane stack = new StackPane();
        Label label = new Label(msg);
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Courier New", 14));
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.90)");
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().add(label);
        stack.setPrefHeight(pane.getHeight());
        stack.setPrefWidth(pane.getWidth());
        pane.getChildren().add(stack);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clear();
                hide();
            }
        });
        show();
    }

    /**
     * Clears everything inside the pane.
     */
    private void clear() {
        pane.getChildren().clear();
        pane.setOnMouseClicked(null);
    }

    /**
     * Sets pane visibility to true.
     */
    public void show() {
        pane.setVisible(true);
    }

    /**
     * Sets pane visibility to false.
     */
    public void hide() {
        pane.setVisible(false);
    }
}
