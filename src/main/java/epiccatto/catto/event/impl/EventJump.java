package epiccatto.catto.event.impl;

import epiccatto.catto.event.Event;


public class EventJump extends Event {

//    yaw motion Y and sprinting
    public float yaw;
    public double motionY;

    public EventJump(float yaw, double motionY) {
        this.yaw = yaw;
        this.motionY = motionY;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public double getMotionY() {
        return motionY;
    }


}
