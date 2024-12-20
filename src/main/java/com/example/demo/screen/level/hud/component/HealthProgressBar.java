package com.example.demo.screen.level.hud.component;

import com.example.demo.entities.HealthObservable;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A health progress bar UI component that displays the health
 * of a {@link HealthObservable}.
 */
public class HealthProgressBar implements HUDComponent {
    private VBox container;
    private Rectangle backgroundBar;
    private Rectangle progressBar;
    private Group bar;
    private Label label;

    private static final String LABEL_STYLE = "-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;";
    private static final double SPACING = 5;
    private static final double BAR_HEIGHT = 20;
    private static final Color BAR_BACKGROUND_COLOR = Color.BLACK;
    private static final Color BAR_PROGRESS_COLOR = Color.RED;

    /**
     * Constructs a new {@code HealthProgressBar}.
     *
     * @param width     the width of the progress bar
     * @param actor     the {@link HealthObservable} whose health is being displayed
     * @param labelText the text to display above the progress bar. If {@code null}, no label is displayed.
     */
    public HealthProgressBar(
            final double width,
            final HealthObservable actor,
            final String labelText
    ) {
        initializeContainer();
        initializeBar(actor, width);
        if (labelText != null) {
            initializeLabel(labelText);
        }
    }

    private void initializeContainer() {
        container = new VBox();
        container.setSpacing(SPACING);
        container.setAlignment(Pos.CENTER);
        // Do not let the container be resized beyond size of it's children
        container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    private void initializeBar(final HealthObservable actor, final double width) {
        backgroundBar = new Rectangle(width, BAR_HEIGHT);
        backgroundBar.setFill(BAR_BACKGROUND_COLOR);

        progressBar = new Rectangle(0, BAR_HEIGHT);
        progressBar.setFill(BAR_PROGRESS_COLOR);

        // Bind progressBar width to (health / max health) * full bar width
        DoubleBinding healthPercentage = actor.healthProperty().divide(actor.maxHealthProperty().doubleValue());
        DoubleBinding barWidth = healthPercentage.multiply(width);
        progressBar.widthProperty().bind(barWidth);

        bar = new Group();
        bar.getChildren().addAll(backgroundBar, progressBar);
        container.getChildren().add(bar);
    }

    private void initializeLabel(final String labelText) {
        label = new Label(labelText);
        label.setStyle(LABEL_STYLE);
        container.getChildren().addFirst(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getView() {
        return container;
    }
}
