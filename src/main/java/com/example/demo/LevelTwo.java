package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_TWO = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private final LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth, HeartDisplay heartDisplay) {
		super(BACKGROUND_IMAGE_TWO, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, heartDisplay);
		this.boss = initializeBoss();
		this.levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		bindShieldToBoss();
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

	private void bindShieldToBoss() {
		levelView.getShieldImage().translateXProperty().bind(boss.translateXProperty());
		levelView.getShieldImage().translateYProperty().bind(boss.translateYProperty());
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

