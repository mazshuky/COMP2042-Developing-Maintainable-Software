package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

class BombTest extends ApplicationTest {

    private Bomb bomb;

    @BeforeEach
    void setUp() {
        bomb = new Bomb(0, 0);
    }

    @Test
    void testMoveDown() {
        double initialY = bomb.getLayoutY();
        bomb.moveDown();
        assertNotEquals(initialY, bomb.getLayoutY());
    }

    @Test
    void testPlayBombDropSound() {
        bomb.playBombDropSound();
        assertNotNull(bomb);
    }

    @Override
    public void start(Stage stage) {
        bomb = new Bomb(0, 0);
    }
}