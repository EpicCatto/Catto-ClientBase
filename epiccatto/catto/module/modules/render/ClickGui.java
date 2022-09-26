package epiccatto.catto.module.modules.render;

import epiccatto.catto.Client;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.settings.impl.ModeSetting;
import net.optifine.ModelUtils;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {

    ModeSetting mode = new ModeSetting("Mode", this, new String[]{"EpicCatto Myth"}, "EpicCatto Myth");

    public ClickGui() {
        super("ClickGui", "Show modules and cool stuff", Category.RENDER, Keyboard.KEY_RSHIFT);
        addSettings(mode);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.instance.mythClickGui);
        setEnabled(false);
        super.onEnable();
    }
}
