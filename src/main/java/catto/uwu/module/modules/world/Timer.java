package catto.uwu.module.modules.world;

import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventUpdate;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;

@ModuleData(name = "Timer", description = "Speeds up the game", category = Category.WORLD)
public class Timer extends Module {
    private final NumberSetting speed = new NumberSetting("Speed", this, 1, 0.1, 10, false);


    public Timer() {
        addSettings(speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.timer.timerSpeed = speed.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }
}
