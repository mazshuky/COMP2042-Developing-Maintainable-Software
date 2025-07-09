package com.example.demo;

import javafx.scene.control.ProgressBar;

/**
 * The {@code BossHealth} class represents a custom progress bar for displaying
 * the boss's health in the game.
 * It extends {@link ProgressBar} and provides a method to update the health value.
 */
public class BossHealth {
    private final ProgressBar bossHealthBar;

    /**
     * Constructs a BossHealth object and initializes the health bar.
     */
    public BossHealth() {
        this.bossHealthBar = new ProgressBar();
        bossHealthBar.setLayoutX(1070);
        bossHealthBar.setLayoutY(20);
        bossHealthBar.setPrefWidth(200);
        bossHealthBar.setPrefHeight(25);
        bossHealthBar.setProgress(1.0);
        bossHealthBar.setStyle("-fx-accent: green;");
    }

    /**
     * Updates the health bar's progress based on the boss's current health.
     *
     * @param health the current health of the boss, represented as a double between 0.0 and 1.0
     */
    public void update(double health) {
        bossHealthBar.setProgress(health);
    }

    /**
     * Returns the ProgressBar representing the boss's health bar.
     *
     * @return the ProgressBar for the boss's health
     */
    public ProgressBar getBossHealthBar() {
        return bossHealthBar;
    }

}

