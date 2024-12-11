package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LayerManager implements ActorEventListener {
    private Pane backgroundLayer;
    private Pane entityLayer;
    private StackPane UILayer;
    private StackPane root;

    public LayerManager(StackPane root) {
        this.root = root;
        this.backgroundLayer = new StackPane();
        this.entityLayer = new Pane();
        this.UILayer = new StackPane();
        this.root.getChildren().addAll(backgroundLayer, entityLayer, UILayer);
    }

    public StackPane getUILayer() {
        return UILayer;
    }

    public Pane getEntityLayer() {
        return entityLayer;
    }

    public Pane getBackgroundLayer() {
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
