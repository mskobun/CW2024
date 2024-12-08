package com.example.demo.level.manager;

import com.example.demo.entities.ActiveActor;
import javafx.scene.Group;
import javafx.scene.Scene;

public class SceneManager implements ActorEventListener {
    private Group backgroundLayer;
    private Group entityLayer;
    private Group UILayer;
    private Group root;
    private Scene scene;

    public SceneManager(double height, double width) {
        this.root = new Group();
        this.backgroundLayer = new Group();
        this.entityLayer = new Group();
        this.UILayer = new Group();
        this.scene = new Scene(root, height, width);
        this.root.getChildren().addAll(backgroundLayer, entityLayer, UILayer);
    }

    public Scene getScene() {
        return scene;
    }

    public Group getUILayer() {
        return UILayer;
    }

    public Group getEntityLayer() {
        return entityLayer;
    }

    public Group getBackgroundLayer() {
        return backgroundLayer;
    }

    public void addActor(ActiveActor actor) {
        getEntityLayer().getChildren().add(actor);
    }

    public void removeActor(ActiveActor actor) {
       getEntityLayer().getChildren().remove(actor);
    }

    @Override
    public void actorAdded(ActiveActor actor) {
        addActor(actor);
    }

    @Override
    public void actorRemoved(ActiveActor actor) {
        removeActor(actor);
    }
}
