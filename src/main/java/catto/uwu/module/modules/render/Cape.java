package catto.uwu.module.modules.render;

import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import net.minecraft.util.ResourceLocation;

@ModuleData(name = "Cape", description = "Change your cape design", category = Category.RENDER)
public class Cape extends Module {

    public ModeSetting capeDesign = new ModeSetting("Style", this, new String[]{"Default", "Catty", "Custom"}, "Default");
    public BooleanSetting allPlayers = new BooleanSetting("All Players", this, false);

    public Cape() {
        addSettings(capeDesign, allPlayers);
    }

    public ResourceLocation getCapeLoc(){
        return new ResourceLocation("catto/cape/cape_" + capeDesign.getValue().toLowerCase() + ".png");
    }
}
