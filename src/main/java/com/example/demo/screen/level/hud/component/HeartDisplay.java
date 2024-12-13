package com.example.demo.screen.level.hud.component;

import com.example.demo.entities.HealthObservable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;


/**
 * A UI component that visually represents the health of an actor using a series of heart icons.
 * The number of hearts dynamically updates when the actor's health changes.
 */
public class HeartDisplay implements HUDComponent {

    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 50;
    private HBox container;

    /**
     * Constructs a {@code HeartDisplay} for the specified actor.
     * The number of hearts displayed is based on the actor's initial health,
     * and updates dynamically as the health changes.
     *
     * @param actor the {@link HealthObservable} actor whose health is represented
     */
    public HeartDisplay(final HealthObservable actor) {
        initializeContainer();
        initializeHearts(actor.healthProperty().get());
        bindHealthValue(actor);
    }

    private void initializeContainer() {
        container = new HBox();
    }

    /**
     * Binds the health of the actor to the heart display, ensuring the UI updates
     * whenever the health value changes.
     *
     * @param actor the {@link HealthObservable} whose health changes are observed
     */
    private void bindHealthValue(final HealthObservable actor) {
        actor.healthProperty().addListener(
                (observableValue, oldValue, newValue) ->
                        updateHearts((Integer) oldValue, (Integer) newValue)
        );
    }

    /**
     * Updates the number of hearts displayed based on the change in health value.
     * Adds hearts if health increases, or removes hearts if health decreases.
     *
     * @param oldValue the previous health value.
     * @param newValue the updated health value.
     */
    private void updateHearts(final int oldValue, final int newValue) {
        if (newValue > oldValue) {
            for (int i = oldValue; i < newValue; i++) {
                addHeart();
            }
        } else {
            for (int i = oldValue; i > newValue; i--) {
                removeHeart();
            }
        }
    }

    /**
     * Initializes the hearts displayed in the container based on the actor's initial health.
     *
     * @param numberOfHeartsToDisplay the number of hearts to display initially.
     */
    private void initializeHearts(final int numberOfHeartsToDisplay) {
        updateHearts(0, numberOfHeartsToDisplay);
    }

    /**
     * Removes a single heart icon from the container.
     * Ensures no operation occurs if the container is already empty.
     */
    private void removeHeart() {
        if (!container.getChildren().isEmpty()) {
            container.getChildren().removeFirst();
        }
    }

    /**
     * Adds a single heart icon to the container.
     */
    private void addHeart() {
        ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heart.setFitHeight(HEART_HEIGHT);
        heart.setPreserveRatio(true);
        container.getChildren().add(heart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getView() {
        return container;
    }
}
