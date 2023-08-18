package epiccatto.catto.module.modules.combat;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventMotion;
import epiccatto.catto.module.api.Category;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleData;
import epiccatto.catto.module.settings.impl.NumberSetting;
import epiccatto.catto.processor.impl.RotationProcessor;
import epiccatto.catto.ui.player.Rotation;
import epiccatto.catto.utils.TimerUtil;
import epiccatto.catto.utils.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@ModuleData(name = "Killaura", description = "Automatically attack things around you!", category = Category.COMBAT)
public class Killaura extends Module {

    public static EntityLivingBase target;
    private final ArrayList<EntityLivingBase> targets = new ArrayList<>();

    private final NumberSetting attackRange = new NumberSetting("Attack Range", this, 4.5, 0.1, 7, false);

    private final NumberSetting cps = new NumberSetting("CPS", this, 10, 1, 20, false);
    private final NumberSetting maxCps = new NumberSetting("Max CPS", this, 10, 1, 20, false);
    private final NumberSetting minCps = new NumberSetting("Min CPS", this, 5, 1, 20, false);



    public TimerUtil attackTimer = new TimerUtil();


    public Killaura() {
        super();
        addSettings(attackRange, cps, maxCps, minCps);
    }



    @EventTarget
    public void onMotion(EventMotion event){
        setSuffix(cps.getValue());
        if (!event.isPre()) return;
        for (Object o : mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) o;
                if (entity != mc.thePlayer && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= attackRange.getValue() + 5 && entity instanceof EntityPlayer) {
                    targets.add(entity);
                    target = targets.get(0);
                }
            }
        }
        if (target != null) {
            float[] rotations = getRotations(target);
            RotationProcessor.setToRotation(new Rotation(rotations[0], rotations[1]));
            if (attackTimer.hasReached((long) ((Math.random() * (1000 / minCps.getValue() - 1000 / maxCps.getValue() + 1) + 1000 / maxCps.getValue()))) && mc.thePlayer.getDistanceToEntity(target) <= attackRange.getValue()) {
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
//                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                attackTimer.reset();
            }
        }
    }

    @Override
    public void onEnable() {
        targets.clear();
        attackTimer.reset();
        target = null;
    }
    public static float[] getRotations(Entity e){
        final Vec3d eyesPos = new Vec3d(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final AxisAlignedBB bb = e.getEntityBoundingBox();
        final Vec3d vec = new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.5F, bb.minY + (bb.maxY - bb.minY) * 0.9F, bb.minZ + (bb.maxZ - bb.minZ) * 0.5F);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));

        return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }

}
