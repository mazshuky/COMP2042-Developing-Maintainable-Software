package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConstantsTest {

    @Test
    void testScreenHeightAdjustment() {
        assertEquals(150, GameConstants.SCREEN_HEIGHT_ADJUSTMENT);
    }

    @Test
    void testMillisecondDelay() {
        assertEquals(50, GameConstants.MILLISECOND_DELAY);
    }

    @Test
    void testBackgroundImageOne() {
        assertEquals("/com/example/demo/images/background1.jpg", GameConstants.BACKGROUND_IMAGE_ONE);
    }

    @Test
    void testBackgroundImageTwo() {
        assertEquals("/com/example/demo/images/background2.jpg", GameConstants.BACKGROUND_IMAGE_TWO);
    }

    @Test
    void testBackgroundImageThree() {
        assertEquals("/com/example/demo/images/background3.jpg", GameConstants.BACKGROUND_IMAGE_THREE);
    }

    @Test
    void testLevelOne() {
        assertEquals("com.example.demo.LevelOne", GameConstants.LEVEL_ONE);
    }

    @Test
    void testLevelTwo() {
        assertEquals("com.example.demo.LevelTwo", GameConstants.LEVEL_TWO);
    }

    @Test
    void testLevelThree() {
        assertEquals("com.example.demo.LevelThree", GameConstants.LEVEL_THREE);
    }

    @Test
    void testGameTutorial() {
        assertEquals("/com/example/demo/images/howtoplay.png", GameConstants.GAME_TUTORIAL);
    }

    @Test
    void testTotalEnemies() {
        assertEquals(10, GameConstants.TOTAL_ENEMIES);
    }

    @Test
    void testKillsToLevelTwo() {
        assertEquals(5, GameConstants.KILLS_TO_LEVEL_TWO);
    }

    @Test
    void testSpawnIntervalMs() {
        assertEquals(1000, GameConstants.SPAWN_INTERVAL_MS);
    }

    @Test
    void testPlayerInitialHealth() {
        assertEquals(5, GameConstants.PLAYER_INITIAL_HEALTH);
    }

    @Test
    void testHeartDisplayXPosition() {
        assertEquals(5, GameConstants.HEART_DISPLAY_X_POSITION);
    }

    @Test
    void testHeartDisplayYPosition() {
        assertEquals(10, GameConstants.HEART_DISPLAY_Y_POSITION);
    }

    @Test
    void testWinImageXPosition() {
        assertEquals(370, GameConstants.WIN_IMAGE_X_POSITION);
    }

    @Test
    void testWinImageYPosition() {
        assertEquals(175, GameConstants.WIN_IMAGE_Y_POSITION);
    }

    @Test
    void testLossScreenXPosition() {
        assertEquals(450, GameConstants.LOSS_SCREEN_X_POSITION);
    }

    @Test
    void testLossScreenYPosition() {
        assertEquals(150, GameConstants.LOSS_SCREEN_Y_POSITION);
    }

    @Test
    void testLossScreenWidth() {
        assertEquals(400, GameConstants.LOSS_SCREEN_WIDTH);
    }

    @Test
    void testLossScreenHeight() {
        assertEquals(400, GameConstants.LOSS_SCREEN_HEIGHT);
    }
}
