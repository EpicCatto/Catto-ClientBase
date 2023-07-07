package epiccatto.catto.module.modules.render;

import epiccatto.catto.Client;
import epiccatto.catto.module.api.Category;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleData;
import epiccatto.catto.module.settings.impl.ModeSetting;

@ModuleData(name = "ClickGui", description = "Opens the ClickGui", category = Category.RENDER)
public class ClickGui extends Module {

    ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Myth"}, "Myth");

    public ClickGui() {
        super();
        addSettings(mode);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.instance.mythClickGui);
        setEnabled(false);
        super.onEnable();
    }
}
