package com.example.demo.level;

import javafx.scene.Group;
import javafx.scene.Scene;

public class SceneManager {
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
}
