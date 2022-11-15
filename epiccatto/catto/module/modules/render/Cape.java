package epiccatto.catto.module.modules.render;

import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.settings.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;

public class Cape extends Module {

    public ModeSetting capeDesign = new ModeSetting("Style", this, new String[]{"Default", "Catty", "Custom"}, "Default");
    public Cape() {
        super("Cape", "wow cool player cape", Category.RENDER, 0);
        addSettings(capeDesign);
    }

    public ResourceLocation getCapeLoc(){
        return new ResourceLocation("catto/cape/cape_" + capeDesign.getValue().toLowerCase() + ".png");
    }
}
