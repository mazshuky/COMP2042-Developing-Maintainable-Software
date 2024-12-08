package com.example.demo;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
    }

    @Test
    void updatePosition() {
        EnemyPlane enemyPlane = new EnemyPlane(100, 100);
        enemyPlane.updatePosition();
        assertEquals(0, enemyPlane.getX(), "The X position should be updated correctly.");
    }

    @Test
    void fireProjectile() {
        EnemyPlane enemyPlane = new EnemyPlane(100, 100);
        ActiveActorDestructible projectile = enemyPlane.fireProjectile();
        if (projectile != null) {
            assertInstanceOf(EnemyProjectile.class, projectile, "The projectile should be an instance of EnemyProjectile.");
        }
    }

    @Test
    void updateActor() {
        EnemyPlane enemyPlane = new EnemyPlane(100, 100);
        enemyPlane.updateActor();
        assertEquals(0, enemyPlane.getX(), "The X position should be updated correctly.");
    }
}