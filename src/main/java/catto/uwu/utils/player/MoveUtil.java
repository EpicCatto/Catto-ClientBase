package catto.uwu.utils.player;

import catto.uwu.Client;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.event.impl.EventMove;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.modules.combat.Killaura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;

public class MoveUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static void strafe(final double speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void strafe(EventMove event, final double speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        event.setX(-Math.sin(yaw) * speed);
        event.setZ(Math.cos(yaw) * speed);
    }

    public static void setMotion(EventMove event, double speed) {
        //        if (Killaura.target != null && ModuleManager.getModuleByName("TargetStrafe").isEnabled() && mc.thePlayer.isMoving()) {
//            final EntityLivingBase target = Killaura.target;
//            if (target != null) {
//                float dist = mc.thePlayer.getDistanceToEntity(target);
//                double radius = TargetStrafe.radiusSetting.getValDouble();
//                setMotion(speed, dist <= radius + 1.0E-4D ? 0 : 1, dist <= radius + 1.0D ? TargetStrafe.direction : 0, Killaura.);
//                return;
//            }
//        }
        setMotion(event ,speed, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementYaw);
    }

    public static void setMotion(EventMove event, double speed, float forward, double strafing, float yaw) {
        if ((forward == 0.0D) && (strafing == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafing > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafing < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafing = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            double cos1 = Math.cos(Math.toRadians(yaw + 90.0F));
            double sin = Math.sin(Math.toRadians(yaw + 90.0F));
            event.setX(forward * speed * cos1 + strafing * speed * sin);
            event.setZ(forward * speed * sin - strafing * speed * cos1);
        }
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static double getDirection() {
        float rotationYaw = mc.thePlayer.movementYaw;

        if (mc.thePlayer.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if (mc.thePlayer.moveForward < 0F)
            forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F)
            forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if (mc.thePlayer.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public static float getSpeed() {
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static boolean isOnGround() {
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

}
