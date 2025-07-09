package com.example.demo;

import javafx.scene.Group;

/**
 * This class is responsible for displaying the third level of the game.
 * It extends the LevelView class and adds a shield image and a boss health bar to the root.
 */
public class LevelViewLevelThree extends LevelView {

    private static final int SHIELD_X_POSITION = 500;
    private static final int SHIELD_Y_POSITION = 500;
    private final Group root;
    private final ShieldImage shieldImage;
    private final BossHealth bossHealthBar;

    /**
     * Constructor for LevelViewLevelThree.
     *
     * @param root            the root of the scene
     * @param heartsToDisplay the number of hearts to display
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
        this.bossHealthBar = new BossHealth();
        addImagesToRoot();
    }

    /**
     * Adds the shield image and boss health bar to the root container.
     */
    private void addImagesToRoot() {
        root.getChildren().addAll(shieldImage);
        root.getChildren().addAll(bossHealthBar.getBossHealthBar());
    }

    /**
     * Updates the boss health bar with the current health value.
     *
     * @param health the current health value of the boss
     */
    public void updateBossHealth(double health) {
        bossHealthBar.update(health);
    }

    /**
     * Gets the shield image for the boss.
     *
     * @return the ShieldImage object representing the boss's shield.
     */
    public ShieldImage getShieldImage() {
        return shieldImage;
    }

}
