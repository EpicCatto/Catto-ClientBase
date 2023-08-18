package epiccatto.catto.event.impl;

import epiccatto.catto.event.Event;


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
