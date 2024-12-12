package com.example.demo.screen;

import com.example.demo.AssetFactory;
import com.example.demo.entities.backgrounds.Background;
import com.example.demo.entities.backgrounds.StaticImageBackground;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuScreen extends AbstractScreen {
    private Background background;

    public MainMenuScreen(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        super(screenHeight, screenWidth, screenNavigator, assetFactory);
        initalizeBackground(screenHeight, screenWidth);
        initializeMenu();
    }

    private void initalizeBackground(double screenHeight, double screenWidth) {
        background = new StaticImageBackground(
                getAssetFactory().createImage("sunsetBackground2.png"),
                screenHeight,
                screenWidth
        );
        getContentRoot().getChildren().add(background.getView());
    }

    private void initializeMenu() {
        ImageView logo = new ImageView(getAssetFactory().createImage("logo.png"));
        logo.setFitHeight(400);
        logo.setPreserveRatio(true);

        Button startButton = new Button("Start Game");
        // Set button actions
        startButton.setOnAction(e -> startGame());

        // Create a VBox layout to arrange buttons vertically
        VBox layout = new VBox(10, logo, startButton);
        layout.setAlignment(Pos.CENTER);
        // Do not let root stretch the layout beyond children's size
        layout.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(layout, Pos.CENTER); // Center buttonLayout in root
        StackPane.setMargin(layout, new Insets(-150, 0, 0, 0));
        getContentRoot().getChildren().add(layout);
    }

    private void startGame() {
        goToScreen(ScreenType.LEVEL_ONE);
    }

    @Override
    public void updateScene(double timeDelta) {
        background.update(timeDelta);
    }
}
