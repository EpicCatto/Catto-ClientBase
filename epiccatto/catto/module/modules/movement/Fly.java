package epiccatto.catto.module.modules.movement;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.Event2D;
import epiccatto.catto.event.impl.EventCollide;
import epiccatto.catto.event.impl.EventMove;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.modules.combat.Killaura;
import epiccatto.catto.module.modules.render.HUD;
import epiccatto.catto.module.settings.impl.BooleanSetting;
import epiccatto.catto.module.settings.impl.ModeSetting;
import epiccatto.catto.module.settings.impl.NumberSetting;
import epiccatto.catto.utils.MoveUtil;
import epiccatto.catto.utils.font.FontLoaders;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class Fly extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Motion", "Test"}, "Motion");
    private final NumberSetting speed = new NumberSetting("Speed", this, 1, 0.1, 10, false, () -> mode.getValue().equalsIgnoreCase("Motion"));
    private final BooleanSetting bobbing = new BooleanSetting("Bobbing", this, true);

    //Default values
    private double startY;

    // Zonecraft
    private boolean zcBoost;

    public Fly() {
        super("Fly", "Make you fly vroom vroom", Category.MOVEMENT, 0);
        addSettings(mode, speed, bobbing);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        setSuffix(mode.getValue());
        if (bobbing.getValue()){
            mc.thePlayer.cameraYaw = 0.1f;
        }
        if (Objects.requireNonNull(event.getType()) == EventUpdate.Type.PRE) {
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
