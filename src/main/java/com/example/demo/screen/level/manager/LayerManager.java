package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Manages the layers of the game screen, organizing different visual elements into separate layers such as
 * background, entities, and UI. Also handles the addition and removal of {@link ActiveActor} visual nodes to and from the entity layer.
 */
public class LayerManager implements ActorEventListener {
    private final Pane backgroundLayer;
    private final Pane entityLayer;
    private final StackPane UILayer;
    private final StackPane root;

    /**
     * Constructs a new {@code LayerManager}.
     *
     * @param root the root {@link StackPane} of the game screen, which will contain all managed layers
     */
    public LayerManager(final StackPane root) {
        this.root = root;
        this.backgroundLayer = new StackPane();
        this.entityLayer = new Pane();
        this.UILayer = new StackPane();
        this.root.getChildren().addAll(backgroundLayer, entityLayer, UILayer);
    }

    /**
     * Gets the UI layer, which is used for displaying user interface components.
     *
     * @return the UI layer as a {@link StackPane}.
     */
    public StackPane getUILayer() {
        return UILayer;
    }

    /**
     * Gets the entity layer, which is used for displaying game entities such as actors.
     *
     * @return the entity layer as a {@link Pane}.
     */
    public Pane getEntityLayer() {
        return entityLayer;
    }

    /**
     * Gets the background layer, which is used for displaying the game's background visuals.
     *
     * @return the background layer as a {@link Pane}.
     */
    public Pane getBackgroundLayer() {
        return backgroundLayer;
    }

    /**
     * Adds an {@link ActiveActor} to the entity layer.
     *
     * @param actor the {@link ActiveActor} to add.
     */
    public void addActor(final ActiveActor actor) {
        getEntityLayer().getChildren().add(actor.getView());
    }

    /**
     * Removes an {@link ActiveActor} from the entity layer.
     *
     * @param actor the {@link ActiveActor} to remove.
     */
    public void removeActor(final ActiveActor actor) {
        getEntityLayer().getChildren().remove(actor.getView());
    }

    /**
     * @see ActorEventListener#actorAdded(ActiveActor)
     */
    @Override
    public void actorAdded(final ActiveActor actor) {
        addActor(actor);
    }

    /**
     * @see ActorEventListener#actorRemoved(ActiveActor)
     */
    @Override
    public void actorRemoved(final ActiveActor actor) {
        removeActor(actor);
    }
}
