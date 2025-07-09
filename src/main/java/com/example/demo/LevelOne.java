package com.example.demo;

import com.example.demo.controller.Controller;


/**
 * The {@code LevelOne} class represents the first level of the game.
 * It extends {@link LevelParent} to inherit common level functionalities
 * and manages specific behaviors pertinent to Level One, such as checking
 * for game over conditions, initializing units, and spawning enemies.
 */
public class LevelOne extends LevelParent {

	/**
	 * Constructs a new {@code LevelOne} instance with specified screen dimensions
	 * and heart display settings.
	 *
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param heartDisplay The {@link HeartDisplay} component to show player lives.
	 */
	public LevelOne(double screenHeight, double screenWidth, HeartDisplay heartDisplay, Controller controller) {
		super(GameConstants.BACKGROUND_IMAGE_ONE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay, controller);

	}

	/**
	 * Checks the game's current state to determine if the game is over, either
	 * by destruction of the User's character or by reaching the kill target, and
	 * calls appropriate methods to handle game progression or loss.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (isKillTargetReached()) {
			advanceToLevelTwo();
		}
	}

	/**
	 * Initializes the player's character or other friendly units
	 * at the start of the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		addUserToRoot();
	}

	/**
	 * Handles the logic for spawning enemy units based on time intervals
	 * and the current number of enemies.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (canSpawnNewEnemy()) {
			spawnNewEnemy();
			updateLastSpawnTime();
		}
	}

	/**
	 * Instantiates a new {@link LevelView} object to manage the
	 * visual aspects of the level.
	 *
	 * @return A {@link LevelView} instance configured for this level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Determines whether the user has achieved the required number of kills
	 * to advance to the next level.
	 *
	 * @return {@code true} if the kill target is reached; otherwise {@code false}.
	 */
	private boolean isKillTargetReached() {
		return getUser().getNumberOfKills() >= GameConstants.KILLS_TO_LEVEL_TWO;
	}

	/**
	 * Transitions the game to the next level if the user has reached the kill target.
	 */
	private void advanceToLevelTwo() {
		goToNextLevel(GameConstants.LEVEL_TWO);
	}

	/**
	 * Adds the user's character to the root group for positioning within the game screen.
	 */
	private void addUserToRoot() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Determines whether it is possible to spawn a new enemy based on the maximum
	 * allowed enemies and the time since the last spawn.
	 *
	 * @return {@code true} if a new enemy can be spawned; otherwise {@code false}.
	 */
	private boolean canSpawnNewEnemy() {
		long currentTime = System.currentTimeMillis();
		return (getCurrentNumberOfEnemies() < GameConstants.TOTAL_ENEMIES) &&
				(isTimeForNewSpawn(currentTime));
	}

	/**
	 * Determines if the required time interval has passed to allow for a new enemy spawn.
	 *
	 * @param currentTime The current system time in milliseconds.
	 * @return {@code true} if the spawn interval has elapsed; otherwise {@code false}.
	 */
	private boolean isTimeForNewSpawn(long currentTime) {
		return (currentTime - getLastSpawnTime()) >= GameConstants.SPAWN_INTERVAL_MS;
	}

	/**
	 * Spawns a new enemy at a random position on the Y-axis.
	 * This method manages adding the enemy to the game environment.
	 */
	private void spawnNewEnemy() {
		double initialYPosition = generateRandomYPosition();
		ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), initialYPosition);
		addEnemyUnit(newEnemy);
	}

	/**
	 * Generates a random starting Y-coordinate for newly spawned enemies within the allowed bounds.
	 *
	 * @return A double value representing the Y-coordinate for the new enemy spawn position.
	 */
	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

	/**
	 * Updates the record of the last enemy spawn time to the current system time.
	 */
	private void updateLastSpawnTime() {
		setLastSpawnTime(System.currentTimeMillis());
	}

}
