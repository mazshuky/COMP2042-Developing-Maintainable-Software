package com.example.demo;

import javafx.scene.control.ProgressBar;

public class BossHealth {
    private final ProgressBar bossHealthBar;

    public BossHealth() {
        this.bossHealthBar = new ProgressBar();
        bossHealthBar.setLayoutX(1070);
        bossHealthBar.setLayoutY(20);
        bossHealthBar.setPrefWidth(200);
        bossHealthBar.setPrefHeight(25);
        bossHealthBar.setProgress(1.0);
        bossHealthBar.setStyle("-fx-accent: green;");
    }

    public void update(double health) {
        bossHealthBar.setProgress(health);
    }

    public ProgressBar getBossHealthBar() {
        return bossHealthBar;
    }

}

