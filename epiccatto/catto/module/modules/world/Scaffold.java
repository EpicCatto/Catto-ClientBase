package epiccatto.catto.module.modules.world;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventSafeWalk;
import epiccatto.catto.event.impl.EventStrafe;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.settings.impl.BooleanSetting;
import epiccatto.catto.module.settings.impl.NumberSetting;

public class Scaffold extends Module {
    private final NumberSetting delay = new NumberSetting("Delay", this, 0, 0, 1000, false);
    private final BooleanSetting safewalk = new BooleanSetting("Safewalk", this, true);

//    0 yaw, 1 pitch
    private float[] rotations = new float[2];

    public Scaffold() {
        super("Scaffold", "Places blocks down for you.", Category.WORLD, 0);
        addSettings(delay, safewalk);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        setSuffix(delay.getValue());


    }

    @EventTarget
    public void onSafewalk(EventSafeWalk event) {
        event.setSafe(safewalk.getValue());
    }
    @EventTarget
    public void onStrafe(EventStrafe event) {
//        apply movefix
        event.setYaw(rotations[0]);
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
