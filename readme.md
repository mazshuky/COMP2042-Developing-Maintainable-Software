# New Java Classes
## [MainMenu.java]
The `MainMenu` class represents the game's main menu, serving as the entry point where players can either start the game
or exit to the desktop. It is responsible for setting up the graphical user interface (GUI), including displaying a background
image and a "Start Game" button. When the `showMainMenu` method is called, it initializes the GUI and plays a sound effect to
enhance the user experience.

Upon clicking the "Start Game" button, the `startGame` method is triggered. This method stops the main menu sound and calls
the `Controller` class's `launchGame` method to begin the game. The class also handles error situations, such as missing image files,
by displaying an appropriate error alert. As part of the game's controller package, the `MainMenu` class manages user interactions with the main menu.

## [CustomException.java]
The `CustomException` class handles exceptions in a structured way to handle specific errors in the game. By extending the `Exception` class,
it allows for custom error messages and the inclusion of a cause, providing more detailed context when exceptions are thrown. This approach ensures
cleaner error handling, simplifies troubleshooting, and improves the game's robustness and maintainability.

## [Bomb.java]
The Bomb class represents a bomb object in the game that appears on the screen and falls down. The bomb is visually represented by an image and has
a sound effect associated with it when it drops. This class is designed to be used in `LevelThree.java` where bombs are involved in the gameplay, adding visual
and audio effects to the bomb's movement. The bomb's image is loaded from a resource file (`/com/example/demo/images/bomb.png`) with size fixed at 70 pixels,
and the aspect ratio of the image is preserved while fitting the image to the specified size. If the image cannot be found, an error message is printed to the console.
The bomb falls down the screen by 5 pixels each time the `moveDown` method is called, creating the visual effect of a bomb descending toward the ground or a target.
The bomb’s position is controlled using the setLayoutX and setLayoutY methods of JavaFX to adjust its horizontal and vertical coordinates.
The bomb has a sound effect (`bombDropSound`) that plays every time the bomb moves down.

# Modified Java Classes
## [ActiveActor.java]
* The image is loaded with additional null checking to ensure that the program will check whether the resource actually
  exists before trying to load the image, preventing potential `NullPointerExceptions` if the resource is missing.
* The resource URL is stored in a local variable (`var resource`), which is then checked for null. The image is only set if the resource is not null.

## [ActiveActorDestructible.java]
* The `setDestroyed()` method was redundant, so it was removed to make the code cleaner and simpler. Since `isDestroyed`
  is a private field, it can be directly modified within the class.

## [Boss.java]
* The Boss class now includes an `AudioClip` to manage sounds, specifically for the fireball attack (`fireballSound`). This is initialized in
  the `initializeSounds()` method and played via `playFireballSound()` method. This abstract sound initialization and playback, keeping the class
  focused on its core responsibilities, such as attacking.
* The shield behavior has been encapsulated into clearly defined methods (`shieldShouldBeActivated`, `shieldExhausted`, `activateShield`, `deactivateShield`).
  This refactor improves readability and makes the shield mechanics easier to test and modify without impacting other aspects of the class.
* The introduction of distinct methods, such as `updatePosition()` and `updateShield()`, ensures that each method is responsible for a single concern.
  This separation enhances the clarity of the class and makes it easier to tweak or extend specific behaviors (e.g., movement, attack, or shield).
* The `resetMovePattern()` method was introduced to manage the movement pattern and reset the boss’s position when it goes out of bounds
  or needs to change its behavior. This reduces code duplication and further clarifies the boss’s movement logic.