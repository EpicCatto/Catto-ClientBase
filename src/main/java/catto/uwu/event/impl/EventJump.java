package catto.uwu.event.impl;

import catto.uwu.event.Event;


public class EventJump extends Event {

//    yaw motion Y and sprinting
    public float yaw;
    public float motionY;

    public EventJump(float yaw, float motionY) {
        this.yaw = yaw;
        this.motionY = motionY;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getMotionY() {
        return motionY;
    }


}
