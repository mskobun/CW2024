package com.example.demo.screen.level.hud;

import com.example.demo.entities.HealthObservable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class HeartDisplay {

    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 50;
    private static final int INDEX_OF_FIRST_ITEM = 0;
    private HBox container;

    public HeartDisplay(HealthObservable actor) {
        initializeContainer();
        initializeHearts(actor.healthProperty().get());
        bindHealthValue(actor);
    }

    private void initializeContainer() {
        container = new HBox();
    }

    private void bindHealthValue(HealthObservable actor) {
        actor.healthProperty().addListener(
                (observableValue, oldValue, newValue) ->
                        updateHearts((Integer) oldValue, (Integer) newValue)
        );
    }

    private void updateHearts(int oldValue, int newValue) {
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

    private void initializeHearts(int numberOfHeartsToDisplay) {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            addHeart();
        }
    }

    private void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(INDEX_OF_FIRST_ITEM);
    }

    private void addHeart() {
        ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heart.setFitHeight(HEART_HEIGHT);
        heart.setPreserveRatio(true);
        container.getChildren().add(heart);
    }

    public Region getView() {
        return container;
    }

}
