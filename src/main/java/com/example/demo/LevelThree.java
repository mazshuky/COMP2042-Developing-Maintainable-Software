package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * The LevelThree class manages the behavior and properties of the third level of the game.
 * It introduces bombs that fall from the top of the screen, adding a new challenge for the player.
 */
public class LevelThree extends LevelParent {

    private final Boss boss;
    private final LevelViewLevelThree levelView;
    private final List<Bomb> bombs = new ArrayList<>();
    private Timeline bombGenerationTimeline;
    private Timeline bombMovementTimeline;

    /**
     * Constructs a new LevelThree instance.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param heartDisplay the heart display for showing player lives
     * @param controller the game controller
     */
    public LevelThree(double screenHeight, double screenWidth, HeartDisplay heartDisplay, Controller controller) {
        super(GameConstants.BACKGROUND_IMAGE_THREE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay, controller);
        this.boss = initializeBoss();
        this.levelView = new LevelViewLevelThree(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
        bindShieldToBoss();
        initializeBombs();
    }

    /**
     * Initializes the friendly units for the game by adding the player to the root.
     */
    @Override
    protected void initializeFriendlyUnits() {
        addPlayerToRoot();
    }

    /**
     * Checks if the game is over by verifying if the player has been destroyed or if the boss is defeated.
     * If the player is destroyed, the game will invoke the loseGame() method.
     * If the boss is destroyed, the game will invoke three methods: the winGame(), StopBombElements(), and stopFireballSound().
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (boss.isDestroyed()) {
            winGame();
            stopBombElements();
            stopFireballSound();
        }
    }

    /**
     * Spawns enemy units on the screen. If there are no enemies currently on the screen,
     * the boss is added to the root.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (isNoEnemiesOnScreen()) {
            addBossToRoot();
        }
    }

    /**
     * Instantiates the LevelView for LevelThree, passing the root and initial player health to the constructor.
     *
     * @return A new LevelView object for LevelThree
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
    }

    /**
     * Starts the game by requesting focus for the background and starting the game-related timelines.
     * This includes starting the bomb generation timeline and bomb movement timeline.
     */
    @Override
    public void startGame() {
        requestFocusForBackground();
        startTimeline();
        startBombGenerationTimeline();
        startBombMovementTimeline();
    }

    /**
     * Updates the LevelView by calling the superclass updateLevelView method.
     * If the boss is shielded, the shield image is displayed, otherwise it is hidden.
     * Also updates the boss's health display on the LevelView.
     */
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

    /**
     * Binds the shield image to the boss's position.
     */
    private void bindShieldToBoss() {
        double xOffset = 600;
        double yOffset = -40;

        levelView.getShieldImage().translateXProperty().bind(boss.translateXProperty().add(xOffset));
        levelView.getShieldImage().translateYProperty().bind(boss.translateYProperty().add(yOffset));
    }

    /**
     * Initializes the boss for the level.
     *
     * @return the initialized boss
     */
    private Boss initializeBoss() {
        return new Boss();
    }

    /**
     * Adds the player to the root node.
     */
    private void addPlayerToRoot() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if there are no enemies on the screen.
     *
     * @return true if there are no enemies on the screen, false otherwise
     */
    private boolean isNoEnemiesOnScreen() {
        return getCurrentNumberOfEnemies() == 0;
    }

    /**
     * Adds the boss to the root node.
     */
    private void addBossToRoot() {
        addEnemyUnit(boss);
    }

    /**
     * Requests focus for the background node.
     */
    private void requestFocusForBackground() {
        getBackground().requestFocus();
    }

    /**
     * Starts the main game timeline.
     */
    private void startTimeline() {
        getTimeline().play();
    }

    /**
     * Initializes the bomb generation and movement timelines.
     */
    private void initializeBombs() {
        bombGenerationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> generateBomb()));
        bombGenerationTimeline.setCycleCount(Timeline.INDEFINITE);

        bombMovementTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveBombs()));
        bombMovementTimeline.setCycleCount(Timeline.INDEFINITE);

        getUser().setBombTimeline(bombMovementTimeline);
    }

    /**
     * Starts the bomb generation timeline.
     */
    private void startBombGenerationTimeline() {
        bombGenerationTimeline.play();
    }

    /**
     * Starts the bomb movement timeline.
     */
    private void startBombMovementTimeline() {
        bombMovementTimeline.play();
    }

    /**
     * Generates a new bomb at a random x-position at the top of the screen.
     */
    private void generateBomb() {
        double xPosition = Math.random() * getScreenWidth();
        Bomb newBomb = new Bomb(xPosition, 0);
        bombs.add(newBomb);
        getRoot().getChildren().add(newBomb);
    }

    /**
     * Moves the bombs down the screen and checks for collisions with the player.
     */
    private void moveBombs() {
        for (Bomb bomb : bombs) {
            bomb.moveDown();
            checkBombCollision(bomb);
        }
    }

    /**
     * Checks if a bomb has collided with the player.
     *
     * @param bomb the bomb to check for collision
     */
    private void checkBombCollision(Bomb bomb) {
        if (bomb.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
            getUser().takeDamage();
            getUser().handleHit();
            resetBombPosition(bomb);
        }
    }

    /**
     * Resets the position of a bomb to the top of the screen at a random x-position.
     *
     * @param bomb the bomb to reset
     */
    private void resetBombPosition(Bomb bomb) {
        bomb.setLayoutX(Math.random() * getScreenWidth());
        bomb.setLayoutY(0);
    }

    /**
     * Stops the bomb elements by stopping the bomb generation and movement timelines.
     */
    private void stopBombElements() {
        if (bombGenerationTimeline != null) {
            bombGenerationTimeline.stop();
        }
        if (bombMovementTimeline != null) {
            bombMovementTimeline.stop();
        }
    }

    /*
     * Stops the fireball sound effect.
     */
    public void stopFireballSound() {
        if (boss.getFireballSound() != null) {
            boss.getFireballSound().stop();
        }
    }

}
