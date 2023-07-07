package epiccatto.catto.ui.clickgui.myth.component.impl;

import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.settings.impl.ModeSetting;
import epiccatto.catto.ui.clickgui.myth.MythClickGui;
import epiccatto.catto.ui.clickgui.myth.component.Component;
import epiccatto.catto.utils.font.FontLoaders;

import java.awt.*;

public class ModeComponent extends Component {

    private int modeIndex;

    public ModeComponent(double x, double y, MythClickGui parent, Module module, ModeSetting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        FontLoaders.Sfui20.drawString(setting.getName() + ": " + setting.getValue(), (int)(parent.x + x - 69), (int)(parent.y + y + 1), new Color(200,200,200).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (isHovered(mouseX, mouseY, parent.x + x - 70, parent.y + y, parent.x + x, parent.y + y + 10) && mouseButton == 0) {
            int max = ((ModeSetting)setting).getOptions().length;
            if (modeIndex + 1 >= max) {
                modeIndex = 0;
            } else {
                modeIndex++;
            }
            ((ModeSetting)setting).setValue(((ModeSetting)setting).getOptions()[modeIndex]);
        }

    }

    @Override
    public int height() {
        return setting.isVisible() ? 20 : 0;
    }
}
