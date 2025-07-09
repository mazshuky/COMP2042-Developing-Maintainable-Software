package com.example.demo;

import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HeartDisplayTest {

    private HeartDisplay heartDisplay;

    @BeforeEach
    void setUp() {
        heartDisplay = new HeartDisplay(100, 100, 3);
    }

    @Test
    void testInitialHearts() {
        HBox container = heartDisplay.getContainer();
        assertEquals(3, container.getChildren().size(), "Initial number of hearts should be 3");
    }

    @Test
    void testRemoveHeart() {
        heartDisplay.removeHeart();
        HBox container = heartDisplay.getContainer();
        assertEquals(2, container.getChildren().size(), "Number of hearts should be 2 after removing one heart");
    }

    @Test
    void testUpdateHearts() {
        heartDisplay.update(1);
        HBox container = heartDisplay.getContainer();
        assertEquals(1, container.getChildren().size(), "Number of hearts should be 1 after update");
    }
}