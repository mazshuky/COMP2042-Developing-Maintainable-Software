package com.example.demo;

import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

class BossTest extends ApplicationTest {

    private Boss boss;

    @Override
    public void start(Stage stage) {
    }

    @BeforeEach
    void setUp() {
        boss = new Boss();
    }

    @Test
    void testInitialHealth() {
        assertEquals(10, boss.getHealth());
    }

    @Test
    void testUpdatePosition() {
        double initialY = boss.getTranslateY();
        boss.updatePosition();
        assertNotEquals(initialY, boss.getTranslateY());
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = boss.fireProjectile();
        if (projectile != null) {
            assertInstanceOf(BossProjectile.class, projectile);
        } else {
            assertNull(projectile);
        }
    }

    @Test
    void testTakeDamageWithoutShield() {
        int initialHealth = boss.getHealth();
        boss.takeDamage();
        assertEquals(initialHealth - 1, boss.getHealth());
    }

    @Test
    void testTakeDamageWithShield() {
        boss.activateShieldPublic();
        int initialHealth = boss.getHealth();
        boss.takeDamage();
        assertEquals(initialHealth, boss.getHealth());
    }

    @Test
    void testShieldActivation() {
        boss.updateShieldPublic();
        if (boss.isShielded()) {
            assertTrue(boss.isShielded());
        } else {
            assertFalse(boss.isShielded());
        }
    }

    @Test
    void testShieldDeactivation() {
        boss.activateShieldPublic();
        for (int i = 0; i < 50; i++) {
            boss.updateShieldPublic();
        }
        assertFalse(boss.isShielded());
    }

    @Test
    void testPlayFireballSound() {
        AudioClip fireballSound = boss.getFireballSound();
        assertNotNull(fireballSound);
    }
}