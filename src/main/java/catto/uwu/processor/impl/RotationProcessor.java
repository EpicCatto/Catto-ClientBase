package catto.uwu.processor.impl;

import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.*;
import catto.uwu.processor.Processor;
import catto.uwu.utils.player.Rotation;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.concurrent.ThreadLocalRandom;

public class RotationProcessor implements Processor {
    // TODO: Movefix, Rotations in general (GCD, ETC..)

    private final Rotation clientRotation = new Rotation();
    private final Rotation serverRotation = new Rotation();

    private static final Rotation toRotation = new Rotation();

    private static boolean enabled, moveFix;

    private static int rotateTicks, revertTicks;

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
//        ChatUtil.sendChatMessageWPrefix("packet: " + event.getPacket().getClass().getSimpleName());
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
            if(packet.getRotating()) {
//                ChatUtil.sendChatMessageWPrefix("serverRotation: " + packet.getYaw() + " " + packet.getPitch());
                serverRotation.setYaw(packet.getYaw());
                serverRotation.setPitch(packet.getPitch());

            }
        }
    }

    @EventTarget
    public void onStrafe(EventStrafe event) {
        if(enabled && moveFix) {
            event.setYaw(clientRotation.getYaw());
        }
    }

    @EventTarget
    public void onJump(EventJump event) {
        if(enabled && moveFix) {
            event.setYaw(clientRotation.getYaw());
        }
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if (!event.isPre()) return;
        if(enabled) {
            event.setYaw(clientRotation.getYaw());
            event.setPitch(clientRotation.getPitch());
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

//        ChatUtil.sendChatMessageWPrefix("enabled: " + enabled + " rotateTicks: " + rotateTicks);

        final float yawDifference;
        final float pitchDifference;
        double base;
        if (enabled && rotateTicks > 0) {
            yawDifference = getAngleDifference(toRotation.getYaw(), clientRotation.getYaw());
            pitchDifference = getAngleDifference(toRotation.getPitch(), clientRotation.getPitch());
            base = Math.hypot(getAngleDifference(toRotation.getYaw(), serverRotation.getYaw()), (toRotation.getPitch() - serverRotation.getPitch()));
            //            ChatUtil.sendChatMessageWPrefix("turnSpeed: " + turnSpeed + " yaw: " + clientRotation.getYaw() + " pitch: " + clientRotation.getPitch() + " realYaw: " + mc.thePlayer.rotationYaw + " realPitch: " + mc.thePlayer.rotationPitch);
        }else {
            yawDifference = getAngleDifference(mc.thePlayer.rotationYaw, clientRotation.getYaw());
            pitchDifference = getAngleDifference(mc.thePlayer.rotationPitch, clientRotation.getPitch());
            base = Math.hypot(getAngleDifference(toRotation.getYaw(), mc.thePlayer.rotationYaw), (toRotation.getPitch() - mc.thePlayer.rotationPitch));
        }
        setClientRotation(yawDifference, pitchDifference, base);

        enabled = revertTicks > 0 || rotateTicks > 0;

        if (rotateTicks > 0) {
            rotateTicks--;
        }else {
            toRotation.reset();
        }

        if (revertTicks > 0)
            revertTicks--;

        if (rotateTicks <= 0 && revertTicks <= 0) {
            moveFix = false;
        }

    }

    private void setClientRotation(float yawDifference, float pitchDifference, double base) {
        if (base < 0) base = -base;
        if (base > 180.0) base = 180.0;

        final float turnSpeed = (float) (base / ThreadLocalRandom.current().nextDouble(1.5, 2.5));

        clientRotation.setYaw(serverRotation.getYaw() + (yawDifference > turnSpeed ? turnSpeed : Math.max(yawDifference, -turnSpeed)));
        clientRotation.setPitch(serverRotation.getPitch() + (pitchDifference > turnSpeed ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }

    public Rotation getClientRotation() {
        return clientRotation;
    }

    public Rotation getServerRotation() {
        return serverRotation;
    }


    public Rotation getToRotation() {
        return toRotation;
    }


    public static void setToRotation(Rotation toRotation) {
        if (toRotation.getPitch() > 90 || toRotation.getPitch() < -90) return;
        setToRotation(toRotation, true);
    }
    public static void setToRotation(Rotation toRotation, boolean moveFix) {
        if (toRotation.getPitch() > 90 || toRotation.getPitch() < -90) return;
        RotationProcessor.toRotation.setYaw(toRotation.getYaw());
        RotationProcessor.toRotation.setPitch(toRotation.getPitch());
        rotateTicks = 1;
        revertTicks = 20;
        RotationProcessor.moveFix = moveFix;
    }

    private static float getAngleDifference(float a, float b) {
        return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
    }
}
