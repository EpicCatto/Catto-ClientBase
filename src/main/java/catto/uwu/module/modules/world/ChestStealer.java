package catto.uwu.module.modules.world;

import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event2D;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.event.impl.EventReceivePacket;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.utils.TimerUtil;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.player.ItemUtils;
import net.minecraft.network.play.server.S40PacketDisconnect;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

@ModuleData(name = "ChestStealer", description = "Steal item in chest", category = Category.WORLD)
public class ChestStealer extends Module {
    public NumberSetting delaySet = new NumberSetting("Delay", this, 100, 0, 1000, true);
    public BooleanSetting silent = new BooleanSetting("Silent", this, false);
    public BooleanSetting badItems = new BooleanSetting("Bad Items", this, true);
    public BooleanSetting autoClose = new BooleanSetting("Auto Close", this, true);

    private final TimerUtil timer = new TimerUtil();
    private double delay;
    public static boolean hideGui, hideGuiMouse;

    public ChestStealer() {
        addSettings(delaySet, silent, badItems, autoClose);
    }

    @EventTarget
    private void onMotion(EventMotion event) {
        if (event.isPre()) {
            setSuffix(delaySet.getValueInt());
            if (this.isChestEmpty()) {
                this.setDelay();
            }

            if (silent.getValue() && mc.currentScreen instanceof GuiChest) {
                Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
                Mouse.setGrabbed(true);
            }

            //if (!silent.getValue()) {

            if (mc.currentScreen instanceof GuiChest) {
                hideGui = silent.getValue();
                if (hideGui && ModuleManager.getModuleByName("InvMove").isEnabled())hideGuiMouse = true;
                final GuiChest chest = (GuiChest) mc.currentScreen;
                boolean close = autoClose.getValue();
                if (isValidChest(chest)) {
                    if ((this.isChestEmpty() || ItemUtils.isInventoryFull()) && close && timer.hasReached((long) delay)) {
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        timer.reset();
                        return;
                    }

                    if (timer.hasReached((long) delay)) {
                        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                            if (stack != null && timer.hasReached((long) delay) && (!ItemUtils.isBad(stack) || !badItems.getValue())) {
                                mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                                this.setDelay();
                                this.timer.reset();
                            }
                        }
                        timer.reset();
                    }
                }
            }else {
                hideGui = false;
                hideGuiMouse = false;
            }

        }
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        if (mc.currentScreen instanceof GuiChest && silent.getValue()) {
            FontLoaders.Sfui20.drawCenteredString("Silent Stealing... press Escape to close the chest", (event.getWidth() / 2), event.getHeight() / 2 + FontLoaders.Sfui20.getStringHeight("Silent Stealing... press Escape to close the chest") / 2 + 3, -1);
        }
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof S40PacketDisconnect) {
            setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        //silentCurrent = null;
        hideGui = false;
        super.onDisable();
    }

    private boolean isChestEmpty() {
        if (mc.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest) mc.currentScreen;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                if (stack != null && (!ItemUtils.isBad(stack) || !badItems.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setDelay() {
        if (delaySet.getValue() <= 5) {
            this.delay = delaySet.getValue();
        } else {
            this.delay = delaySet.getValue() + ThreadLocalRandom.current().nextDouble(-40, 40);
        }

    }


    private boolean isValidChest(GuiChest chest) {

        int radius = 5;
        for (int x = -radius; x < radius; x++) {
            for (int y = radius; y > -radius; y--) {
                for (int z = -radius; z < radius; z++) {
                    double xPos = mc.thePlayer.posX + x;
                    double yPos = mc.thePlayer.posY + y;
                    double zPos = mc.thePlayer.posZ + z;

                    BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();

                    if (block instanceof BlockChest) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

}
