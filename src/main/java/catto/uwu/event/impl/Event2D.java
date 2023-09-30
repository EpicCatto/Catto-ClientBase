package catto.uwu.event.impl;

import catto.uwu.event.Event;

public class Event2D extends Event {
    private final float width;
    private final float height;

    public Event2D(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
