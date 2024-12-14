## Github
<https://github.com/mskobun/CW2024>
## Compilation Instructions
These instructions are for Linux, since this is what the author is most familiar with.

This program needs JDK 21 to be built. It uses the maven build system.

If your system has the required JDK, but not Maven, the provided Maven wrapper (`./mvnw`) can be used. Just substitute `mvn` in commands below for `./mvnw`.

### Compiling into .jar
Output will be in `target`
```sh
mvn compile
```
### Compiling into a standalone executable
Output will be in `target`
```sh
mvn javafx:jlink
```
### Running from Maven
```sh
mvn javafx:run
```
### Running the tests
```sh
mvn test
```
##
## Implemented and Working Properly
### Added Main Menu
It is now the first thing shown when the game launches, and allows the user to pick between starting a normal game or endless mode.

### Added Game End Menus
Previously, if the game was lost/won, if you wanted to play a game, you needed to restart the game program. Introduced a menu with options for going to main menu or playing again.
### Added pausing functionality
It pauses the game, along with displaying a menu to resume or exit to main menu.

### Added a third level
It introduces two new enemy types: `ZigZagPlane` and `BomberPlane`.

### Added endless mode
A level that endlessly spawns enemies, it only ends when the user dies.

### Added a scrolling background
In many other side-scrolling shooter games, the background scrolls sideways to give a sense of speed. I have implemented a `Background` class that implements `UpdatableEntity`, from which I could extend to `ScrollingImageBackground`.

### Added x-direction movement for the User unit
The user can now move forward and backward, not only up and down.

### Introduced health progress bar for `Boss`
The boss has a lot of health, so the user would want to know they are doing some damage. I have introduced  `HealthProgressBar` which can show the health of any class implementing `HealthObservable` interface.

### Improved collision detection
Previously, collision detection was quite bad, the hitboxes didn't align with the visuals. This was due to the fact that the collision algorithm, simply got the bounds of the `ImageView`s and said they collided if they overlapped.

While this is an OK algorithm, the problem was that the images included contained a large amount of transparent borders. So they would not be seen, but be counted by the collision algorithm. I have trimmed the transparent borders, resulting in improved collision detection.

### Added asset caching
Previously, *every time* an entity was created, the creation code would create a new `Image`, which would load this image from the filesystem.

This is quite inefficient, there might be 100s of projectiles created in seconds, and each one would read the same file over and over. I have improved this by introducing `AssetFactory` and it's implementation `CachedAssetFactory`, which only loads the same image ones, and then serves a cached representation.
### Ensured UI nodes are layered correctly
Previously, the nodes were layered by time of their addition. This meant for example, that enemies and projectiles could be displayed in front of the heart display, as the latter was spawned earlier.

I have fixed this by introducing `LayerManager` which splits the scene into 3 layers: UI, entities, background.

### Made the game scale to different screen resolutions by using letterboxing
The game used to only work at 1300x700 and not allow resizing of the window. I have introduced letterboxing, meaning that if a game window is scaled, the content will be scaled as well. And if the new aspect ratio is different, black bars will be added to the sides.

### Made projectiles smaller and speed higher
The game used to feel slow paced to me, but if you added more enemies it just became unberable due to projectiles being difficult to avoid. So looking a smilar games, I figured downsizing the enemy projectiles and making everything move faster would make it more fun, which it did.
### Fixed the boss shield
The original implementation was broken.

### Made updates framerate-independent
Previously, things like movements and probabilities were specified on a per-frame basis. Meaning that if you, say increased the framerate from 20 FPS to 100 FPS, everything would start moving 5 times faster.

Coupled with the fact the framerate was set at 20 FPS it made the game feel very sluggish. Instead, I made to pass every entity that needs to be updated a time delta since last frame, and made sure they calculate speeds/probabilities based , on it.

Finally, I increased the default framerate to 60 FPS making the game appear much smoother.

### Added Github Actions to automatically check if code complies/passes tests
If more people will start working on the project, more pull requests will be submitted. To make it easier for reviewers to instantly know if the code passes tests, a Github Action was added. If it does, github will show a green checkmark next to the commit, otherwise it will show a red cross.
### Updated JDK to 21
Java 19 was a short-term release whose support ended almost 2 years ago. I switched to the latest LTS release, which is currently 21.

## Refactorings
This section is for refactoring that was done, that did not necessarily result in features. 
### Untangle `LevelParent`
`LevelParent` (now: `AbstractLevel`) did a lot of unrelated things, clearly violating single-responsibility principle. While this was not completely eliminated, it was made better by:

