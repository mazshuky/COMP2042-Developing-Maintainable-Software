package com.example.demo;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget())
			goToNextLevel(NEXT_LEVEL);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		int enemiesToSpawn = TOTAL_ENEMIES - currentNumberOfEnemies;
		for (int i = 0; i < enemiesToSpawn; i++) {
			if (shouldSpawnEnemy()) {
				double initialYPosition = generateRandomYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), initialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	private boolean shouldSpawnEnemy() {
		return Math.random() < ENEMY_SPAWN_PROBABILITY;
	}

	private double generateRandomYPosition() {
		return Math.random() * getEnemyMaximumYPosition();
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
