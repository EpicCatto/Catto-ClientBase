package epiccatto.catto.ui.clickgui.myth.component.impl;

import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.settings.impl.NoteSetting;
import epiccatto.catto.ui.clickgui.myth.MythClickGui;
import epiccatto.catto.ui.clickgui.myth.component.Component;
import epiccatto.catto.utils.font.FontLoaders;


import java.awt.*;

public class NoteComponent extends Component {
    public NoteComponent(double x, double y, MythClickGui parent, Module module, NoteSetting setting) {
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
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public int height() {
        return setting.isVisible() ? FontLoaders.Sfui20.getHeight() + 5 : 0;
    }
}
