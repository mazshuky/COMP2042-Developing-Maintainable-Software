package com.example.demo;

import javafx.scene.Group;

public class LevelView {

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = setUpHeartDisplay(heartsToDisplay);
		this.winImage = createWinImage();
		this.gameOverImage = createGameOverImage();
	}

	private HeartDisplay setUpHeartDisplay(int heartsToDisplay) {
		return new HeartDisplay(GameConstants.HEART_DISPLAY_X_POSITION, GameConstants.HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	private WinImage createWinImage() {
		return new WinImage(GameConstants.WIN_IMAGE_X_POSITION, GameConstants.WIN_IMAGE_Y_POSITION);
	}

	private GameOverImage createGameOverImage() {
		return new GameOverImage(GameConstants.LOSS_SCREEN_X_POSITION, GameConstants.LOSS_SCREEN_Y_POSITION, GameConstants.LOSS_SCREEN_WIDTH, GameConstants.LOSS_SCREEN_HEIGHT);
	}

	public void showHeartDisplay() {
		if (!root.getChildren().contains(heartDisplay.getContainer())) {
			root.getChildren().add(heartDisplay.getContainer());
		}
	}

	public void showWinImage() {
		if (!root.getChildren().contains(winImage)) {
			root.getChildren().add(winImage);
			winImage.showWinImage();
		}
	}

	public void showGameOverImage() {
	    if (!root.getChildren().contains(gameOverImage)) {
	        root.getChildren().add(gameOverImage);
	    }
	}

	public void removeHearts(int heartsRemaining) {
		heartsRemaining = Math.max(heartsRemaining, 0);
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
