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

## [LevelParent.java] | check again
* The `Observable` and `setChanged()/notifyObservers()` are replaced by the `PropertyChangeSupport` and `PropertyChangeListener`, offering
  a more structured and decoupled approach for notifying other components about the level change, improving flexibility and scalability for the game logic.
* The background is now initialized in a dedicated method (`initializeBackground()`), which is more focused and easier to manage. 
  The timeline setup is streamlined, and the game loop setup is separated in its own method (`initializeTimeline()`). By moving background 
  and timeline setup into their own methods, the code becomes modular and easier to follow.
* Key press handling has been refactored into two distinct methods: `handleKeyPress(KeyCode)` and `handleKeyRelease(KeyCode)`, making
  the logic more modular and easier to maintain. This change is part of the cleaner separation of concerns, and simplifies adding
  or modifying key controls in the future. The `switch` statement makes the code more organized and maintainable.
* The `bombs` list is introduced, along with handling for bomb collisions and updates. This is a new game feature for `LevelThree.java`.
* The logic for checking if the game is over, along with going to the next level, is handled by firing a property change event (`support.firePropertyChange()`), 
  and additional checks for the level change are introduced.
* The game tutorial is introduced with a button that shows a tutorial screen when clicked.
* The `currentNumberOfEnemies` has been changed to a `SimpleIntegerProperty` to facilitate easier binding to other UI components and
  react to changes in the number of enemies automatically.
* Spawn timing is now controlled by `lastSpawnTime`, which tracks when the next enemy should spawn, providing more precise control over spawn intervals.
* Enemy projectile spawning is handled by a separate method, `generateEnemyFire()`, improving code organization.
* Collision detection has been consolidated into a single method, `handleCollisions()`, to handle various actor interactions
  (e.g., planes vs. projectiles, projectiles vs. enemies), removing redundancy and making it more reusable.
* Enemy penetration detection has been moved to a dedicated method, `enemyHasPenetratedDefenses()`, making the logic clearer and more readable.
  This method checks if an enemy has crossed the defense line.
* When an enemy penetrates the defenses, it causes damage to the player and is destroyed, which is handled in the `handlePenetratedEnemies()` method 
  that checks all enemies.

## [LevelOne.java]
* The constructor has been modified to accept two additional parameters: `HeartDisplay heartDisplay` and `Controller controller`. 
  This allows the constructor to integrate heart display management (for showing player lives) and a game controller object for better handling of game logic.
  The `super()` call is modified to reflect the new parameters, and constants like `BACKGROUND_IMAGE_ONE` and `PLAYER_INITIAL_HEALTH` are replaced with values from a `GameConstants` class.
* The logic to advance to the next level is now more abstracted in the `advanceToLevelTwo()` method (which is introduced later). The condition  `userHasReachedKillTarget()` 
  is replaced with the method `isKillTargetReached()`, which is more descriptive and encapsulates the kill target check logic.
* The `initializerFriendlyUnits` method now uses a helper method `addUserToRoot()` for adding the user to the root, making the code
  more modular and easier to maintain or extend.
* The spawning logic is refactored into smaller, more manageable methods. The `canSpawnEnemy()` checks if a new enemy can spawn 
  based on the maximum enemy cunt and the time since the last spawn. The `spawnNewEnemy()` encapsulates the actual logic for spawning 
  a new enemy at a random Y-position. The `updateLastSpawnTime()` tracks the time of the last spawn. These changes make the spawning logic
  cleaner and separates concerns for better readability.
* Instead of using a hardcoded constant `PLAYER_INITIAL_HEALTH`, the updated code now retrieves this value from the `GameConstants` class. 
  This enhances maintainability, especially if the value is used in multiple places throughout the game.

## [LevelTwo.java]
* The constructor now takes `HeartDisplay heartDisplay` and `Controller controller` parameters, aligning with the changes made in the `LevelOne` class. 
  These allow for heart display management and a game controller to handle interactions or game logic.
* Instead of directly creating the boss inside the constructor, it is now done via the `initializeBoss()` method. 
* The level view (`LevelViewLevelTwo`) is instantiated right in the constructor.
* The method `addPlayerToRoot()` is now used instead of directly adding the player (`getUser()`) to the root. This is a refactor for better organization and code readability.
* Instead of calling `winGame()` when the boss is destroyed, the code now calls `advanceToLevelThree()`. It uses `goToNextLevel(GameConstants.LEVEL_THREE)` to perform 
  the transition, ensuring the game flow proceeds correctly. This method transitions the game to the next level, 
  aligning with the game's progression to level three.
* The check for the number of enemies (`getCurrentNumberOfEnemies() == 0`) is replaced with the `isNoEnemiesOnScreen()` method, which improves the readability of the code.
* The action to add the boss to the root is now done through the method `addBossToRoot()` instead of directly calling `addEnemyUnit(boss)`. 
  This adds a layer of abstraction and keeps the code more modular.
* The `LevelViewLevelTwo` instantiation in the method has been simplified. There is no need to assign the result to `levelView` in this method since it is returned immediately. 
  This change reduces redundancy in the code.
* The `bindShieldToBoss()` method is added to bind the shield image's position to the boss's coordinates. It uses a `translateX` and `translateY` property binding 
  to keep the shield positioned relative to the boss, improving the visual display when the boss has a shield.
* The `requestFocusForBackground()` method ensures the background is focused when the game starts, which is useful for handling input events.
* The `startTimeline()` method starts the game's timeline, which encapsulates the timeline starting logic, making it clearer where the game's animation begins.
* Like the changes made in `LevelOne`, constants are now moved into the `GameConstants` class. This makes the code more maintainable, as constants such as 
  `BACKGROUND_IMAGE_TWO` and `PLAYER_INITIAL_HEALTH` are defined in one place.

## LevelView.java
## LevelViewLevelTwo.java
## Projectile.java
## ShieldImage.java
## UserPlane.java
## UserProjectile.java
## WinImage.java
