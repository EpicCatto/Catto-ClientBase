package epiccatto.catto.ui.clickgui.myth.component.impl;

import epiccatto.catto.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.settings.impl.ColorSetting;
import epiccatto.catto.ui.clickgui.myth.MythClickGui;
import epiccatto.catto.ui.clickgui.myth.component.Component;

import java.awt.*;

public class ColorComponent extends Component {

    public ColorComponent(double x, double y, MythClickGui parent, Module module, ColorSetting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontLoaders.Sfui20.drawString(setting.getName(), (int)(parent.x + x - 55), (int)(parent.y + y + 1), new Color(200,200,200).getRGB());

        for (float i = 0; i < 44f; i += (43.5f / 50)) {
            Gui.drawRect((int) (1 + i + (60F / 50)), 1 + 11, (int) (x + 1 + i + (60F / 50)), 1 + 20, Color.getHSBColor(i / 50, 1, 1).getRGB());
        }
        super.drawScreen(mouseX, mouseY);
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public int height() {
        return setting.isVisible() ? 30 : 0;
    }

    public int getColor(int red, int green, int blue, int alpha) {
        int color = MathHelper.clamp_int(alpha, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        color |= MathHelper.clamp_int(blue, 0, 255);
        return color;
    }
    public int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }
    public int getColor(int brightness) {
        return getColor(brightness, 255);
    }
}
