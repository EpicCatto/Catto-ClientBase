package catto.uwu.ui.clickgui.myth.component.impl;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.StringSetting;
import catto.uwu.ui.clickgui.myth.MythClickGui;
import catto.uwu.ui.clickgui.myth.component.Component;
import catto.uwu.ui.clickgui.myth.component.utils.SettingsInputBox;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class StringComponent extends Component {

    private final SettingsInputBox value;

    public StringComponent(double x, double y, MythClickGui parent, Module module, StringSetting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;

        value = new SettingsInputBox(1, Minecraft.getMinecraft().fontRendererObj, 20, 150, 50, 20);
        value.setText(setting.getValue());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        value.yPosition = (int)(parent.y + y + 1);
        value.xPosition = (int)(parent.x + x - 66) + FontLoaders.Sfui20.getStringWidth(setting.getName());
        value.width = FontLoaders.Sfui20.getStringWidth((String) setting.getValue()) + 20;
        value.height = FontLoaders.Sfui20.getHeight();
        //value.drawTextBox();
        value.setEnableBackgroundDrawing(false);
        ((StringSetting)setting).setValue(value.getText());
        //RenderUtils.drawRoundedRect(value.xPosition, value.yPosition, value.xPosition + value.getWidth(), value.yPosition + value.height, 0, new Color(143, 229, 221, 52).getRGB());

        FontLoaders.Sfui20.drawString(setting.getName() + ": " + setting.getValue(), (int)(parent.x + x - 69), (int)(parent.y + y + 1), new Color(200,200,200).getRGB());

        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        value.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (value.isFocused()) {
            value.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public int height() {
        return setting.isVisible() ? FontLoaders.Sfui20.getHeight() + 5 : 0;
    }

}
