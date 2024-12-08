package com.example.demo.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainMenu {

    private static final double BUTTON_MARGIN_BOTTOM = 50.0;
    private final Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void showMainMenu() {
        StackPane layout = createMainMenuLayout();
        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createMainMenuLayout() {
        Button startButton = createStartButton();
        ImageView backgroundImageView = createBackgroundImageView();

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, startButton);
        return layout;
    }

    private Button createStartButton() {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(startButton, new Insets(0, 0, BUTTON_MARGIN_BOTTOM, 0));
        return startButton;
    }

    private ImageView createBackgroundImageView() {
        InputStream imageStream = getClass().getResourceAsStream("/com/example/demo/images/mainmenu.jpg");
        if (imageStream == null) {
            showErrorAlert(new CustomException("Background image not found", new Throwable("Resource not found")));
            return new ImageView();
        }
        Image backgroundImage = new Image(imageStream);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        return backgroundImageView;
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