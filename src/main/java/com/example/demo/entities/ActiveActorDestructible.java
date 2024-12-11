package com.example.demo.entities;

import javafx.scene.Node;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    private boolean isDestroyed;

    public ActiveActorDestructible(Node view, double initialXPos, double initialYPos) {
        super(view, initialXPos, initialYPos);
        isDestroyed = false;
    }

    @Override
    public void destroy() {
        setDestroyed(true);
    }

    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
