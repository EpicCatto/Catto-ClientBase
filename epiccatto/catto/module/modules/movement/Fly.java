package epiccatto.catto.module.modules.movement;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.Event2D;
import epiccatto.catto.event.impl.EventCollide;
import epiccatto.catto.event.impl.EventMove;
import epiccatto.catto.event.impl.EventMotion;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleData;
import epiccatto.catto.module.settings.impl.BooleanSetting;
import epiccatto.catto.module.settings.impl.ModeSetting;
import epiccatto.catto.module.settings.impl.NumberSetting;
import epiccatto.catto.utils.player.MoveUtil;

import java.util.Objects;

@ModuleData(name = "Fly", description = "Allows you to fly", category = Category.MOVEMENT)
public class Fly extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Motion", "Test"}, "Motion");
    private final NumberSetting speed = new NumberSetting("Speed", this, 1, 0.1, 10, false, () -> mode.getValue().equalsIgnoreCase("Motion"));
    private final BooleanSetting bobbing = new BooleanSetting("Bobbing", this, true);

    //Default values
    private double startY;


    public Fly() {
        addSettings(mode, speed, bobbing);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        setSuffix(mode.getValue());
        if (bobbing.getValue()){
            mc.thePlayer.cameraYaw = 0.1f;
        }
        if (Objects.requireNonNull(event.getType()) == EventMotion.Type.PRE) {
            switch (mode.getValue()) {
                case "Motion":
                    updateMotion();
                    break;
            }
        }
    }

    @EventTarget
    public void onMove(EventMove event){
        switch (mode.getValue()) {
            case "Motion":
                MoveUtil.strafe(event,speed.getValue());
                break;
            case "Test":
                break;
        }
    }

    @EventTarget
    public void onCollide(EventCollide event) {
    }

    @EventTarget
    public void onRender2D(Event2D event) {
    }

    @Override
    public void onEnable() {
        this.startY = mc.thePlayer.posY;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void updateMotion(){
        mc.thePlayer.motionY = 0;
        if (mc.gameSettings.keyBindJump.pressed) {
            mc.thePlayer.motionY = 0.42 + (speed.getValue() / 10);
        } else if (mc.gameSettings.keyBindSneak.pressed) {
            mc.thePlayer.motionY = -0.42 - (speed.getValue() / 10);
        }
    }
}
