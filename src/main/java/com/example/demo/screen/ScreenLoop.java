package com.example.demo.screen;

import javafx.animation.AnimationTimer;

/**
 * Manages screen's update loop. Calls {@link AbstractScreen#updateScene(int)} with a specified interval.
 */
public class ScreenLoop extends AnimationTimer {
    private static final long NO_LAST_UPDATE = 0;
    private long lastUpdate;
    private final long targetDeltaNanos;
    private final AbstractScreen screen;

    /**
     * Constructs a new {@code ScreenLoop}.
     * <p>
     * @param screen    The screen to update
     * @param targetDelta   How often to update the screen, in seconds.
     */
    public ScreenLoop(AbstractScreen screen, double targetDelta) {
        this.screen = screen;
        this.lastUpdate = NO_LAST_UPDATE;
        this.targetDeltaNanos = (long) (targetDelta * 1_000_000_000);
    }

    /**
     * Decides whether it's time to call {@link AbstractScreen#updateScene} . Is called every frame if started.
     */
    @Override
    public void handle(long now) {
        if (lastUpdate == NO_LAST_UPDATE) {
            lastUpdate = now;
            return;
        }

        if (now - lastUpdate >= targetDeltaNanos) {
            double timeDelta = ((double) (now - lastUpdate) / 1_000_000_000);
            screen.updateScene(timeDelta);
            lastUpdate = now;
        }
    }

    /**
     * Stop the loop and reset last update time.
     */
    @Override
    public void stop() {
        super.stop();
        lastUpdate = 0;
    }
}
