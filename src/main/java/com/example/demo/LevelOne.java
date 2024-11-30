package com.example.demo;

public class LevelOne extends LevelParent {

	public LevelOne(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(GameConstants.BACKGROUND_IMAGE_ONE, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (isKillTargetReached()) {
			advanceToNextLevel();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		addUserToRoot();
	}

	@Override
	protected void spawnEnemyUnits() {
		if (canSpawnNewEnemy()) {
			spawnNewEnemy();
			updateLastSpawnTime();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
	}

	private boolean isKillTargetReached() {
		return getUser().getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE;
	}

	private void advanceToNextLevel() {
		goToNextLevel(GameConstants.LEVEL_TWO);
	}

	private void addUserToRoot() {
		getRoot().getChildren().add(getUser());
	}

	private boolean canSpawnNewEnemy() {
		long currentTime = System.currentTimeMillis();
		return (getCurrentNumberOfEnemies() < GameConstants.TOTAL_ENEMIES) &&
				(isTimeForNewSpawn(currentTime));
	}

	private boolean isTimeForNewSpawn(long currentTime) {
		return (currentTime - getLastSpawnTime()) >= GameConstants.SPAWN_INTERVAL_MS;
	}

	private void spawnNewEnemy() {
		double initialYPosition = generateRandomYPosition();
		ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), initialYPosition);
		addEnemyUnit(newEnemy);
	}

	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

	private void updateLastSpawnTime() {
		setLastSpawnTime(System.currentTimeMillis());
	}

}
