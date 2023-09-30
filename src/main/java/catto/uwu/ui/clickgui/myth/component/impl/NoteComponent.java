package catto.uwu.ui.clickgui.myth.component.impl;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.ui.clickgui.myth.MythClickGui;
import catto.uwu.ui.clickgui.myth.component.Component;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;


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
        FontLoaders.Sfui20.drawString(((((NoteSetting)setting).getValue()) ? " V ": " > ") + setting.getName(), (int)(parent.x + x - 55), (int)(parent.y + y + 1), new Color(200,200,200).getRGB());

//        draw hitbox
        Gui.drawRect(parent.x + x - FontLoaders.Sfui20.getStringWidth(setting.getName())/2 - 20, parent.y + y, parent.x + x + FontLoaders.Sfui20.getStringWidth(setting.getName())/2, parent.y + y + 10, new Color(0, 0, 0, 100).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
//        cover the text
        if (isHovered(mouseX, mouseY, parent.x + x - FontLoaders.Sfui20.getStringWidth(setting.getName())/2 - 20, parent.y + y, parent.x + x + FontLoaders.Sfui20.getStringWidth(setting.getName())/2, parent.y + y + 10) && mouseButton == 0) {
            ChatUtil.sendChatMessageWOutPrefix("Note: " + setting.getName());
            ((NoteSetting)setting).setValue(!((NoteSetting)setting).getValue());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public int height() {
        return setting.isVisible() ? FontLoaders.Sfui20.getHeight() + 5 : 0;
    }
}
