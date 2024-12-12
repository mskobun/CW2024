package com.example.demo.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles keyboard input events and maps key presses to specific actions.
 * It allows listeners to register for specific actions.
 */
public class KeyInputHandler implements EventHandler<KeyEvent> {
    private final Map<KeyCode, KeyAction> codeActionMap;
    private final Map<KeyAction, List<KeyActionHandler>> listeners;

    /**
     * Constructs a new KeyInputHandler with predefined key-action mappings.
     */
    public KeyInputHandler() {
        codeActionMap = new HashMap<>();
        listeners = new HashMap<>();
        initializeCodeEventMap();
    }

    /**
     * Attaches this KeyInputHandler to the specified scene,
     * registering it for key press and release events.
     *
     * @param scene the scene to attach this handler to
     */
    public void attachToScene(final Scene scene) {
        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(this);
    }

    private List<KeyActionHandler> getOrCreateListeners(final KeyAction action) {
        return listeners.computeIfAbsent(action, k -> new ArrayList<>());
    }

    /**
     * Adds a listener for the specified action.
     *
     * @param action  the action to listen for
     * @param handler the handler to notify when the action occurs
     */
    public void addListener(final KeyAction action,
                            final KeyActionHandler handler) {
        getOrCreateListeners(action).add(handler);
    }


    /**
     * Removes a listener for the specified action.
     *
     * @param action  the action to stop listening for
     * @param handler the handler to remove
     */
    public void removeListener(final KeyAction action, final KeyActionHandler handler) {
        getOrCreateListeners(action).remove(handler);
    }

    private void initializeCodeEventMap() {
        codeActionMap.put(KeyCode.UP, KeyAction.MOVE_UP);
        codeActionMap.put(KeyCode.DOWN, KeyAction.MOVE_DOWN);
        codeActionMap.put(KeyCode.RIGHT, KeyAction.MOVE_RIGHT);
        codeActionMap.put(KeyCode.LEFT, KeyAction.MOVE_LEFT);
        codeActionMap.put(KeyCode.SPACE, KeyAction.FIRE_PROJECTILE);
        codeActionMap.put(KeyCode.ESCAPE, KeyAction.TOGGLE_PAUSE);
    }

    /**
     * Handles a key event, notifying all registered listeners of the corresponding action.
     *
     * @param keyEvent the key event to handle
     */
    @Override
    public void handle(final KeyEvent keyEvent) {
        boolean active;

        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            active = true;
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            active = false;
        } else {
            return;
        }

        KeyAction action = codeActionMap.get(keyEvent.getCode());
        if (action != null) {
            getOrCreateListeners(action).forEach(listener -> listener.handle(action, active));
        }
    }
}