- Subclassing `AbstractScreen` to get access to it's scene creation, keyboard handling and update loop managment.
- Delegating most actor managment operations (removing destroyed, updating, handling collisions) to `ActorManager`.
- Delegating most UI management operations to `LayerManager`. `LayerManager` is connected to `ActorManager` by implementing the `ActorEventListener` interface, and will automatically add/remove actors to the scene.
- Making HUD elements subscription-based. For example `HeartDisplay` now only needs to be passed a `HealthObservable` on initalization, and it automatically reduce/add hearts on property changes.
### Replaced `Timeline` with `AnimationTimer`

`AnimationTimer` is more suited for UI updates since instead of running at a constant interval, it runs every frame. However, updating the logic every frame would be unnecessarily costly, so I have introduced a `ScreenLoop` class, that only runs update when `targetDelta` is reached. Similar to how a `Timeline` would operate, but the difference is that the `timeDelta` reported to the update funciton is the actual time elapsed, not a constant.
### Introduce `KeyInputHandler`
This class is responsible for handling keyboard input, and converting it to a custom enum named `KeyAction`. Then it registers listeners for `KeyAction`s. This allows to decouple specific keycodes from the actions they perform, allowing, for example, to have configurable keybinds.
### Distance `ActiveActor` logic from the visual representation
`ActiveActor` subclassing `ImageView` violated the composition over inheritance principle. Refactored `ActiveActor` no longer subclasses it, instead having a `Node getView()` method for UI code to get the visual representation.

This allowed, for example, for the `Boss` class to encapsulate the `Shield`.
### Implement `MovementStrategy`
Some actors, despite differences in visual representation, projectile firing times, etc. would still share the same moving code.

To facilitate reuse of such code, `MovementStrategy` class was implemented and `ActiveActor` was extended to have a `setMovementStrategy` method, and automatically update position from it.
### Make `ActiveActorDestructible` implement `HealthObserable`
Every destructible actor now has observable `health`/`maxHealth` properties so that, for example, UI elements could subscribe to their health and not have to be manually updated every update loop.
### Implement `ProjectileListener` in `LevelManager` and make `FighterPlane` rely on it
Instead of `LayerManager` having to loop over all actors and check if they want to fire the projectile this update loop, `FighterPlane`s can now inform `LevelManager` if they do.
### Introduce `Background` class
Instead of `AbstractLevel` handling image background creation, introduce a `Background` class implementing various backgrounds. Currently has two implementations: `StaticImageBackground` and `ScrollingImageBackground`.
### Introduce `Shield` class
To move out shield-managment logic out of `Boss` I introduced a generic shield class, and it's child `ProbabilisticShield`.
### Make `LevelHUD` (previously `LevelView`) use `StackpPane` to position its nodes
Previously `LevelView` kept x/y position of where to place its nodes as constants. This was unwieldy and had to be modified every time a child node changed it's size. Instead, use `StackPane` so that the position can be specified in terms of alignment (i.e. left, bottom right, top center, etc.) and margin.
### Create `AssetFactory` interface and `CachedAssetFactory`
`AsssetFactory` is a generic asset factory interface, while is a specific implementation that caches the assets after loading them once.

This arrangement is an example of an abstract factory pattern, while `CachedAssetFactory` is an example of a flyweight pattern.
### Create factory for `Screen`
Previously, layer switching logic (now that we have main menu, it is screen switching) was quite prone to error, as next level was passed as a package name.

Instead, I created a `ScreenType` enum that `Controller` can pass to `ScreenFactory` to make a new level.
### Create factory for `Actor`
As actor creation logic started becoming more complex with the addition of an asset factory, projectile listener, key input listener, etc.. I thought it would be a good idea to share some of it.
### Remove `LevelViewLevelTwo`
It was a band-aid for the fact that `ActiveActor` could only represent itself with a single image, it was removed after `ActiveActor` refactoring.
## Implemented but Not Working Properly
N/A
## Features Not Implemented
### Automatic style checking with `checkstyle`
I felt like automatic code style enforcement was a good improvement in maintainability, especially if the project would be worked on by a team. However, the most popular such solution for java, `checkstyle`, came with two built-in default styles: Google and Sun.

The Google style was objectionable to me, since it used 2 spaces for indentation instead of 4 I am used to. The Sun style was even more objectionable, since it wanted javadoc for every private field and method, which I felt was a bit too much.

While I could create my own style, or fix the code to conform to one of the default styles, the effort in doing so was not worth the benefit.
### Configurable keybindings
Most of the work was already there with `KeyInputHandler`, but I didn't have time to make it configurable.
### Power-ups
I have thought of adding custom power ups randomly spawned on the field, that the user can collect. For example: bombs, health packs, additional guns.

