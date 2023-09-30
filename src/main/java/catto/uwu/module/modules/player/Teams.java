package catto.uwu.module.modules.player;

import catto.uwu.Client;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import net.minecraft.entity.Entity;

@ModuleData(name = "Teams", description = "Know your teammates.", category = Category.PLAYER)
public class Teams extends Module {

    public static boolean isOnSameTeam(Entity entity) {
        if (!Client.instance.moduleManager.getModuleByClass(Teams.class).isEnabled())
            return false;
        if (mc.thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
            if (mc.thePlayer.getDisplayName().getUnformattedText().length() <= 2
                    || entity.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            if (mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2)
                    .equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        return false;
    }
}
