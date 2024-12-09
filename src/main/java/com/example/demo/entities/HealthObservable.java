package com.example.demo.entities;

import javafx.beans.property.IntegerProperty;

public interface HealthObservable {
    IntegerProperty healthProperty();
    IntegerProperty maxHealthProperty();
}
