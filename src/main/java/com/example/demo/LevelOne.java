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
		long currentTime = System.currentTimeMillis();
		if (canSpawnMoreEnemies() && shouldSpawnEnemy(currentTime)) {
			spawnEnemy();
			setLastSpawnTime(currentTime);
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
		goToNextLevel(GameConstants.LEVEL_TWO);
	}

	private void addUserToRoot() {
		getRoot().getChildren().add(getUser());
	}

	private boolean canSpawnMoreEnemies() {
		return getCurrentNumberOfEnemies() < GameConstants.TOTAL_ENEMIES;
	}

	private boolean shouldSpawnEnemy(long currentTime) {
		return (currentTime - getLastSpawnTime()) >= GameConstants.SPAWN_INTERVAL_MS;
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
