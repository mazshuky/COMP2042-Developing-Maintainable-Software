package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_TWO = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	public LevelTwo(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(BACKGROUND_IMAGE_TWO, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, heartDisplay);
		this.boss = initializeBoss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		addPlayerToRoot();
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (isNoEnemiesOnScreen()) {
			addBossToRoot();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	@Override
	public void startGame() {
		requestFocusForBackground();
		startTimeline();
	}

	private Boss initializeBoss() {
		return new Boss();
	}

	private void addPlayerToRoot() {
		getRoot().getChildren().add(getUser());
	}

	private boolean isNoEnemiesOnScreen() {
		return getCurrentNumberOfEnemies() == 0;
	}

	private void addBossToRoot() {
		addEnemyUnit(boss);
	}

	private void requestFocusForBackground() {
		getBackground().requestFocus();
	}

	private void startTimeline() {
		getTimeline().play();
	}
}
