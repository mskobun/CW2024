package com.example.demo.entities;

import javafx.scene.Node;

public interface UpdatableEntity {
    Node getView();
    void update(double timeDelta);
}
