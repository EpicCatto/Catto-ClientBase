package catto.uwu.module.modules.combat;

import catto.uwu.Client;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventUpdate;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.settings.impl.ModeSetting;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;

@ModuleData(name = "AntiBot", description = "Prevents you from attacking bots", category = Category.COMBAT)
public class AntiBot extends Module {

    public ModeSetting mode = new ModeSetting("Mode", this, new String[]{"TabList"}, "TabList");
    private static final ArrayList<Entity> bots = new ArrayList<>();

    public AntiBot() {
        addSettings(mode);
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {
        setSuffix(mode.getValue());
        if(mode.getValue().equalsIgnoreCase("TabList")) {
            if (mc.thePlayer.ticksExisted % 10 == 0) {
                bots.clear();
            }
            for (EntityPlayer player : mc.theWorld.playerEntities) {
                if (player == mc.thePlayer)
                    continue;
                if (!GuiPlayerTabOverlay.getPlayers().contains(player)) bots.add(player);
            }
        }
    }


    public static boolean isBot(EntityLivingBase player) {
        if (!Client.instance.moduleManager.getModuleByClass(AntiBot.class).isEnabled()){
            return false;
        }
        if (player instanceof EntityPlayer) {
            return bots.contains(player);
        }else {
            return false;
        }
    }


}
