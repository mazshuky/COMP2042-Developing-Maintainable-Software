package com.example.demo.controller;

import com.example.demo.SoundEffects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Class representing the main menu of the game.
 */
public class MainMenu {

    private static final double BUTTON_MARGIN_BOTTOM = 50.0;
    private final Stage stage;
    private AudioClip launchGameSound;

    /**
     * Constructor for the main menu.
     *
     * @param stage the stage to display the main menu for the game
     */
    public MainMenu(Stage stage) {
        this.stage = stage;
        initializeSounds();
    }

    /**
     * Displays the game main menu.
     */
    public void showMainMenu() {
        StackPane layout = createMainMenuLayout();
        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
        playGameLaunchSound();
    }

    /**
     * Creates the layout for the main menu.
     *
     * @return the layout for the main menu
     */
    private StackPane createMainMenuLayout() {
        Button startButton = createStartButton();
        ImageView backgroundImageView = createBackgroundImageView();

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, startButton);
        return layout;
    }

    /**
     * Creates the start button for the main menu.
     *
     * @return the start button
     */
    private Button createStartButton() {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(startButton, new Insets(0, 0, BUTTON_MARGIN_BOTTOM, 0));
        return startButton;
    }

    /**
     * Creates the background image view for the main menu.
     *
     * @return the background image view
     */
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

    /**
     * Starts the game by transitioning to the game controller.
     */
    private void startGame() {
        stopGameLaunchSound();
        Controller controller = new Controller(stage);
        try {
            controller.launchGame(stage);
        } catch (CustomException e) {
            showErrorAlert(e);
        }
    }

    /**
     * Initializes the sounds for the main menu.
     */
    private void initializeSounds() {
        this.launchGameSound = SoundEffects.loadSound("/com/example/demo/sounds/launchgame.wav", this.getClass());
    }

    /**
     * Plays the sound upon game launch.
     */
    private void playGameLaunchSound() {
        if (launchGameSound != null) {
            launchGameSound.play();
        }
    }

    /**
     * Stops the game launch sound.
     */
    private void stopGameLaunchSound() {
        if (launchGameSound != null) {
            launchGameSound.stop();
        }
    }

    /**
     * Shows an error alert with the specified exception.
     *
     * @param e the exception to display in the alert
     */
    private void showErrorAlert(CustomException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error starting the game: " + e.getMessage());
        alert.show();
    }
}