package com.example.demo;

import javafx.scene.Group;

/**
 * The {@code LevelView} class manages the visual components for a game level,
 * including displaying hearts, win, and game-over images. It interacts with
 * a JavaFX {@link Group} to render these components on the screen.
 */
public class LevelView {

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new {@code LevelView} instance with specified initial settings.
	 *
	 * @param root            the root {@link Group} to which game components are added
	 * @param heartsToDisplay the initial number of heart icons to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = setUpHeartDisplay(heartsToDisplay);
		this.winImage = createWinImage();
		this.gameOverImage = createGameOverImage();
	}

	/**
	 * Configures the heart display with the given number of hearts.
	 *
	 * @param heartsToDisplay the number of hearts to initially display
	 * @return a configured {@link HeartDisplay} instance
	 */
	private HeartDisplay setUpHeartDisplay(int heartsToDisplay) {
		return new HeartDisplay(GameConstants.HEART_DISPLAY_X_POSITION, GameConstants.HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	/**
	 * Creates the win image component.
	 *
	 * @return a configured {@link WinImage} instance
	 */
	private WinImage createWinImage() {
		return new WinImage(GameConstants.WIN_IMAGE_X_POSITION, GameConstants.WIN_IMAGE_Y_POSITION);
	}

	/**
	 * Creates the game-over image component.
	 *
	 * @return a configured {@link GameOverImage} instance
	 */
	private GameOverImage createGameOverImage() {
		return new GameOverImage(GameConstants.LOSS_SCREEN_X_POSITION, GameConstants.LOSS_SCREEN_Y_POSITION, GameConstants.LOSS_SCREEN_WIDTH, GameConstants.LOSS_SCREEN_HEIGHT);
	}

	/**
	 * Displays the heart icons on the screen by adding them to the root group.
	 */
	public void showHeartDisplay() {
		if (!root.getChildren().contains(heartDisplay.getContainer())) {
			root.getChildren().add(heartDisplay.getContainer());
		}
	}

	/**
	 * Displays the win image on the screen.
	 */
	public void showWinImage() {
		if (!root.getChildren().contains(winImage)) {
			root.getChildren().add(winImage);
			winImage.showWinImage();
		}
	}

	/**
	 * Displays the game-over image on the screen.
	 */
	public void showGameOverImage() {
	    if (!root.getChildren().contains(gameOverImage)) {
	        root.getChildren().add(gameOverImage);
	    }
	}

	/**
	 * Removes the win image from the screen.
	 */
	public void removeHearts(int heartsRemaining) {
		heartsRemaining = Math.max(heartsRemaining, 0);
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public WinImage getWinImage() {
		return winImage;
	}

}
