/*package com.example.demo.controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu {

    private final Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void showMainMenu() {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());

        StackPane layout = new StackPane();
        layout.getChildren().add(startButton);

        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    private void startGame() {
        Controller controller = new Controller(stage);
        try {
            controller.launchGame(stage);
        } catch (CustomException e) {
            showErrorAlert(e);
        }
    }

    private void showErrorAlert(CustomException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error starting the game: " + e.getMessage());
        alert.show();
    }
}
*/

package com.example.demo.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    private final Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void showMainMenu() {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());

        VBox vbox = new VBox(20); // 20 is the spacing between elements
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(startButton);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/background.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, vbox);

        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    private void startGame() {
        Controller controller = new Controller(stage);
        try {
            controller.launchGame(stage);
        } catch (CustomException e) {
            showErrorAlert(e);
        }
    }

    private void showErrorAlert(CustomException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error starting the game: " + e.getMessage());
        alert.show();
    }
}