package catto.uwu.module.modules.world;

import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.processor.impl.RotationProcessor;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventSafeWalk;
import catto.uwu.event.impl.EventStrafe;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.utils.player.Rotation;

@ModuleData(name = "Scaffold", description = "Automatically places blocks under you", category = Category.WORLD)
public class Scaffold extends Module {
    private final NumberSetting delay = new NumberSetting("Delay", this, 0, 0, 1000, false);
    private final BooleanSetting safewalk = new BooleanSetting("Safewalk", this, true);

//    0 yaw, 1 pitch
    private float[] rotations = new float[2];

    public Scaffold() {
        addSettings(delay, safewalk);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        setSuffix(delay.getValue());
//				if (place) {
//        event.setYaw(mc.thePlayer.rotationYaw - 180);
//        event.setPitch(78.59991f);
        RotationProcessor.setToRotation(new Rotation(mc.thePlayer.rotationYaw - 180, 78.59991f), false);
    }

    @EventTarget
    public void onSafewalk(EventSafeWalk event) {
        event.setSafe(safewalk.getValue());
    }
    @EventTarget
    public void onStrafe(EventStrafe event) {
//        apply movefix
//        event.setYaw(rotations[0]);
    }
    @Override
    public void onEnable() {
        rotations[0] = mc.thePlayer.rotationYaw;
        rotations[1] = mc.thePlayer.rotationPitch;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
