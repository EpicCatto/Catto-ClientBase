package catto.uwu.module.modules.player;

import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventJump;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.event.impl.EventMove;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.player.MoveUtil;

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

    @EventTarget
    public void onJump(EventJump event){
        switch (mode.getValue()) {
            case "NCP":
                event.setCancelled(true);
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
