package com.example.demo.level.manager;

import com.example.demo.entities.ActiveActor;

public interface ActorEventListener {
    void actorAdded(ActiveActor actor);
    void actorRemoved(ActiveActor actor);
}
