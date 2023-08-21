package catto.uwu.module.modules.render;

import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.Client;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;

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
