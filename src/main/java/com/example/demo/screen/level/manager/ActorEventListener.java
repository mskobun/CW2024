package com.example.demo.screen.level.manager;

import com.example.demo.entities.ActiveActor;

public interface ActorEventListener {
    void actorAdded(ActiveActor actor);
    void actorRemoved(ActiveActor actor);
}
