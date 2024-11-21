package com.example.demo;

import java.util.List;

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

	public UserPlane(int initialHealth, HeartDisplay heartDisplay) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		this.heartDisplay = heartDisplay;
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
		double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectilePosition[0], projectilePosition[1]);
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
	}

	public boolean collidesWith(EnemyPlane enemyPlane) {
		return this.getBoundsInParent().intersects(enemyPlane.getBoundsInParent());
	}

	public boolean collidesWith(EnemyProjectile enemyProjectile) {
		return this.getBoundsInParent().intersects(enemyProjectile.getBoundsInParent());
	}

	public void checkCollisions(List<EnemyPlane> enemyPlanes, List<EnemyProjectile> enemyProjectiles) {
		for (EnemyPlane enemyPlane : enemyPlanes) {
			if (collidesWith(enemyPlane)) {
				handleCollisions();
				break;
			}
		}

		for (EnemyProjectile enemyProjectile : enemyProjectiles) {
			if (collidesWith(enemyProjectile)) {
				handleCollisions();
				break;
			}
		}
	}

	private void handleCollisions() {
		heartDisplay.removeHeart();
	}

	public void updateGame(List<EnemyPlane> enemyPlanes, List<EnemyProjectile> enemyProjectiles) {
		updatePosition();
		checkCollisions(enemyPlanes, enemyProjectiles);
	}

}
