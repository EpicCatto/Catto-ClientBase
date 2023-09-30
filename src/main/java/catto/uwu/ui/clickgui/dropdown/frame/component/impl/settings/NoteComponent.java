package catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.dropdown.frame.component.Component;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class NoteComponent extends Component {
    private NoteSetting noteSetting;
    public NoteComponent(double x, double y, Frame parent, Module module, NoteSetting noteSetting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.noteSetting = noteSetting;
        this.settings.add(noteSetting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect((int) x, (int) y, (int) x + 90, (int) y + 15, new Color(0, 0, 0, 150).getRGB());
        FontLoaders.Sfui18.drawStringWithShadow(noteSetting.getName(), (float) x + 2 , (float) y + 5, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, (int) x, (int) y, (int) x + 90, (int) y + 15)) {
            noteSetting.setValue(!noteSetting.getValue());
        }
    }

    @Override
    public int height() {
        return 15;
    }

}
