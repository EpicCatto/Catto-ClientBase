package catto.uwu.module.modules.render;

import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.Client;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.ui.clickgui.dropdown.DropDownGui;

@ModuleData(name = "ClickGui", description = "Opens the ClickGui", category = Category.RENDER)
public class ClickGui extends Module {

    ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Myth", "Drop"}, "Myth");

    public ClickGui() {
        super();
        addSettings(mode);
    }

    @Override
    public void onEnable() {
        switch (mode.getValue()) {
            case "Myth":
                mc.displayGuiScreen(Client.instance.mythClickGui);
                break;
            case "Drop":
                mc.displayGuiScreen(Client.instance.dropDownGui);
                break;
        }
        setEnabled(false);
        super.onEnable();
    }
}
