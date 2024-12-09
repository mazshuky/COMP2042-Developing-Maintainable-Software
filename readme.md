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

## [BossHealth.java]
The `BossHealth` class is a custom implementation for managing and displaying the health of a boss character in a game using JavaFX.
It is specifically designed to provide an intuitive visual representation of the boss's health through a progress bar, which dynamically
updates as the boss takes damage or heals. The class extends `ProgressBar`, a JavaFX control, and initializes it with specific properties,
including its size, position, and initial progress value (set to 100% health by default). A method `update(double health)` allows other parts
of the game to update the boss's health dynamically. The health is represented as a floating-point value between 0.0 (no health) and 1.0 (full health).

In addition, the health bar is placed at a fixed position on the screen (x: 1070, y: 20) and has a specified width (200 pixels) and height (25 pixels).
This location is likely chosen to fit within the user interface of the game, typically near the top-right corner or another prominent area.
The appearance of the health bar can be modified by color (`-fx-accent: green;`), allowing for visual feedback on the boss's health status.

## GameConstants.java
## GameTutorial.java
## GameTutorialController.java
## SoundEffects.java
## LevelThree.java
## LevelViewLevelThree.java

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

## [BossProjectile.java]
* The primary change is the modification of the `IMAGE_HEIGHT` constant from 75 to 30. This change affects the visual appearance
  of the `BossProjectile`, making it smaller and for aesthetic reasons.

## [EnemyPlane.java]
* Sound effects are introduced in the modified class. The class now includes `AudioClip` variables to store the sound effects for when
  the enemy plane is destroyed (`enemyDestroyedSound`) and when it fires a projectile (`fireProjectileSound`).
* A private method `initializeSounds()` is added to load the sound files. This method ensures that the sound effects are loaded
  correctly and provides a centralised location for managing sound resources.
* A private `playSound()` method is introduced to play the sound effects.
* A `playEnemyDestroySound()` method is added to play the sound effect when an enemy plane is destroyed. This method is called
  when the enemy plane is destroyed, providing auditory feedback to the player.
* The code now uses `getProjectilePosition(PROJECTILE_X_POSITION_OFFSET, PROJECTILE_Y_POSITION_OFFSET)`, which is assumed
  to return both X and Y coordinates as a `double[]`. The change to using a single method `getProjectilePosition()`
  that returns both X and Y coordinates as an array improves code clarity and reduces redundancy.

## [FighterPlane.java]
* The `takeDamage()` method now delegates the health reduction to a helper method `decreaseHealth()`, ensuring that health does
  not go below zero using `Math.max()`.
* The damage amount is stored in a constant `DAMAGE_AMOUNT`, which is used in the `decreaseHealth()` method for improved maintainability and consistency.
* The `setHealth()` method was added to allow setting a new health value and improves flexibility.
* The `healthAtZero()` method has been removed as it was redundant. The check for `health == 0` is straightforward and only used once,
  so it was replaced with a direct condition (`health <= 0`) to simplify the code and eliminate unnecessary overhead.
* The two methods `getProjectileXPosition()` and `getProjectileYPosition()` are now combined into a single method `getProjectilePosition()`.
  This reduces code duplication, improves efficiency, and returns both X and Y positions in a single array, making it easier to use
  them together when spawning projectiles or performing other actions.

## [GameOverImage.java]
* The constructor now accepts additional width and height parameters, which allow the size of the image to be set upon creation. 
  The `setGameOverImage()`, `setPosition()`, and `setDimensions()` methods are called within the constructor to improve modularity and clarity.
* The image loading is moved to a dedicated method `setGameOverImage()`. This method attempts to load the image and logs an error if the image is not found.
  By isolating the image setup logic, the constructor remains clean, and the image loading behavior can be easily modified in one place. 
  The error handling (logging a message if the image is not found) adds robustness and helps with debugging.
* The position and dimensions are set through two new methods, `setPosition(double xPosition, double yPosition)` and `setDimensions(double width, double height)`. 
  These methods encapsulate specific tasks, improving the clarity of the constructor. It also makes it easier to modify these properties later, 
  as the logic for positioning and resizing is now isolated.
* The `setGameOverImage()` method now includes error handling. If the image resource cannot be found, an error message is logged to System.err.

## [HeartDisplay.java]
* Explicit error handling is introduced to check whether the resource is `null` and throws an `IllegalArgumentException`
  with a descriptive error message if the heart image cannot be found.
* The `ImageView` is created and delegated to the `createHeartImageView()` method. If the image is not found, the method
  returns null, and the heart is not added to the container.
* The constant `HEART_SIZE` is changed from 50 to 30 to improve visual preference, making it appear better in the game window.
* `final` modifiers are added to the class fields to ensure that they are not modified after initialisation, which are
  is helpful for maintaining the code and preventing unintended changes.
* The `container` is explicitly initialized in the constructor (`this.container = new HBox();`). This makes the container
  initialization clearer and more consistent with object-oriented principles, as it is instantiated directly within the constructor
  instead of relying on an implicit initialization later.
* An `update()` method is introduced to allow changing the heart display dynamically based on the current health value.
  This method clears the current hearts and re-initializes the display according to the new health value, making the class
  more flexible for use in scenarios where the number of hearts may change over time.

## LevelParent.java
## LevelOne.java
## LevelTwo.java
## LevelView.java
## LevelViewLevelTwo.java
## Projectile.java
## ShieldImage.java
## UserPlane.java
## UserProjectile.java
## WinImage.java
