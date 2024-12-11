package com.example.demo.entities;

import com.example.demo.AssetFactory;
import com.example.demo.controller.KeyInputHandler;
import javafx.scene.Group;
import javafx.scene.Node;

public class ActorFactory {
    private final AssetFactory assetFactory;
    private final ProjectileListener projectileListener;

    public ActorFactory(AssetFactory assetFactory, ProjectileListener projectileListener) {
        this.assetFactory = assetFactory;
        this.projectileListener = projectileListener;
    }

    private Node createImageActorNode(String imageName, double imageHeight) {
        return new ImageActorNode(assetFactory, imageName, imageHeight);
    }

    private Node createImageActorNode(String imageName, double imageHeight, double imageWidth) {
        return new ImageActorNode(assetFactory, imageName, imageHeight, imageWidth);
    }

    public EnemyProjectile createEnemyProjectile(double initialXPos, double initalYPos) {
        return new EnemyProjectile(createImageActorNode("enemyFire.png", 32), initialXPos, initalYPos);
    }

    public EnemyPlane createEnemyPlane(double initialXPos, double initialYPos) {
        return new EnemyPlane(createImageActorNode("enemyplane.png", 54), initialXPos, initialYPos, this, projectileListener);
    }

    public UserPlane createUserPlane(int initialHealth, KeyInputHandler keyInputHandler) {
        return new UserPlane(createImageActorNode("userplane.png", 39), initialHealth, this, keyInputHandler, projectileListener);
    }

    public BossProjectile createBossProjectile(double initialYPos) {
        return new BossProjectile(createImageActorNode("fireball.png", 75), initialYPos);
    }

    public Boss createBoss() {
        Group bossRoot = new Group();
        Node shieldImageNode = createImageActorNode("shield.png", 200, 200);
        Node bossImageNode = createImageActorNode("bossplane.png", 56);
        return new Boss(bossRoot, bossImageNode, shieldImageNode, this, projectileListener);
    }

    public UserProjectile createUserProjetile(double initialXPos, double initialYPos) {
        return new UserProjectile(createImageActorNode("userfire.png", 6), initialXPos, initialYPos);
    }
}