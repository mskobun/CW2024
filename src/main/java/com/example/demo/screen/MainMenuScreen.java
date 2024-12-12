package com.example.demo.screen;

import com.example.demo.AssetFactory;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.StaticImageBackground;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuScreen extends AbstractScreen {
    private Background background;

    public MainMenuScreen(
            final double screenHeight,
            final double screenWidth,
            final ScreenNavigator screenNavigator,
            final AssetFactory assetFactory
    ) {
        super(screenHeight, screenWidth, screenNavigator, assetFactory);
        initalizeBackground(screenHeight, screenWidth);
        initializeMenu();
    }

    private void initalizeBackground(
            final double screenHeight,
            final double screenWidth) {
        background = new StaticImageBackground(
                getAssetFactory().createImage("sunsetBackground2.png"),
                screenHeight,
                screenWidth
        );
        getContentRoot().getChildren().add(background.getView());
    }

    private void initializeMenu() {
        final Insets LAYOUT_MARGIN = new Insets(-150, 0, 0, 0);

        Node layout = initializeLayout();
        // Center buttonLayout in root
        StackPane.setAlignment(layout, Pos.CENTER);
        StackPane.setMargin(layout, LAYOUT_MARGIN);
        getContentRoot().getChildren().add(layout);
    }

    private Node initializeLayout() {
        final double LOGO_HEIGHT = 400;
        final double LAYOUT_SPACING = 10;

        ImageView logo = new ImageView(getAssetFactory().createImage("logo.png"));
        logo.setFitHeight(LOGO_HEIGHT);
        logo.setPreserveRatio(true);

        Button startButton = new Button("Start Game");
        Button endlessModeButton = new Button("Endless mode");
        // Set button actions
        startButton.setOnAction(e -> startGame());
        endlessModeButton.setOnAction(e -> goToScreen(ScreenType.LEVEL_ENDLESS_MODE));

        // Create a VBox layout to arrange buttons vertically
        VBox layout = new VBox(LAYOUT_SPACING, logo, startButton, endlessModeButton);
        layout.setAlignment(Pos.CENTER);
        // Do not let root stretch the layout beyond children's size
        layout.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return layout;
    }

    private void startGame() {
        goToScreen(ScreenType.LEVEL_ONE);
    }

    @Override
    public void updateScene(final double timeDelta) {
        background.update(timeDelta);
    }
}
