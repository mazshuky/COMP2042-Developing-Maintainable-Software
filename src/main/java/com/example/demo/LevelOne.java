package com.example.demo;

public class LevelOne extends LevelParent {

	public LevelOne(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(GameConstants.BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, GameConstants.PLAYER_INITIAL_HEALTH, heartDisplay);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			goToNextLevel(GameConstants.NEXT_LEVEL);
		}
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE;
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		int enemiesToSpawn = GameConstants.TOTAL_ENEMIES - currentNumberOfEnemies;
		for (int i = 0; i < enemiesToSpawn; i++) {
			if (shouldSpawnEnemy()) {
				double initialYPosition = generateRandomYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), initialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	private boolean shouldSpawnEnemy() {
		return Math.random() < GameConstants.ENEMY_SPAWN_PROBABILITY;
	}

	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), GameConstants.PLAYER_INITIAL_HEALTH);
	}
}
