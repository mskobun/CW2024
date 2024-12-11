package com.example.demo.entities;

import com.example.demo.util.Probability;
import javafx.scene.Node;


/**
 * Represents a shield with probabilistic activation and a maximum duration of activity.
 */
public class ProbabilisticShield extends Shield {
    private double activatedDelta;
    private final double maxActivatedDelta;
    private final Probability activationProbability;

    /**
     * Creates a new ProbabilisticShield instance.
     *
     * @param view                  The graphical node representing the shield.
     * @param initialX              The initial x-coordinate of the shield.
     * @param initialY              The initial y-coordinate of the shield.
     * @param activationProbability The probability object determining the chance of shield activation.
     * @param maxActivatedDelta     The maximum time (in seconds) the shield can remain active before deactivating.
     */
    public ProbabilisticShield(Node view, double initialX, double initialY, Probability activationProbability, double maxActivatedDelta) {
        super(view, initialX, initialY);
        this.maxActivatedDelta = maxActivatedDelta;
        this.activationProbability = activationProbability;
    }

    /**
     * Updates the shield's state based on the elapsed time.
     * <p>
     *
     * @param timeDelta The time elapsed since the last update (in seconds).
     */
    public void updateShield(double timeDelta) {
        if (isActive()) {
            activatedDelta += timeDelta;
            if (activatedDelta >= maxActivatedDelta) {
                hideShield();
            }
        } else if (activationProbability.evaluate(timeDelta)) {
            showShield();
        }
    }

    /**
     * Activates the shield, resetting the active duration timer.
     */
    @Override
    public void showShield() {
        super.showShield();
        activatedDelta = 0;
    }
}