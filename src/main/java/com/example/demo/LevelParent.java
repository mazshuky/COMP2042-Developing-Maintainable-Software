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

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private final LevelView levelView;
	private final PropertyChangeSupport support;
	private final SimpleIntegerProperty currentNumberOfEnemies;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, HeartDisplay heartDisplay) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, heartDisplay);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - GameConstants.SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = new SimpleIntegerProperty(0);
		this.support = new PropertyChangeSupport(this);

		initializeBackground(backgroundImageName);
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();
	protected abstract void checkIfGameOver();
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
		if ("LevelTwo".equals(levelName)) {
			LevelTwo levelTwo = new LevelTwo(screenHeight, screenWidth, user.getHeartDisplay());
			Scene levelTwoScene = levelTwo.initializeScene();
			setScene(levelTwoScene);
			levelTwo.startGame();
		}
		support.firePropertyChange("levelChange", null, levelName); // Notify listeners of level change
	}

	public void setScene(Scene scene) {
		this.scene.setRoot(scene.getRoot());
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
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

		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		background.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
		root.getChildren().add(background);
	}

	private void handleKeyPress(KeyCode keyCode) {
		if (keyCode == KeyCode.UP) user.moveUp();
		if (keyCode == KeyCode.DOWN) user.moveDown();
		if (keyCode == KeyCode.SPACE) fireProjectile();
	}

	private void handleKeyRelease(KeyCode keyCode) {
		if (keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) user.stop();
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
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

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
		updateNumberOfEnemies();
		updateKillCount();
	}

	private void handleCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
		handleCollisions(userProjectiles, enemyUnits);
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handlePenetratedEnemies() {
		List<ActiveActorDestructible> penetratedEnemies = enemyUnits.stream()
				.filter(this::enemyHasPenetratedDefenses)
				.toList();
		for (ActiveActorDestructible enemy : penetratedEnemies) {
			enemy.takeDamage();
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies.get() - enemyUnits.size(); i++) {
			user.incrementKillCount();
			if (user.getNumberOfKills() >= GameConstants.KILLS_TO_ADVANCE) {
				goToNextLevel(GameConstants.NEXT_LEVEL);
			}
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
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

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies.set(enemyUnits.size());
	}
}
