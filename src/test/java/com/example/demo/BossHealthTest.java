package com.example.demo;

import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

class BossHealthTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
    }

    @Test
    void testUpdate() {
        BossHealth bossHealth = new BossHealth();
        bossHealth.update(0.75);
        assertEquals(0.75, bossHealth.getBossHealthBar().getProgress(), 0.01);
    }

    @Test
    void testGetBossHealthBar() {
        BossHealth bossHealth = new BossHealth();
        ProgressBar progressBar = bossHealth.getBossHealthBar();
        assertNotNull(progressBar);
        assertEquals(1.0, progressBar.getProgress(), 0.01);
    }
}