package com.example.demo;

import java.util.List;
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

	private int velocityMultiplier;
	private int numberOfKills;
	private final HeartDisplay heartDisplay;
	private AudioClip shootSound;
	private AudioClip explosionSound;
	private AudioClip gameOverSound;

	public UserPlane(int initialHealth, HeartDisplay heartDisplay) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.heartDisplay = heartDisplay;
		initUserPlane();
	}

	private void initUserPlane() {
		velocityMultiplier = 0;
		loadShootSound();
		loadExplosionSound();
		loadGameOverSound();
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
		if (shootSound != null) {
			shootSound.play();
		}
		double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectilePosition[0], projectilePosition[1]);
	}

	private void loadShootSound() {
		try {
			var resource = getClass().getResource("/com/example/demo/sounds/userplaneshoot.wav");
			if (resource != null) {
				shootSound = new AudioClip(resource.toExternalForm());
			} else {
				System.err.println("Sound file not found: /com/example/demo/sounds/userplaneshoot.wav");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadExplosionSound() {
		try {
			var resource = getClass().getResource("/com/example/demo/sounds/enemyplaneexplode.wav");
			if (resource != null) {
				explosionSound = new AudioClip(resource.toExternalForm());
			} else {
				System.err.println("Sound file not found: /com/example/demo/sounds/enemyplaneexplode.wav");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadGameOverSound() {
		try {
			var resource = getClass().getResource("/com/example/demo/sounds/gameover.wav");
			if (resource != null) {
				explosionSound = new AudioClip(resource.toExternalForm());
			} else {
				System.err.println("Sound file not found: /com/example/demo/sounds/gameover.wav");
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public void playExplosionSound() {
		if (explosionSound != null) {
			explosionSound.play();
		}
	}

	public boolean collidesWith(ActiveActorDestructible otherActor) {
		return this.getBoundsInParent().intersects(otherActor.getBoundsInParent());
	}

	private void handleCollisions() {
		heartDisplay.removeHeart();
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

	public void updateGame(List<? extends ActiveActorDestructible> enemyPlanes, List<? extends ActiveActorDestructible> enemyProjectiles) {
		updatePosition();
		checkCollisions(enemyPlanes, enemyProjectiles);
	}

}
