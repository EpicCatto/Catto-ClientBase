package epiccatto.catto.module.modules.render;

import epiccatto.catto.module.api.Category;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleData;
import epiccatto.catto.module.settings.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;

@ModuleData(name = "Cape", description = "Change your cape design", category = Category.RENDER)
public class Cape extends Module {

    public ModeSetting capeDesign = new ModeSetting("Style", this, new String[]{"Default", "Catty", "Custom"}, "Default");
    public Cape() {
        addSettings(capeDesign);
    }

    public ResourceLocation getCapeLoc(){
        return new ResourceLocation("catto/cape/cape_" + capeDesign.getValue().toLowerCase() + ".png");
    }
}
