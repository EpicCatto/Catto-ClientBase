package epiccatto.catto.module.modules.movement;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.Event2D;
import epiccatto.catto.event.impl.EventCollide;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.modules.combat.Killaura;
import epiccatto.catto.module.modules.render.HUD;
import epiccatto.catto.module.settings.impl.ModeSetting;
import epiccatto.catto.utils.MoveUtil;
import epiccatto.catto.utils.font.FontLoaders;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    private ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Motion", "Zonecraft"}, "Motion");

    //Default values
    private double startY;

    // Zonecraft
    private boolean zcBoost;

    public Fly() {
        super("Fly", "Make you fly vroom vroom", Category.MOVEMENT, 0);
        addSettings(mode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        setSuffix(mode.getValue());
        switch (event.getType()) {
            case PRE:
                switch (mode.getValue()) {
                    case "Motion":
                        mc.thePlayer.motionY = 0;
                        if (mc.gameSettings.keyBindJump.pressed) {
                            mc.thePlayer.motionY = 0.42;
                        } else if (mc.gameSettings.keyBindSneak.pressed) {
                            mc.thePlayer.motionY = -0.42;
                        }
                        break;
                    case "Zonecraft":
                        // Mini jump
                        if (mc.thePlayer.onGround && zcBoost) {
                            mc.thePlayer.motionY = 0.1;
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1E-32, mc.thePlayer.posZ);
                            //get if there is a block under the player
                            if (Killaura.target == null && zcBoost) {
                                mc.thePlayer.motionX *= 1.5;
                                mc.thePlayer.motionZ *= 1.5;
                            }
                        }

                        MoveUtil.strafe();
                        break;
                }
                break;

            case POST:
                switch (mode.getValue()) {
                    case "Motion":
                        break;
                    case "Zonecraft":
                        //Can use boost
                        Block block = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock();
                        zcBoost = block instanceof BlockAir;

                        // Disable jump button
                        if (mc.gameSettings.keyBindJump.pressed) {
                            mc.gameSettings.keyBindJump.pressed = false;
                        }
                        break;
                }
                break;
        }
    }

    @EventTarget
    public void onCollide(EventCollide event) {
        switch (mode.getValue()) {
            case "Zonecraft":
                // Fake Collusion with ground
                if (event.getBlock() instanceof BlockAir && event.getY() <= startY){
                    event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, startY, event.getZ() + 1));
                }
                break;
        }
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        switch (mode.getValue()) {
            case "Zonecraft":
                HUD.addInfo("Zonecraft Boost", zcBoost);
                break;
        }
    }

    @Override
    public void onEnable() {
        this.startY = mc.thePlayer.posY;
        switch (mode.getValue()) {
            case "Zonecraft":
                zcBoost = false;
                break;
        }
    }

    @Override
    public void onDisable() {
        switch (mode.getValue()) {
            case "Zonecraft":
                zcBoost = false;
                HUD.removeInfo("Zonecraft Boost");
                break;
        }
        super.onDisable();
    }
}
