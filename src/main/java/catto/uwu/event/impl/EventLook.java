package catto.uwu.event.impl;

import catto.uwu.event.Event;

import javax.vecmath.Vector2f;

public class EventLook extends Event {
    private Vector2f rotation;

    public EventLook(Vector2f rotation) {
        this.rotation = rotation;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }
}
