package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 500;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealth bossHealthBar;

	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthBar = new BossHealth();
		addImagesToRoot();
	}

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
		root.getChildren().addAll(bossHealthBar.getBossHealthBar());
		//root.getChildren().addAll(bossHealthBar.getBossHealthBar(), bossHealthBar.getBossHealthLabel());
	}

	public void updateBossHealth(double health) {
		bossHealthBar.update(health);
	}

	public ShieldImage getShieldImage() {
		return shieldImage;
	}

}
