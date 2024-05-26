package catto.uwu.module.modules.movement;

import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.*;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.modules.combat.Killaura;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.TimerUtil;
import catto.uwu.utils.player.MoveUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ModuleData(name = "Speed", description = "Allows you to move faster", category = Category.MOVEMENT)
public class Speed extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Custom", "Verus"}, "Custom");
    private final ModeSetting custom_Jump = new ModeSetting("Jump Mode", this, new String[]{"None", "Jump", "MotionY", "Low-hop", "Dev"}, "Jump", () -> mode.getValue().equalsIgnoreCase("Custom"));
    private final BooleanSetting custom_Strafe = new BooleanSetting("Strafe", this, true, () -> mode.getValue().equalsIgnoreCase("Custom"));
    private final NumberSetting custom_Speed = new NumberSetting("Speed", this, 1, 0.1, 2.0, false, () -> custom_Strafe.getValue() && mode.getValue().equalsIgnoreCase("Custom"));

    //Timer
    private final TimerUtil timer = new TimerUtil();


    public Speed() {
        addSettings(mode, custom_Jump, custom_Strafe, custom_Speed);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        setSuffix(mode.getValue());
        if (event.isPre()) {
            switch (mode.getValue()) {
                case "Custom":
                    updateCustom();
                    break;
                case "Verus":
                    updateVerus(event);
                    break;
            }
        }
    }

    @EventTarget
    public void onMove(EventMove event) {
        switch (mode.getValue()) {
            case "Custom":
                if(custom_Strafe.getValue()){
                    if (custom_Jump.getValue().equalsIgnoreCase("Low-hop") && (mc.thePlayer.fallDistance > 0.2 || Killaura.target!=null))return;
                    MoveUtil.setMotion(event,custom_Speed.getValue());
                }
                break;
            case "Verus":
                moveVerus(event);
                break;
        }
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
    }

    @EventTarget
    public void onCollide(EventCollide event) {

    }

    @EventTarget
    public void onRender2D(Event2D event) {
    }

    @Override
    public void onEnable() {
        timer.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void updateCustom() {
        switch (custom_Jump.getValue()) {
            case "None":
                break;
            case "Jump":
                if (MoveUtil.isOnGround() && MoveUtil.isMoving()) {
                    mc.thePlayer.jump();
                }
                break;
            case "MotionY":
                if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                    mc.thePlayer.motionY = 0.42;
                }
                break;
            case "Low-hop":
                if ((mc.thePlayer.fallDistance > 0.2)) return;
                if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                    mc.thePlayer.motionY = 0.2;
                }
                break;
            case "Dev":
                if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                    mc.thePlayer.jump();

                }
//                ChatUtil.sendChatMessageWOutPrefix(mc.thePlayer.motionY);
//                if (mc.thePlayer.motionY < 0.15 && mc.thePlayer.motionY > -0.16) {
//                    mc.thePlayer.motionY = -0.1021744352;
//                }
                break;
        }
    }



    private void updateVerus(EventMotion event) {
        if (mc.thePlayer.onGround){
            MoveUtil.strafe(0.336767 * 4.45f);
            mc.thePlayer.jump();
        }
        else mc.thePlayer.posY -= 0.2;
        MoveUtil.strafe(0.376767);
    }

    private void moveVerus(EventMove event) {
    }
}
