package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 100;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
	private static final Logger logger = Logger.getLogger(EnemyPlane.class.getName());

	private AudioClip explosionSound;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		initializeSounds();
	}

	private void initializeSounds() {
		this.explosionSound = loadSound("/com/example/demo/sounds/enemyplaneexplode.wav");
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

	private void playSound(AudioClip sound) {
		if (sound != null) {
			sound.play();
		}
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double[] projectilePosition = getProjectilePosition(PROJECTILE_X_POSITION_OFFSET, PROJECTILE_Y_POSITION_OFFSET);
			double projectileXPosition = projectilePosition[0];
			double projectileYPosition = projectilePosition[1];
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	public void playExplosionSound() {
		playSound(explosionSound);
	}


}
