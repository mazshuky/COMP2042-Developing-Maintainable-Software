package com.example.demo;

import com.example.demo.controller.Controller;

/**
 * LevelTwo is the second level of the game. It is a subclass of LevelParent and
 * contains the logic for the second level of the game. It contains a boss unit
 * that the player must defeat to advance to the next level.
 */
public class LevelTwo extends LevelParent {

	private final Boss boss;
	private final LevelViewLevelTwo levelView;

	/**
	 * Constructor for LevelTwo. Initializes the boss unit and the level view.
	 *
	 * @param screenHeight  the height of the screen
	 * @param screenWidth   the width of the screen
	 * @param heartDisplay  the display for the player's health
	 * @param controller    the controller for the game
	 */
	public LevelTwo(double screenHeight, double screenWidth, HeartDisplay heartDisplay, Controller controller) {
		super(GameConstants.BACKGROUND_IMAGE_TWO, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay, controller);
		this.boss = initializeBoss();
		this.levelView = new LevelViewLevelTwo(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
		bindShieldToBoss();
	}

	/**
	 * Initializes friendly units by adding the player to the root.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		addPlayerToRoot();
	}

	/**
	 * Checks if the game is over by determining if the user or the boss is destroyed.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			advanceToLevelThree();
		}
	}

	/**
	 * Advances the game to level three.
	 */
	private void advanceToLevelThree() {
		goToNextLevel(GameConstants.LEVEL_THREE);
	}

	/**
	 * Spawns enemy units by adding the boss to the root if no enemies are on screen.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (isNoEnemiesOnScreen()) {
			addBossToRoot();
		}
	}

	/**
	 * Instantiates the level view for level two.
	 *
	 * @return the instantiated level view
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Starts the game by requesting focus for the background and starting the timeline.
	 */
	@Override
	public void startGame() {
		requestFocusForBackground();
		startTimeline();
	}

	/**
	 * Updates the level view, including the boss's shield and health display.
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
	 * Binds the shield image's position to the boss's coordinates.
	 */
	private void bindShieldToBoss() {
		double xOffset = 600;
		double yOffset = -40;

		levelView.getShieldImage().translateXProperty().bind(boss.translateXProperty().add(xOffset));
		levelView.getShieldImage().translateYProperty().bind(boss.translateYProperty().add(yOffset));
	}

	/**
	 * Initializes the boss for level two.
	 *
	 * @return the initialized boss
	 */
	private Boss initializeBoss() {
		return new Boss();
	}

	/**
	 * Adds the player to the root.
	 */
	private void addPlayerToRoot() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if there are no enemies on screen.
	 *
	 * @return true if there are no enemies on screen, false otherwise
	 */
	private boolean isNoEnemiesOnScreen() {
		return getCurrentNumberOfEnemies() == 0;
	}

	/**
	 * Adds the boss to the root.
	 */
	private void addBossToRoot() {
		addEnemyUnit(boss);
	}

	/**
	 * Requests focus for the background.
	 */
	private void requestFocusForBackground() {
		getBackground().requestFocus();
	}

	/**
	 * Starts the game's timeline.
	 */
	private void startTimeline() {
		getTimeline().play();
	}
}

