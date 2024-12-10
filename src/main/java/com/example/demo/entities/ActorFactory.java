package com.example.demo.entities;

import com.example.demo.AssetFactory;
import javafx.scene.Group;
import javafx.scene.Node;

public class ActorFactory {
    private final AssetFactory assetFactory;

    public ActorFactory(AssetFactory assetFactory) {
        this.assetFactory = assetFactory;
    }

    private Node createImageActorNode(String imageName, double imageHeight) {
        return new ImageActorNode(assetFactory, imageName, imageHeight);
    }

    public EnemyProjectile createEnemyProjectile(double initialXPos, double initalYPos) {
        return new EnemyProjectile(createImageActorNode("enemyFire.png", 32), initialXPos, initalYPos);
    }

    public EnemyPlane createEnemyPlane(double initialXPos, double initialYPos) {
        return new EnemyPlane(createImageActorNode("enemyplane.png", 54), initialXPos, initialYPos, this);
    }
    public UserPlane createUserPlane(int initialHealth) {
        return new UserPlane(createImageActorNode("userplane.png", 39), initialHealth, this);
    }

    public BossProjectile createBossProjectile(double initialYPos) {
        return new BossProjectile(createImageActorNode("fireball.png", 75), initialYPos);
    }

    public Boss createBoss() {
        Group bossRoot = new Group();
        Node bossImageNode = createImageActorNode("bossplane.png", 56);
        return new Boss(bossRoot, bossImageNode, this);
    }

    public UserProjectile createUserProjetile(double initialXPos, double initialYPos) {
        return new UserProjectile(createImageActorNode("userfire.png", 6), initialXPos, initialYPos);
    }
}