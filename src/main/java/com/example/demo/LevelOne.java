package com.example.demo;

public class LevelOne extends LevelParent {

	public LevelOne(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(GameConstants.BACKGROUND_IMAGE_ONE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay);
	}

	// check again
	@Override
	protected void checkIfGameOver() {
		System.out.println("checkIfGameOver called");
		if (userIsDestroyed()) {
			System.out.println("User is destroyed");
			loseGame();
		} else if (userHasReachedKillTarget()) {
			System.out.println("User has reached kill target condition met");
			handleUserReachedKillTarget();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		addUserToRoot();
	}

	@Override
	protected void spawnEnemyUnits() {
		if (canSpawnMoreEnemies()) {
			if (shouldSpawnEnemy()) {
				spawnEnemy();
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		System.out.println("Number of kills: " + getUser().getNumberOfKills());
		System.out.println("Kills required to advance: " + GameConstants.KILLS_TO_ADVANCE);
		return getUser().getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE;
	}

	private void handleUserReachedKillTarget() {
		System.out.println("User has reached kill target");
		goToNextLevel(GameConstants.LEVEL_TWO);
	}

	private void addUserToRoot() {
		getRoot().getChildren().add(getUser());
	}

	private boolean canSpawnMoreEnemies() {
		return getCurrentNumberOfEnemies() < GameConstants.TOTAL_ENEMIES;
	}

	private boolean shouldSpawnEnemy() {
		return Math.random() < GameConstants.ENEMY_SPAWN_PROBABILITY;
	}

	private void spawnEnemy() {
		double initialYPosition = generateRandomYPosition();
		ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), initialYPosition);
		addEnemyUnit(newEnemy);
		System.out.println("Enemy spawned. Current enemy count: " + getCurrentNumberOfEnemies());
	}

	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

}
