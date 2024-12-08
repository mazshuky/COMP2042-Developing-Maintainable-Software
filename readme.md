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