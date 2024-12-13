package com.example.demo.entities;

import com.example.demo.AssetFactory;
import com.example.demo.controller.KeyInputHandler;
import com.example.demo.entities.planes.Boss;
import com.example.demo.entities.planes.EnemyPlane;
import com.example.demo.entities.planes.ProjectileListener;
import com.example.demo.entities.planes.UserPlane;
import com.example.demo.entities.projectiles.BossProjectile;
import com.example.demo.entities.projectiles.EnemyProjectile;
import com.example.demo.entities.projectiles.UserProjectile;
import javafx.scene.Group;
import javafx.scene.Node;


/**
 * Factory class responsible for creating various types of actors such as planes and projectiles.
 * It handles the creation of nodes associated with actors and their initialization.
 */
public class ActorFactory {
    private final AssetFactory assetFactory;
    private final ProjectileListener projectileListener;

    /**
     * Constructs an ActorFactory with the provided asset factory and projectile listener.
     *
     * @param assetFactory the factory responsible for creating assets
     * @param projectileListener the listener for spawned projectiles
     */
    public ActorFactory(final AssetFactory assetFactory, final ProjectileListener projectileListener) {
        this.assetFactory = assetFactory;
        this.projectileListener = projectileListener;
    }

    /**
     * Creates a scaled image view node with the specified image name and height.
     * The image is scaled to maintain its aspect ratio.
     *
     * @param imageName the name of the image to be loaded
     * @param imageHeight the desired height for the image
     * @return the created scaled image view node
     */
    private Node createScaledImageView(final String imageName, final double imageHeight) {
        return new ScaledImageView(assetFactory, imageName, imageHeight);
    }


    /**
     * Creates a scaled image view node with the specified image name, height, and width.
     * The image is scaled to the specified dimensions.
     *
     * @param imageName the name of the image to be loaded
     * @param imageHeight the desired height for the image
     * @param imageWidth the desired width for the image
     * @return the created scaled image view node
     */
    private Node createScaledImageView(final String imageName, final double imageHeight, final double imageWidth) {
        return new ScaledImageView(assetFactory, imageName, imageHeight, imageWidth);
    }


    /**
     * Creates a new enemy projectile.
     *
     * @param initialXPos the initial X position of the enemy projectile
     * @param initialYPos the initial Y position of the enemy projectile
     * @return the created {@link EnemyProjectile}
     */
    public EnemyProjectile createEnemyProjectile(final double initialXPos, final double initialYPos) {
        return new EnemyProjectile(createScaledImageView("enemyFire.png", 8), initialXPos, initialYPos);
    }

    /**
     * Creates a new enemy plane.
     *
     * @param initialXPos the initial X position of the enemy plane
     * @param initialYPos the initial Y position of the enemy plane
     * @return the created {@link EnemyPlane}
     */
    public EnemyPlane createEnemyPlane(final double initialXPos, final double initialYPos) {
        return new EnemyPlane(createScaledImageView("enemyplane.png", 54), initialXPos, initialYPos, this, projectileListener);
    }

    /**
     * Creates a new user plane.
     *
     * @param initialHealth the initial health of the user plane
     * @param keyInputHandler the handler for processing user input
     * @return the created {@link UserPlane}
     */
    public UserPlane createUserPlane(final int initialHealth, final KeyInputHandler keyInputHandler) {
        return new UserPlane(createScaledImageView("userplane.png", 39), initialHealth, this, keyInputHandler, projectileListener);
    }


    /**
     * Creates a new boss projectile.
     *
     * @param initialYPos the initial Y position of the boss projectile
     * @return the created {@link BossProjectile}
     */
    public BossProjectile createBossProjectile(final double initialYPos) {
        final double PROJECTILE_HEIGHT = 75;
        return new BossProjectile(createScaledImageView("fireball.png", PROJECTILE_HEIGHT), initialYPos);
    }


    /**
     * Creates a new boss
     *
     * @return the created {@link Boss}
     */
    public Boss createBoss() {
        final double SHIELD_HEIGHT = 125;
        final double BOSS_HEIGHT = 56;
        Group bossRoot = new Group();
        Node shieldImageNode = createScaledImageView("shield.png", SHIELD_HEIGHT);
        Node bossImageNode = createScaledImageView("bossplane.png", BOSS_HEIGHT);
        return new Boss(bossRoot, bossImageNode, shieldImageNode, this, projectileListener);
    }

    public UserProjectile createUserProjetile(final double initialXPos, final double initialYPos) {
        final double PROJECTILE_HEIGHT=6;
        return new UserProjectile(createScaledImageView("userfire.png", PROJECTILE_HEIGHT), initialXPos, initialYPos);
    }
}