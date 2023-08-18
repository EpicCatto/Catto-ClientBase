package epiccatto.catto.module.modules.world;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.api.Category;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleData;
import epiccatto.catto.module.settings.impl.NumberSetting;

@ModuleData(name = "Timer", description = "Speeds up the game", category = Category.WORLD)
public class Timer extends Module {
    private NumberSetting speed = new NumberSetting("Speed", this, 1, 0.1, 10, false);


    public Timer() {
        addSettings(speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.timer.timerSpeed = speed.getValue().floatValue();
    }

}
