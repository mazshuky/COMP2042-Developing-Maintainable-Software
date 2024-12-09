package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class LevelThree extends LevelParent {

    private final Boss boss;
    private final LevelViewLevelThree levelView;
    private final List<Bomb> bombs = new ArrayList<>();
    private Timeline bombGenerationTimeline;
    private Timeline bombMovementTimeline;

    public LevelThree(double screenHeight, double screenWidth, HeartDisplay heartDisplay, Controller controller) {
        super(GameConstants.BACKGROUND_IMAGE_THREE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay, controller);
        this.boss = initializeBoss();
        this.levelView = new LevelViewLevelThree(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
        bindShieldToBoss();
        initializeBombs();
    }

    @Override
    protected void initializeFriendlyUnits() {
        addPlayerToRoot();
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (isNoEnemiesOnScreen()) {
            addBossToRoot();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
    }

    @Override
    public void startGame() {
        requestFocusForBackground();
        startTimeline();
        startBombGenerationTimeline();
        startBombMovementTimeline();
    }

    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        if (boss.isShielded()) {
            levelView.getShieldImage().showShield();
        } else {
            levelView.getShieldImage().hideShield();
        }
        levelView.updateBossHealth(boss.getHealth() / 10.0);
    }

    private void bindShieldToBoss() {
        double xOffset = 600;
        double yOffset = -40;

        levelView.getShieldImage().translateXProperty().bind(boss.translateXProperty().add(xOffset));
        levelView.getShieldImage().translateYProperty().bind(boss.translateYProperty().add(yOffset));
    }

    private Boss initializeBoss() {
        return new Boss();
    }

    private void addPlayerToRoot() {
        getRoot().getChildren().add(getUser());
    }

    private boolean isNoEnemiesOnScreen() {
        return getCurrentNumberOfEnemies() == 0;
    }

    private void addBossToRoot() {
        addEnemyUnit(boss);
    }

    private void requestFocusForBackground() {
        getBackground().requestFocus();
    }

    private void startTimeline() {
        getTimeline().play();
    }

    private void initializeBombs() {
        bombGenerationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> generateBomb()));
        bombGenerationTimeline.setCycleCount(Timeline.INDEFINITE);

        bombMovementTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveBombs()));
        bombMovementTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void startBombGenerationTimeline() {
        bombGenerationTimeline.play();
    }

    private void startBombMovementTimeline() {
        bombMovementTimeline.play();
    }

    private void generateBomb() {
        double xPosition = Math.random() * getScreenWidth();
        Bomb newBomb = new Bomb(xPosition, 0);
        bombs.add(newBomb);
        getRoot().getChildren().add(newBomb);
    }

    private void moveBombs() {
        for (Bomb bomb : bombs) {
            bomb.moveDown();
            checkBombCollision(bomb);
        }
    }

    private void checkBombCollision(Bomb bomb) {
        if (bomb.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
            getUser().takeDamage();
            getUser().handleHit();
            resetBombPosition(bomb);
        }
    }

    private void resetBombPosition(Bomb bomb) {
        bomb.setLayoutX(Math.random() * getScreenWidth());
        bomb.setLayoutY(0);
    }
}
