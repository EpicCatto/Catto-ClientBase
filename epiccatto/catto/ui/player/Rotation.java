package epiccatto.catto.ui.player;

public class Rotation {
    private float yaw, lastYaw;
    private float pitch, lastPitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Rotation() {
        this(0, 0);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Rotation setYaw(float yaw) {
        lastYaw = this.yaw;
        this.yaw = yaw;
        return this;
    }

    public Rotation setPitch(float pitch) {
        lastPitch = this.pitch;
        this.pitch = pitch;
        return this;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public Rotation setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
        return this;
    }

    public Rotation setLastPitch(float lastPitch) {
        this.lastPitch = lastPitch;
        return this;
    }

    public Rotation resetLast() {
        this.lastYaw = 0;
        this.lastPitch = 0;
        return this;
    }

    public Rotation getLastRotation() {
        return new Rotation(lastYaw, lastPitch);
    }

    public Rotation setLastRotation(Rotation rotation) {
        this.lastYaw = rotation.getYaw();
        this.lastPitch = rotation.getPitch();
        return this;
    }

    public Rotation addYaw(float yaw) {
        this.yaw += yaw;
        return this;
    }

    public Rotation addPitch(float pitch) {
        this.pitch += pitch;
        return this;
    }

    public Rotation subYaw(float yaw) {
        this.yaw -= yaw;
        return this;
    }

    public Rotation subPitch(float pitch) {
        this.pitch -= pitch;
        return this;
    }

    public Rotation setRotation(Rotation rotation) {
        this.yaw = rotation.getYaw();
        this.pitch = rotation.getPitch();
        return this;
    }

    public Rotation reset() {
        this.yaw = 0;
        this.pitch = 0;
        return this;
    }

    public Rotation clone() {
        return new Rotation(yaw, pitch);
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }
}
