package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.controller.Controller;
import com.example.demo.controller.CustomException;

/**
 * Class to display the game tutorial.
 */
public class GameTutorial {

    private static final Logger LOGGER = Logger.getLogger(GameTutorial.class.getName());
    private static final String FXML_PATH = "/com/example/demo/GameTutorial.fxml";
    private static final String ICON_PATH = "/com/example/demo/images/tutorialfavicon.png";
    private static final String WINDOW_TITLE = "How to Play";
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;

    private final Controller controller;

    /**
     * Constructs a new GameTutorial instance with the specified controller.
     *
     * @param controller  the controller for the game
     */
    public GameTutorial(Controller controller) {
        this.controller = controller;
    }

    /**
     * Displays the game tutorial window, pausing the game in the process.
     */
    public void showTutorial() {
        try {
            controller.pauseGame();
            Parent root = loadFXML();
            Stage tutorialStage = createTutorialStage(root);
            tutorialStage.show();
        } catch (CustomException e) {
            LOGGER.log(Level.SEVERE, "Failed to load the tutorial FXML file", e);
        }
    }

    /**
     * Loads the FXML file for the tutorial window.
     *
     * @return the root node of the FXML file
     * @throws CustomException if the FXML file fails to load
     */
    private Parent loadFXML() throws CustomException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            return loader.load();
        } catch (IOException e) {
            throw new CustomException("Failed to load the tutorial FXML file", e);
        }
    }

    /**
     * Creates and configures the tutorial stage.
     *
     * @param root  the root node of the tutorial window
     * @return the tutorial stage
     */
    private Stage createTutorialStage(Parent root) {
        Stage tutorialStage = new Stage();
        tutorialStage.initModality(Modality.APPLICATION_MODAL);
        tutorialStage.setTitle(WINDOW_TITLE);
        tutorialStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        setStageIcon(tutorialStage);
        tutorialStage.setOnCloseRequest(event -> controller.resumeGame());
        return tutorialStage;
    }

    /**
     * Sets the icon for the specified stage.
     *
     * @param stage  the stage to set the icon for
     */
    private void setStageIcon(Stage stage) {
        InputStream iconStream = getClass().getResourceAsStream(ICON_PATH);
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        } else {
            LOGGER.log(Level.WARNING, "Icon resource not found: " + ICON_PATH);
        }
    }
}