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

public class GameTutorial {

    private static final Logger LOGGER = Logger.getLogger(GameTutorial.class.getName());
    private final Controller controller;

    public GameTutorial(Controller controller) {
        this.controller = controller;
    }

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

    private Parent loadFXML() throws CustomException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/GameTutorial.fxml"));
            return loader.load();
        } catch (IOException e) {
            throw new CustomException("Failed to load the tutorial FXML file", e);
        }
    }

    private Stage createTutorialStage(Parent root) {
        Stage tutorialStage = new Stage();
        tutorialStage.initModality(Modality.APPLICATION_MODAL);
        tutorialStage.setTitle("How to Play");
        tutorialStage.setScene(new Scene(root, 500, 400));

        InputStream iconStream = getClass().getResourceAsStream("/com/example/demo/images/tutorialfavicon.png");
        if (iconStream != null) {
            tutorialStage.getIcons().add(new Image(iconStream));
        } else {
            LOGGER.log(Level.WARNING, "Icon resource not found: /com/example/demo/images/tutorialfavicon.png");
        }

        tutorialStage.setOnCloseRequest(event -> controller.resumeGame());
        return tutorialStage;
    }
}