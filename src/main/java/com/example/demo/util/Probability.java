package com.example.demo.util;

public class Probability {
    double probabilityPerSecond;
    public Probability(double probabilityPerSecond) {
        this.probabilityPerSecond = probabilityPerSecond;
    }

    private double calculateAdjusted(double timeDelta) {
        return probabilityPerSecond * timeDelta;
    }
    public boolean evaluate(double timeDelta) {
        double adjustedProbability = calculateAdjusted(timeDelta);
        return Math.random() < adjustedProbability;
    }
}
