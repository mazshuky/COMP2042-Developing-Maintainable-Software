package com.example.demo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private static final Logger logger = Logger.getLogger(UserPlane.class.getName());

	private int velocityMultiplier;
	private int numberOfKills;
	private final HeartDisplay heartDisplay;

	private AudioClip shootSound;
	private AudioClip explosionSound;
	private AudioClip gameOverSound;

	public UserPlane(int initialHealth, HeartDisplay heartDisplay) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.heartDisplay = heartDisplay;
		initializeSounds();
	}

	private void initializeSounds() {
		this.velocityMultiplier = 0;
		this.shootSound = loadSound("/com/example/demo/sounds/userplaneshoot.wav");
		this.explosionSound = loadSound("/com/example/demo/sounds/enemyplaneexplode.wav");
		this.gameOverSound = loadSound("/com/example/demo/sounds/gameover.wav");
	}

	private AudioClip loadSound(String path) {
		try {
			var resource = getClass().getResource(path);
			if (resource != null) {
				return new AudioClip(resource.toExternalForm());
			} else {
				logger.warning("Sound file not found: " + path);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to load sound: " + path, e);
		}
		return null;
	}

	@Override
	public void updatePosition() {
		if (!isMoving()) {
			return;
		}
		double initialTranslateY = getTranslateY();
		moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
		if (isOutOfBounds()) {
			setTranslateY(initialTranslateY);
		}
	}

	private boolean isOutOfBounds() {
		double newPosition = getLayoutY() + getTranslateY();
		return newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		playSound(shootSound);
		double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectilePosition[0], projectilePosition[1]);
	}

	public void playExplosionSound() {
		playSound(explosionSound);
	}

	private void playSound(AudioClip sound) {
		if (sound != null) {
			sound.play();
		}
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
		System.out.println("Incremented kill count. Current number of kills: " + numberOfKills);
	}

	public boolean collidesWith(ActiveActorDestructible otherActor) {
		System.out.println("UserPlane bounds: " + this.getBoundsInParent());
		System.out.println("OtherActor bounds: " + otherActor.getBoundsInParent());
		boolean collision = this.getBoundsInParent().intersects(otherActor.getBoundsInParent());

		System.out.println("Collision result with " + otherActor + ": " + collision);
		return collision;
	}

	private void handleCollisions() {
		System.out.println("Collision detected. Removing a heart.");
		heartDisplay.removeHeart();
		if (heartDisplay.getRemainingHearts() == 0) {
			System.out.println("No remaining hearts. Game over.");
			handleGameOver();
		}
	}

	public void checkCollisions(List<? extends ActiveActorDestructible> enemyPlanes, List<? extends ActiveActorDestructible> enemyProjectiles) {
		for (ActiveActorDestructible enemy : enemyPlanes) {
			if (collidesWith(enemy)) {
				handleCollisions();
				break;
			}
		}

		for (ActiveActorDestructible projectile : enemyProjectiles) {
			if (collidesWith(projectile)) {
				handleCollisions();
				break;
			}
		}

	}

	public void checkProjectileCollisions(List<UserProjectile> userProjectiles, Boss boss) {
		for (UserProjectile projectile : userProjectiles) {
			if (boss.collidesWith(projectile)) {
				System.out.println("Collision detected between UserProjectile and Boss.");
				boss.takeDamage();
			}
		}
	}

	public void updateGame(List<? extends ActiveActorDestructible> enemyPlanes, List<? extends ActiveActorDestructible> enemyProjectiles, List<UserProjectile> userProjectiles, Boss boss) {
		updatePosition();
		checkCollisions(enemyPlanes, enemyProjectiles);
		checkProjectileCollisions(userProjectiles, boss);
	}

	public void handleGameOver() {
		logger.info("Game over. Playing game over sound.");
		playSound(gameOverSound);
	}

}
