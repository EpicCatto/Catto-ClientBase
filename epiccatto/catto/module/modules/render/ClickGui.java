package epiccatto.catto.module.modules.render;

import epiccatto.catto.Client;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleData;
import epiccatto.catto.module.settings.impl.ModeSetting;
import net.optifine.ModelUtils;
import org.lwjgl.input.Keyboard;

@ModuleData(name = "ClickGui", description = "Opens the ClickGui", category = Category.RENDER)
public class ClickGui extends Module {

    ModeSetting mode = new ModeSetting("Mode", this, new String[]{"EpicCatto Myth"}, "EpicCatto Myth");

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
