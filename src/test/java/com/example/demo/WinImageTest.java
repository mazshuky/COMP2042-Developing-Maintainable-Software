package com.example.demo;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

class WinImageTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
    }

    @Test
    void testWinImageInitialization() {
        WinImage winImage = new WinImage(100, 100);
        assertNotNull(winImage.getImage(), "The win image should be initialized.");
        assertEquals(100, winImage.getLayoutX(), "The X position should be set correctly.");
        assertEquals(100, winImage.getLayoutY(), "The Y position should be set correctly.");
        assertEquals(400, winImage.getFitHeight(), "The height should be set correctly.");
        assertEquals(500, winImage.getFitWidth(), "The width should be set correctly.");
    }

    @Test
    void testShowWinImage() {
        WinImage winImage = new WinImage(100, 100);
        winImage.showWinImage();
        assertTrue(winImage.isVisible(), "The win image should be visible after calling showWinImage.");
    }
}