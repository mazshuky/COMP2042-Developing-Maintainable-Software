package com.example.demo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public abstract class LevelParent {

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final LevelView levelView;
	private final PropertyChangeSupport support;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private final SimpleIntegerProperty currentNumberOfEnemies;
	private final ShieldImage shieldImage;
	private long lastSpawnTime;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, HeartDisplay heartDisplay) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - GameConstants.SCREEN_HEIGHT_ADJUSTMENT;

		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.shieldImage = new ShieldImage(1150, 500);
		this.user = new UserPlane(playerInitialHealth, heartDisplay, shieldImage);
		this.background = new ImageView();

		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.levelView = instantiateLevelView();
		this.support = new PropertyChangeSupport(this);
		this.currentNumberOfEnemies = new SimpleIntegerProperty(0);
		this.lastSpawnTime = 0;

		initializeBackground(backgroundImageName);
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();
	protected abstract void spawnEnemyUnits();
	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		// System.out.println("Transitioning to next level: " + levelName);
		enemyUnits.clear();
		root.getChildren().removeIf(node -> node instanceof EnemyPlane);
		support.firePropertyChange("levelChange", null, levelName);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(GameConstants.MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground(String backgroundImageName) {
		if (backgroundImageName != null) {
			var resource = getClass().getResource(backgroundImageName);
			if (resource != null) {
				background.setImage(new Image(resource.toExternalForm()));
			}
		}
		setupBackground();
	}

	private void setupBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		background.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
		root.getChildren().add(background);
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleCollisions();
		handlePenetratedEnemies();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
		user.updateGame(enemyUnits, enemyProjectiles);
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	protected long getLastSpawnTime() {
		return lastSpawnTime;
	}

	protected void setLastSpawnTime(long lastSpawnTime) {
		this.lastSpawnTime = lastSpawnTime;
	}

	protected void checkIfGameOver() {
        if (getUser().isDestroyed() || getUser().getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE) {
            goToNextLevel(GameConstants.LEVEL_TWO);
        }
	}

	private void handleKeyPress(KeyCode keyCode) {
		System.out.println("Key Pressed: " + keyCode);
		switch (keyCode) {
			case UP -> user.moveUp();
			case DOWN -> user.moveDown();
			case SPACE -> fireProjectile();
			// case S -> user.activateShield();
			case S -> {
				System.out.println("Activating shield.");
				user.activateShield();
			}
		}
	}

	private void handleKeyRelease(KeyCode keyCode) {
		if (keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) user.stop();
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void handleCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
		handleCollisions(userProjectiles, enemyUnits);
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor1 : actors1) {
			for (ActiveActorDestructible actor2 : actors2) {
				if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
					actor1.takeDamage();
					actor2.takeDamage();
				}
			}
		}
	}

	private void handlePenetratedEnemies() {
		enemyUnits.stream()
				.filter(this::enemyHasPenetratedDefenses)
				.forEach(ActiveActorDestructible::takeDamage);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		updateKillCount();
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);

		if (actors == enemyUnits) {
			int numberOfDestroyedEnemies = destroyedActors.size();
			for (int i = 0; i < numberOfDestroyedEnemies; i++) {
				getUser().incrementKillCount();
				getUser().playExplosionSound();
			}
		}
		actors.removeAll(destroyedActors);
		updateNumberOfEnemies();
		checkIfGameOver();
	}

	private void updateKillCount() {
		int kills = getCurrentNumberOfEnemies() - enemyUnits.size();
		for (int i = 0; i < kills; i++) {
			getUser().incrementKillCount();
			System.out.println("Kill count: " + getUser().getNumberOfKills());
			if (getUser().getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE) {
				System.out.println("Advancing to the next level");
				goToNextLevel(GameConstants.LEVEL_TWO);
				break;
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies.set(enemyUnits.size());
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		getUser().handleGameOver();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected ImageView getBackground() {
		return background;
	}

	protected Timeline getTimeline() {
		return timeline;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

}
