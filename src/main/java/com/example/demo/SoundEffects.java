package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;

/**
 * Utility class for loading and playing sound effects in the game.
 */
public class SoundEffects {

    private static final Logger logger = Logger.getLogger(SoundEffects.class.getName());

    /**
     * Loads an audio clip from the specified path.
     *
     * @param path  the path to the sound file
     * @param clazz the class used to load the resource
     * @return      the loaded Audioclip, or null if the sound could not be loaded
     */
    public static AudioClip loadSound(String path, Class<?> clazz) {
        try {
            var resource = clazz.getResource(path);
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
}
