package com.example.demo.controller;

/**
 * Handler for {@link KeyAction} events.
 */
public interface KeyActionHandler {
    /**
     * Handle {@link KeyAction} event.
     * @param keyAction action that occurred.
     * @param active whether the event is activated or deactivated.
     */
    void handle(KeyAction keyAction, boolean active);
}
