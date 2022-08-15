package epiccatto.catto.module.modules.movement;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", "Make you fly vroom vroom", Category.MOVEMENT, 0);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.motionY = 0;
        if (mc.gameSettings.keyBindJump.pressed) {
            mc.thePlayer.motionY = 0.42;
        }else if (mc.gameSettings.keyBindSneak.pressed) {
            mc.thePlayer.motionY = -0.42;
        }
    }
}
