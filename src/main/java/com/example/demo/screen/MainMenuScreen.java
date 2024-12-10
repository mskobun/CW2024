package com.example.demo.screen;

import com.example.demo.AssetFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuScreen extends AbstractScreen {
    public MainMenuScreen(double screenHeight, double screenWidth, ScreenNavigator screenNavigator, AssetFactory assetFactory) {
        super(screenHeight, screenWidth, screenNavigator, assetFactory);
        initializeMenu();
    }

    private void initializeMenu() {
        Button startButton = new Button("Start Game");
        // Set button actions
        startButton.setOnAction(e -> startGame());

        // Create a VBox layout to arrange buttons vertically
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        StackPane.setAlignment(layout, Pos.CENTER); // Center buttonLayout in root
        layout.getChildren().addAll(startButton);
        getContentRoot().getChildren().add(layout);
    }

    private void startGame() {
        goToScreen(ScreenType.LEVEL_ONE);
    }

    @Override
    public void updateScene(double timeDelta) {

    }
}
