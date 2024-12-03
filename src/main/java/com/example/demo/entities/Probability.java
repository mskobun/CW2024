package com.example.demo.entities;

public class Probability {
    double probabilityPerSecond;
    public Probability(double probabilityPerSecond) {
        this.probabilityPerSecond = probabilityPerSecond;
    }

    private double calculateAdjusted(int timeDelta) {
        return probabilityPerSecond * ((double) timeDelta / 1000.0);
    }
    public boolean evaluate(int timeDelta) {
        double adjustedProbability = calculateAdjusted(timeDelta);
        return Math.random() < adjustedProbability;
    }
}
