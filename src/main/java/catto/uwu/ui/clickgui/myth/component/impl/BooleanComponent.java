package catto.uwu.ui.clickgui.myth.component.impl;


import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.ui.clickgui.myth.MythClickGui;
import catto.uwu.ui.clickgui.myth.component.Component;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.RenderUtils;

import java.awt.*;


public class BooleanComponent extends Component {
    public BooleanComponent(double x, double y, MythClickGui parent, Module module, BooleanSetting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        FontLoaders.Sfui20.drawString(setting.getName(), (int)(parent.x + x - 55), (int)(parent.y + y + 1), new Color(200,200,200).getRGB());
        RenderUtils.drawCircle((float) parent.x + x - 65, (double)parent.y + y + 5, 5f,((BooleanSetting)setting).getValue() ? new Color(0, 216, 245).getRGB() : new Color(30,30,30).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovered(mouseX, mouseY, parent.x + x - 70, parent.y + y, parent.x + x + 10 - 70, parent.y + y + 10) && mouseButton == 0) {
            ((BooleanSetting)setting).setValue(!((BooleanSetting)setting).getValue());
        }
    }

    @Override
    public int height() {
        return setting.isVisible() ? 20 : 0;
    }
}
