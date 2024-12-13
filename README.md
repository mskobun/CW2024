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
TODO
## Implemented but Not Working Properly
TODO
## Features Not Implemented
TODO
## New Java Classes
```
com.example.demo.AssetFactory.java
com.example.demo.CachedAssetFactory.java
com.example.demo.controller.KeyAction.java
com.example.demo.controller.KeyActionHandler.java
com.example.demo.controller.KeyInputHandler.java
com.example.demo.entities.ActorFactory.java
com.example.demo.entities.ActorType.java
com.example.demo.entities.Destructible.java
com.example.demo.entities.HealthObservable.java
com.example.demo.entities.HealthObservableActor.java
com.example.demo.entities.ScaledImageView.java
com.example.demo.entities.UpdatableEntity.java
com.example.demo.entities.backgrounds.Background.java
com.example.demo.entities.backgrounds.ScrollingImageBackground.java
com.example.demo.entities.backgrounds.StaticImageBackground.java
com.example.demo.entities.planes.BomberPlane.java
com.example.demo.entities.planes.ProjectileListener.java
com.example.demo.entities.planes.ZigZagPlane.java
com.example.demo.entities.projectiles.BombProjectile.java
com.example.demo.entities.projectiles.BossProjectile.java
com.example.demo.entities.projectiles.EnemyProjectile.java
com.example.demo.entities.shields.ProbabilisticShield.java
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
com.example.demo.entities.ActiveActor.java
com.example.demo.entities.ActiveActorDestructible.java
com.example.demo.screen.level.hud.component.HeartDisplay.java
com.example.demo.screen.level.hud.LevelHUD.java
com.example.demo.entities.shields.Shield.java
com.example.demo.entities.projectiles.Projectile.java
com.example.demo.entities.projectiles.UserProjectile.java
com.example.demo.entities.planes.Boss.java
com.example.demo.entities.planes.EnemyPlane.java
com.example.demo.entities.planes.UserPlane.java
com.example.demo.entities.planes.FighterPlane.java
```
## Unexpected Problems
TODO