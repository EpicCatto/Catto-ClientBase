package epiccatto.catto.module.modules.player;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventMotion;
import epiccatto.catto.event.impl.EventMove;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleData;
import epiccatto.catto.module.settings.impl.BooleanSetting;
import epiccatto.catto.module.settings.impl.ModeSetting;
import epiccatto.catto.module.settings.impl.NumberSetting;
import epiccatto.catto.utils.ChatUtil;
import epiccatto.catto.utils.player.MoveUtil;

@ModuleData(name = "ResetVL", description = "Resets your anticheat VL", category = Category.PLAYER)
public class ResetVL extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"NCP", "Test"}, "NCP");
    private final BooleanSetting autoDisable = new BooleanSetting("Auto disable", this, true);
    private final NumberSetting delay = new NumberSetting("Ticks", this, 60, 20, 100, true, autoDisable::getValue);

    int ticks = 0;

    @EventTarget
    public void onMotion(EventMotion event) {
        if (!event.isPre()) return;
        setSuffix(mode.getValue() + " " + delay.getValue().intValue());
        ticks++;

        switch (mode.getValue()) {
            case "NCP":
                resetNCP();
                break;
            case "Test":
                break;
        }

        if(ticks >= delay.getValue() && autoDisable.getValue()){
            setEnabled(false);
            ChatUtil.sendChatMessageWPrefix("Reset VL complete");
        }

    }

    @EventTarget
    public void onMove(EventMove event){
        switch (mode.getValue()) {
            case "NCP":
                event.setX(0);
                event.setZ(0);
                break;
            case "Test":
                break;
        }
    }

    @Override
    public void onDisable() {
        ticks = 0;
        mc.timer.timerSpeed = 1f;
        super.onDisable();
    }

    private void resetNCP(){
        mc.timer.timerSpeed = 3f;
        if(MoveUtil.isOnGround(0.001)){
            mc.thePlayer.motionY = 0.03;
        }
    }


}
