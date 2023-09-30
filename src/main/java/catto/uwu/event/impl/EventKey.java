package catto.uwu.event.impl;

import catto.uwu.event.Event;


public class EventKey extends Event {
    private int key;

    public EventKey(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

}
