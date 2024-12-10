package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayerManager implements ActorEventListener {
    private Group backgroundLayer;
    private Group entityLayer;
    private Group UILayer;
    private Pane root;

    public LayerManager(Pane root) {
        this.root = root;
        this.backgroundLayer = new Group();
        this.entityLayer = new Group();
        this.UILayer = new Group();
        this.root.getChildren().addAll(backgroundLayer, entityLayer, UILayer);
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
        getEntityLayer().getChildren().add(actor.getView());
    }

    public void removeActor(ActiveActor actor) {
       getEntityLayer().getChildren().remove(actor.getView());
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
