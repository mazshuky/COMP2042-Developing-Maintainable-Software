package com.example.demo;

public class LevelOne extends LevelParent {
	public LevelOne(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(GameConstants.BACKGROUND_IMAGE_ONE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
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
	}

	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

}
