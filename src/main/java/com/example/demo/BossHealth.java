package com.example.demo;

//import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class BossHealth {
    private final ProgressBar bossHealthBar;
    //private Label bossHealthLabel;

    public BossHealth() {
        this.bossHealthBar = new ProgressBar();
        //this.bossHealthLabel = new Label();
        bossHealthBar.setLayoutX(1070);
        bossHealthBar.setLayoutY(20);
        bossHealthBar.setPrefWidth(200);
        bossHealthBar.setPrefHeight(25);
        bossHealthBar.setProgress(1.0);
        bossHealthBar.setStyle("-fx-accent: green;");
        //bossHealthLabel.setLayoutX(1000);
        //bossHealthLabel.setLayoutY(0);
       // bossHealthLabel.setText("Boss Health");
    }

    public void update(double health) {
        bossHealthBar.setProgress(health);
    }

    public ProgressBar getBossHealthBar() {
        return bossHealthBar;
    }

    /*public Label getBossHealthLabel() {
        return bossHealthLabel;
    }*/
}

