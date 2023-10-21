package catto.uwu.module.modules.combat;

import catto.uwu.module.modules.player.Teams;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.processor.impl.RotationProcessor;
import catto.uwu.utils.player.Rotation;
import catto.uwu.utils.player.RotationUtil;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.optifine.util.MathUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

@ModuleData(name = "Killaura", description = "Automatically attack things around you!", category = Category.COMBAT)
public class Killaura extends Module {

    public static EntityLivingBase target;
    private final ArrayList<EntityLivingBase> targets = new ArrayList<>();
    public NoteSetting sortingNote = new NoteSetting("Sorting Settings", this);
    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Single", "Switch"}, "Single", sortingNote);
    public NumberSetting switchDelay = new NumberSetting("Switch Delay", this, 100, 1, 1000, true, () -> mode.getValue().equalsIgnoreCase("Switch"), sortingNote);
    public ModeSetting sortingMode = new ModeSetting("Sorting Mode", this, new String[]{"Angle", "Distance", "Health"}, "Health", sortingNote);

    public NoteSetting targetsNote = new NoteSetting("Targets Settings", this);


    public BooleanSetting player = new BooleanSetting("Player", this, true, targetsNote);
    public BooleanSetting animals = new BooleanSetting("Animals", this, false, targetsNote);
    public BooleanSetting mobs = new BooleanSetting("Mobs", this, false, targetsNote);
    public BooleanSetting invisible = new BooleanSetting("Invisible", this, false, targetsNote);
    public BooleanSetting wall = new BooleanSetting("Wall", this, true, targetsNote);

    public NoteSetting attackNote = new NoteSetting("Attack Settings", this);
    private final ModeSetting attackMethod = new ModeSetting("Method", this, new String[]{"Packet", "MC", "Legit"}, "Packet", attackNote);
    private final NumberSetting range = new NumberSetting("Attack Range", this, 4, 1, 7, false, attackNote);
    private final BooleanSetting rayCast = new BooleanSetting("RayCast", this, false, attackNote);
    private final BooleanSetting randomizeCps = new BooleanSetting("Randomize CPS", this, false, attackNote);
    private final NumberSetting cps = new NumberSetting("CPS", this, 10, 1, 20, false, () -> !randomizeCps.getValue(), attackNote);
    private final NumberSetting maxCps = new NumberSetting("Max CPS", this, 10, 1, 20, false, randomizeCps::getValue, attackNote);
    private final NumberSetting minCps = new NumberSetting("Min CPS", this, 5, 1, 20, false, randomizeCps::getValue, attackNote);


    private int targetIndex = 0;
    private final TimerUtil switchTimer = new TimerUtil();
    private final TimerUtil attackTimer = new TimerUtil();
    private final Random RANDOM = new Random();

    public Killaura() {
        super();
        addSettings(sortingNote, mode, switchDelay, sortingMode, targetsNote, player, animals, mobs, invisible, wall, attackNote, attackMethod, range, randomizeCps, cps, maxCps, minCps, rayCast);
    }



    @EventTarget
    public void onMotion(EventMotion event){
        setSuffix(mode.getValue());
        if (!event.isPre()) return;

        getAllTarget();
        sortTargets();
        slotTargetSwitch();

        if (target == null) return;

        RotationUtil.Rotation rotations = RotationUtil.toRotation(RotationUtil.searchCenter(target.getEntityBoundingBox(), false, true, true, true).getVec(), false);
        RotationProcessor.setToRotation(new Rotation(rotations.getYaw(), rotations.getPitch()), true);

        if (attackTimer.hasReached( (long) 1000 / (int) (MathUtils.nextDouble(maxCps.getValue(), minCps.getValue()) - RANDOM.nextInt(10) + RANDOM.nextInt(10)))) {
            final MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
//            if (!rayCast.getValue() || (movingObjectPosition != null && movingObjectPosition.entityHit == target && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY))
//                attack(movingObjectPosition.entityHit);
//            else if (!rayCast.getValue())
                attack(target);

            attackTimer.reset();
        }
    }

    @Override
    public void onEnable() {
        targets.clear();
        attackTimer.reset();
        target = null;
    }

    private void attack(Entity target) {
//        if ((mc.thePlayer.getDistanceToEntity(target) <= range && !rayCast.getValue()) ||
//                (rayCast.getValue() && movingObjectPosition != null && movingObjectPosition.entityHit == target)) {
        switch (attackMethod.getValue().toLowerCase()) {
            case "packet":
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C02PacketUseEntity(target, net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK));
                break;
            case "mc":
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
                break;
            case "legit":
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindPickBlock.getKeyCode(), true);
                KeyBinding.onTick(mc.gameSettings.keyBindPickBlock.getKeyCode());
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindPickBlock.getKeyCode(), false);
                break;

        }
    }

    private void getAllTarget() {
        targets.clear();

        for (Entity entity : mc.thePlayer.getEntityWorld().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValidEntity(entityLivingBase))
                    targets.add(entityLivingBase);
            }
        }
    }

    private void sortTargets() {
        switch (sortingMode.getValue()) {
            case "Angle":
                targets.sort(
                        Comparator.comparingDouble(
                                RotationUtil::getAngleChange));
                break;
            case "Distance":
                targets.sort(
                        Comparator.comparingDouble(
                                RotationUtil::getDistanceToEntity));
                break;
            case "Health":
                targets.sort(
                        Comparator.comparingDouble(
                                EntityLivingBase::getHealth));
                break;
        }
    }

    public void slotTargetSwitch() {
        if (switchTimer.hasReached(switchDelay.getValueLong()) && mode.getValue().equals("Switch")) {
            targetIndex++;
            switchTimer.reset();
        }

        if (targetIndex >= targets.size())
            targetIndex = 0;

        target = !targets.isEmpty() &&
                targetIndex < targets.size() ?
                targets.get(targetIndex) :
                null;
    }

    private boolean isValidEntity(EntityLivingBase ent) {
        if (mc.thePlayer.isDead) return false;
        if (!ent.canEntityBeSeen(mc.thePlayer) && !wall.getValue())
            return false;
        if (ent instanceof EntityArmorStand) return false;
        return ent != mc.thePlayer && (!(ent instanceof EntityPlayer) || this.player.getValue()) && (!(ent instanceof EntityAnimal) && !(ent instanceof EntitySquid) || this.animals.getValue()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityVillager) && !(ent instanceof EntitySnowman) && !(ent instanceof EntityBat) || this.mobs.getValue()) && !((double) mc.thePlayer.getDistanceToEntity(
                ent) > attackRange(ent) + 0.4) && (!ent.isInvisible() || this.invisible.getValue()) && (!AntiBot.isBot(ent)) && !mc.thePlayer.isDead && (!(ent instanceof EntityPlayer) || !Teams.isOnSameTeam(ent));
    }
    private double attackRange(EntityLivingBase target) {
        double distance = 0;
        distance = range.getValue();
//        if(!autoBlockMode.getValue().equalsIgnoreCase("None")){
//            if (autoBlockRange.getValue() >= range.getValue()) {
//                return autoBlockRange.getValue();
//            }else {
//                distance = range.getValue();
//            }
//        }else {
//            distance = range.getValue();
//        }
        return distance;
    }

    private int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
