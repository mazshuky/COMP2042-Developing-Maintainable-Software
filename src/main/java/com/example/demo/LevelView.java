package com.example.demo;

import javafx.scene.Group;

// Class representing the view of the game level
public class LevelView {
	
	public static final double HEART_DISPLAY_X_POSITION = GameConstants.HEART_DISPLAY_X_POSITION;
	public static final double HEART_DISPLAY_Y_POSITION = GameConstants.HEART_DISPLAY_Y_POSITION;
	public static final int WIN_IMAGE_X_POSITION = GameConstants.WIN_IMAGE_X_POSITION;
	public static final int WIN_IMAGE_Y_POSITION = GameConstants.WIN_IMAGE_Y_POSITION;
	public static final int LOSS_SCREEN_X_POSITION = GameConstants.LOSS_SCREEN_X_POSITION;
	public static final int LOSS_SCREEN_Y_POSITION = GameConstants.LOSS_SCREEN_Y_POSITION;
	public static final int LOSS_SCREEN_WIDTH = GameConstants.LOSS_SCREEN_WIDTH;
	public static final int LOSS_SCREEN_HEIGHT = GameConstants.LOSS_SCREEN_HEIGHT;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new LevelView
	 *
	 * @param root              the root group for the scene
	 * @param heartsToDisplay   the initial number of hearts to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION, LOSS_SCREEN_WIDTH, LOSS_SCREEN_HEIGHT);
	}

	// Shows the heart display on the screen
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	// Displays the win image on the screen
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	// Displays the game over image on the screen
	public void showGameOverImage() {
	    if (!root.getChildren().contains(gameOverImage)) {
	        root.getChildren().add(gameOverImage);
	    }
	}

	/**
	 * Removes hearts from the heart display based on remaining hearts
	 *
	 * @param heartsRemaining   the number of hearts remaining to be displayed
	 */
	public void removeHearts(int heartsRemaining) {
		heartsRemaining = Math.max(heartsRemaining, 0); // Ensure non-negative
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
