package com.example.demo;

import javafx.scene.Group;

/**
 * This class is responsible for displaying the second level of the game.
 * It extends the LevelView class and adds a shield image and a boss health bar to the root.
 */
public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 500;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealth bossHealthBar;

	/**
	 * Constructs a LevelViewLevelTwo object.
	 *
	 * @param root            the root group to which the level's elements are added
	 * @param heartsToDisplay the number of hearts to display for the player
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthBar = new BossHealth();
		addImagesToRoot();
	}

	/**
	 * Adds the shield image and boss health bar to the root group.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
		root.getChildren().addAll(bossHealthBar.getBossHealthBar());
	}

	/**
	 * Updates the boss's health bar.
	 *
	 * @param health the current health of the boss as a percentage
	 */
	public void updateBossHealth(double health) {
		bossHealthBar.update(health);
	}

	/**
	 * Get the shield image.
	 *
	 * @return the shield image
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

}
