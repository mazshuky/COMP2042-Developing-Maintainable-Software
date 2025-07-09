package com.example.demo;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class SoundEffectsTest {

    @Test
    void testSoundFilesExist() {
        var resource = getClass().getResource("/com/example/demo/sounds");
        assertNotNull(resource, "The sound directory resource should not be null");

        File soundDir = new File(resource.getFile());
        assertTrue(soundDir.exists() && soundDir.isDirectory(), "The sound directory should exist");

        File[] soundFiles = soundDir.listFiles();
        assertNotNull(soundFiles, "The sound directory should contain files");
        assertTrue(soundFiles.length > 0, "The sound directory should not be empty");

        for (File soundFile : soundFiles) {
            assertTrue(soundFile.isFile(), "Each entry in the sound directory should be a file");
            assertTrue(soundFile.length() > 0, "Each sound file should not be empty");
        }
    }
}