However, it would require a major refactoring of collision handling code, so I didn't have time.
### Effects
I have thought of adding effects, for example explosions when an enemy gets destroyed.

I would say the majority of the work is there with `UpdatableEntity`, to add effects one just needs to:

1. Add `UpdatableEntity` handling to `ActorManger`
2. Refactor `ProjectileListener` to `EntityListener`
3. Add the required effects, all of them extending `UpdatableEntity`
4. 
Unfortunately, I didn't have time to do that.
## New Java Classes
```
com.example.demo.AssetFactory.java
com.example.demo.CachedAssetFactory.java
com.example.demo.controller.KeyAction.java
com.example.demo.controller.KeyActionHandler.java
com.example.demo.controller.KeyInputHandler.java
com.example.demo.entity.ActorFactory.java
com.example.demo.entity.ActorType.java
com.example.demo.entity.Destructible.java
com.example.demo.entity.HealthObservable.java
com.example.demo.entity.HealthObservableActor.java
com.example.demo.util.ScaledImageView.java
com.example.demo.entity.UpdatableEntity.java
com.example.demo.entity.background.Background.java
com.example.demo.entity.background.ScrollingImageBackground.java
com.example.demo.entity.background.StaticImageBackground.java
com.example.demo.entity.plane.BomberPlane.java
com.example.demo.entity.plane.ProjectileListener.java
com.example.demo.entity.plane.ZigZagPlane.java
com.example.demo.entity.projectile.BombProjectile.java
com.example.demo.entity.projectile.BossProjectile.java
com.example.demo.entity.projectile.EnemyProjectile.java
com.example.demo.entity.shield.ProbabilisticShield.java
com.example.demo.movement.BossMovementStrategy.java
com.example.demo.movement.DirectionalMovementStrategy.java
com.example.demo.movement.LinearMovementStrategy.java
com.example.demo.movement.MovementStrategy.java
com.example.demo.movement.NoMovementStrategy.java
com.example.demo.movement.PositionDelta.java
com.example.demo.movement.ZigZagMovementStrategy.java
com.example.demo.screen.AbstractScreen.java
com.example.demo.screen.LetterboxManager.java
com.example.demo.screen.MainMenuScreen.java
com.example.demo.screen.ScreenFactory.java
com.example.demo.screen.ScreenLoop.java
com.example.demo.screen.ScreenNavigator.java
com.example.demo.screen.ScreenType.java
com.example.demo.screen.level.AbstractLevel.java
com.example.demo.screen.level.EndlessModeLevel.java
com.example.demo.screen.level.LevelOne.java
com.example.demo.screen.level.LevelThree.java
com.example.demo.screen.level.LevelTwo.java
com.example.demo.screen.level.hud.component.GameEndOverlay.java
com.example.demo.screen.level.hud.component.HUDComponent.java
com.example.demo.screen.level.hud.component.HealthProgressBar.java
com.example.demo.screen.level.hud.component.LoseOverlay.java
com.example.demo.screen.level.hud.component.PauseOverlay.java
com.example.demo.screen.level.hud.component.WinOverlay.java
com.example.demo.screen.level.manager.ActorEventListener.java
com.example.demo.screen.level.manager.ActorManager.java
com.example.demo.screen.level.manager.LayerManager.java
com.example.demo.util.Probability.java
```
## Modified Java Classes
Listed under new names.
```
com.example.demo.entity.ActiveActor.java
com.example.demo.entity.ActiveActorDestructible.java
com.example.demo.screen.level.hud.component.HeartDisplay.java
com.example.demo.screen.level.hud.LevelHUD.java
com.example.demo.entity.shield.Shield.java
com.example.demo.entity.projectile.Projectile.java
com.example.demo.entity.projectile.UserProjectile.java
com.example.demo.entity.plane.Boss.java
com.example.demo.entity.plane.EnemyPlane.java
com.example.demo.entity.plane.UserPlane.java
com.example.demo.entity.plane.FighterPlane.java
com.example.demo.controller.Controller.java
com.example.demo.controller.Main.java
```
## Unexpected Problems
### `checkstyle`
See above.
### Letterboxing
It took me hours to figure out letterboxing based on this [StackOverflow answer](https://stackoverflow.com/questions/16606162/javafx-fullscreen-resizing-elements-based-upon-screen-siz). I have copied the code, but I assumed it would work with any root node. So over couple hours I tried it with `Group(Group)`, `Pane(Pane)`, `StackPane(Pane)`, `StackPane(StackPane)`, etc., seting different background colors to see if it works. Ultimately I figured out, having `Group(StackPane(StackPane))` is essential to having it work, but I am still not quite sure why top `Group` is necessary. It does not work without it.