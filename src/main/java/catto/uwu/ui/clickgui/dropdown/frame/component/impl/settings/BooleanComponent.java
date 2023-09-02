package catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.dropdown.frame.component.Component;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class BooleanComponent extends Component {
    private final BooleanSetting booleanSetting;
    public BooleanComponent(double x, double y, Frame parent, Module module, BooleanSetting booleanSetting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.booleanSetting = booleanSetting;
        this.settings.add(booleanSetting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect((int) x, (int) y, (int) x + 90, (int) y + 15, booleanSetting.getParentNote() != null ? new Color(0, 0, 0, 200).getRGB() : new Color(0, 0, 0, 150).getRGB());
        FontLoaders.Sfui18.drawStringWithShadow(booleanSetting.getName(), (float) x + 2, (float) y + 5, -1);
        Gui.drawRect((int) x + 75, (int) y + 2, (int) x + 85, (int) y + 12, booleanSetting.getValue() ? new Color(198, 109, 255, 255).getRGB() : new Color(0, 0, 0, 150).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, (int) x + 75, (int) y + 2, (int) x + 85, (int) y + 12)) {
            booleanSetting.setValue(!booleanSetting.getValue());
        }
    }

    @Override
    public int height() {
        return 15;
    }

}
