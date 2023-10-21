package catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.dropdown.frame.component.Component;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModeComponent extends Component {
    private final ModeSetting modeSetting;
    public ModeComponent(double x, double y, Frame parent, Module module, ModeSetting modeSetting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.modeSetting = modeSetting;
        this.settings.add(modeSetting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect((int) x, (int) y, (int) x + 90, (int) y + 15, modeSetting.getParentNote() != null ? new Color(0, 0, 0, 200).getRGB() : new Color(0, 0, 0, 150).getRGB());
        FontLoaders.Sfui18.drawStringWithShadow(modeSetting.getName() + ": " + modeSetting.getValue(), (float) x + 2, (float) y + 5, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, (int) x, (int) y, (int) x + 90, (int) y + 15)) {
            modeSetting.cycle();
        }
    }

    @Override
    public int height() {
        return 15;
    }

}
