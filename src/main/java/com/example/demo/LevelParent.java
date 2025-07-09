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

import com.example.demo.controller.Controller;

/**
 * The {@code LevelParent} class is an abstract class that represents a level in the game.
 * It contains common functionalities and properties that are shared by all levels, such as
 * the game screen dimensions, the user's character, and the timeline.
 * It also provides methods for updating the game scene, handling collisions, and managing
 * the game state.
 */
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
	private final Controller controller;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> bombs = new ArrayList<>();

	private final SimpleIntegerProperty currentNumberOfEnemies;
	private long lastSpawnTime;

	/**
	 * Constructs a new {@code LevelParent} instance with the specified background image,
	 * screen dimensions, player initial health, heart display, and controller.
	 *
	 * @param backgroundImageName the name of the background image file
	 * @param screenHeight        the height of the game screen
	 * @param screenWidth         the width of the game screen
	 * @param playerInitialHealth the initial health of the player character
	 * @param heartDisplay        the {@link HeartDisplay} component to show player lives
	 * @param controller          the {@link Controller} instance to handle user input
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, HeartDisplay heartDisplay, Controller controller) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.controller = controller;
		this.enemyMaximumYPosition = screenHeight - GameConstants.SCREEN_HEIGHT_ADJUSTMENT;

		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, heartDisplay);
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

	/**
	 * Initializes the game scene by adding the player character.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Spawns enemy units based on time intervals and the current number of enemies.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates the level view.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene.
	 *
	 * @return the scene
	 */
	public Scene initializeScene() {
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by setting the focus on the background and playing the timeline.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Goes to the next level in the game.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToNextLevel(String levelName) {
		enemyUnits.clear();
		root.getChildren().removeIf(node -> node instanceof EnemyPlane);
		support.firePropertyChange("levelChange", null, levelName);
	}

	/**
	 * Adds a property change listener to the level.
	 *
	 * @param listener the property change listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a property change listener from the level.
	 *
	 * @param listener the property change listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * Initializes the timeline for the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(GameConstants.MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image for the game scene.
	 *
	 * @param backgroundImageName the name of the background image file
	 */
	private void initializeBackground(String backgroundImageName) {
		if (backgroundImageName != null) {
			var resource = getClass().getResource(backgroundImageName);
			if (resource != null) {
				background.setImage(new Image(resource.toExternalForm()));
			}
		}
		setupBackground();
	}

	/**
	 * Sets up the background image for the game scene.
	 */
	private void setupBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		background.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
		root.getChildren().add(background);
		howToPlay();
	}

	/**
	 * Updates the game scene by spawning enemy units, updating actors, generating enemy fire,
	 * handling collisions, handling penetrated enemies, removing destroyed actors, updating the level view,
	 * and checking if the game is over.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleCollisions();
		handlePenetratedEnemies();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Updates the actors in the game scene.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Generates enemy fire by spawning enemy projectiles.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns a new enemy projectile in the game scene.
	 *
	 * @param projectile the enemy projectile to spawn
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Returns the last spawn time of an enemy unit.
	 *
	 * @return the last spawn time of an enemy unit
	 */
	protected long getLastSpawnTime() {
		return lastSpawnTime;
	}

	/**
	 * Sets the last spawn time of an enemy unit.
	 *
	 * @param lastSpawnTime the last spawn time of an enemy unit
	 */
	protected void setLastSpawnTime(long lastSpawnTime) {
		this.lastSpawnTime = lastSpawnTime;
	}

	/**
	 * Checks if the game is over based on the user's health and number of kills.
	 */
	protected void checkIfGameOver() {
        if (getUser().isDestroyed() || getUser().getNumberOfKills() >= GameConstants.KILLS_TO_LEVEL_TWO) {
            goToNextLevel(GameConstants.LEVEL_TWO);
        }
	}

	/**
	 * Handles key press events for user input.
	 *
	 * @param keyCode the key code of the pressed key
	 */
	private void handleKeyPress(KeyCode keyCode) {
		switch (keyCode) {
			case UP -> user.moveUp();
			case DOWN -> user.moveDown();
			case LEFT -> user.moveLeft();
			case RIGHT -> user.moveRight();
			case SPACE -> fireProjectile();
		}
	}

	/**
	 * Handles key release events for user input.
	 *
	 * @param keyCode the key code of the released key
	 */
	private void handleKeyRelease(KeyCode keyCode) {
		switch (keyCode) {
			case UP, DOWN, LEFT, RIGHT -> user.stop();
		}
	}

	/**
	 * Fires a projectile in the game scene.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Handles collisions between actors in the game scene.
	 */
	private void handleCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
		handleCollisions(userProjectiles, enemyUnits);
		handleCollisions(enemyProjectiles, friendlyUnits);
		handleCollisions(List.of(user), bombs);
	}

	/**
	 * Handles collisions between two lists of actors.
	 *
	 * @param actors1 the first list of actors
	 * @param actors2 the second list of actors
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor1 : actors1) {
			for (ActiveActorDestructible actor2 : actors2) {
				if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
					actor1.takeDamage();
					actor2.takeDamage();

					if (actor1 instanceof UserPlane) {
						((UserPlane) actor1).handleHit();
					} else if (actor2 instanceof UserPlane) {
						((UserPlane) actor2).handleHit();
					}
				}
			}
		}
	}

	/**
	 * Handles enemies that have penetrated defenses.
	 */
	private void handlePenetratedEnemies() {
		enemyUnits.stream()
				.filter(this::enemyHasPenetratedDefenses)
				.forEach(ActiveActorDestructible::takeDamage);
	}

	/**
	 * Removes all destroyed actors from the game scene.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		updateKillCount();
	}

	/**
	 * Removes destroyed actors from a list of actors.
	 *
	 * @param actors the list of actors to remove destroyed actors from
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed).toList();
		root.getChildren().removeAll(destroyedActors);

		if (actors == enemyUnits) {
			for (ActiveActorDestructible destroyedActor : destroyedActors) {
				getUser().incrementKillCount();
				if (destroyedActor instanceof EnemyPlane) {
					((EnemyPlane) destroyedActor).playEnemyDestroySound();
				}
			}
		}
		actors.removeAll(destroyedActors);
		updateNumberOfEnemies();
		checkIfGameOver();
	}

	/**
	 * Updates the kill count of the user based on the number of destroyed enemies.
	 */
	private void updateKillCount() {
		int kills = getCurrentNumberOfEnemies() - enemyUnits.size();
		for (int i = 0; i < kills; i++) {
			getUser().incrementKillCount();
			System.out.println("Kill count: " + getUser().getNumberOfKills());
			if (getUser().getNumberOfKills() >= GameConstants.KILLS_TO_LEVEL_TWO) {
				System.out.println("Advancing to the next level");
				goToNextLevel(GameConstants.LEVEL_TWO);
				break;
			}
		}
	}

	/**
	 * Updates the level view based on the game state.
	 */
	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Determines whether an enemy has penetrated defenses.
	 *
	 * @param enemy the enemy to check
	 * @return {@code true} if the enemy has penetrated defenses; otherwise {@code false}
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/*
	 * Updates the number of enemies in the game scene.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies.set(enemyUnits.size());
	}

	/**
	 * Handles the win condition of the game.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		getUser().handleWin(levelView.getWinImage());
	}

	/**
	 * Handles the lose condition of the game.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		getUser().handleGameOver();
	}

	/**
	 * Displays how to play the game.
	 */
	private void howToPlay() {
		var resource = getClass().getResource(GameConstants.GAME_TUTORIAL);
		if (resource != null) {
			ImageView tutorialImage = new ImageView(new Image(resource.toExternalForm()));
			tutorialImage.setFitWidth(50);
			tutorialImage.setFitHeight(50);
			tutorialImage.setLayoutX(screenWidth - 110);
			tutorialImage.setLayoutY(screenHeight - 110);
			tutorialImage.setOnMouseClicked(event -> showTutorial());
			root.getChildren().add(tutorialImage);
		}
	}

	/**
	 * Shows the game tutorial.
	 */
	private void showTutorial() {
		System.out.println("Tutorial clicked! Show the tutorial here.");
		GameTutorial tutorialScreen = new GameTutorial(controller);
		tutorialScreen.showTutorial();
	}

	/**
	 * Pauses the game.
	 */
	public void pause() {
		timeline.pause();
	}

	/**
	 * Resumes the game.
	 */
	public void resume() {
		timeline.play();
	}

	/**
	 * Gets the user plane.
	 *
	 * @return the user plane
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Gets the root group of the game scene.
	 *
	 * @return the root group
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the background image view of the game scene.
	 *
	 * @return the background image view
	 */
	protected ImageView getBackground() {
		return background;
	}

	/**
	 * Gets the timeline of the game loop.
	 *
	 * @return the timeline
	 */
	protected Timeline getTimeline() {
		return timeline;
	}

	/**
	 * Gets the current number of enemies in the game scene.
	 *
	 * @return the current number of enemies
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the game scene.
	 *
	 * @param enemy the enemy unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Generates a random Y position for an enemy unit.
	 *
	 * @return a random Y position for an enemy unit
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the screen width of the game scene.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user is destroyed.
	 *
	 * @return true if the user is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

}
