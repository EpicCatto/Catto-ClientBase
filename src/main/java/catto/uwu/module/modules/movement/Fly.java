package catto.uwu.module.modules.movement;

import catto.uwu.event.impl.*;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.event.EventTarget;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.utils.player.MoveUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumFacing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ModuleData(name = "Fly", description = "Allows you to fly", category = Category.MOVEMENT)
public class Fly extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Motion", "Test"}, "Motion");
    private final NumberSetting speed = new NumberSetting("Speed", this, 1, 0.1, 10, false, () -> mode.getValue().equalsIgnoreCase("Motion"));
    private final BooleanSetting bobbing = new BooleanSetting("Bobbing", this, true);

    //Default values
    private double startY;

    //Blink
    private final List<Packet> blinkQueue = new CopyOnWriteArrayList<>();


    public Fly() {
        addSettings(mode, speed, bobbing);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        setSuffix(mode.getValue());
        if (bobbing.getValue()){
            mc.thePlayer.cameraYaw = 0.1f;
        }
        if (event.isPre()) {
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
    public void onSendPacket(EventSendPacket event){
        if (event.getPacket() instanceof C03PacketPlayer) {
            blinkQueue.add(event.getPacket());
//            event.setCancelled(true);
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
//        blinkQueue.forEach(p -> mc.thePlayer.sendQueue.addToSendQueue(p));
        blinkQueue.clear();
        super.onDisable();
    }

    private void updateMotion(){
        mc.thePlayer.motionY = 0;
        if(mc.gameSettings.keyBindSneak.isKeyDown()){
            mc.thePlayer.motionY += speed.getValue() / 2;
        }
        if(mc.gameSettings.keyBindSprint.isKeyDown()){
            mc.thePlayer.motionY -= speed.getValue() / 2;
        }
    }
}
