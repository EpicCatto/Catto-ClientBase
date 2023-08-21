package catto.uwu.ui.clickgui.myth.component.impl;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.ui.clickgui.myth.MythClickGui;
import catto.uwu.ui.clickgui.myth.component.Component;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberComponent extends Component {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;

    public NumberComponent(double x, double y, MythClickGui parent, Module module, NumberSetting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        double min = ((NumberSetting)setting).getMin();
        double max = ((NumberSetting)setting).getMax();
        double l = 90;

        renderWidth = (l) * (((NumberSetting)setting).getValue() - min) / (max - min);
        renderWidth2 = (l) * (((NumberSetting)setting).getMax() - min) / (max - min);

        double diff = Math.min(l, Math.max(0, mouseX - (parent.x + x - 70)));
        if (dragging) {
            if (diff == 0) {
                ((NumberSetting)setting).setValue(((NumberSetting)setting).getMin());
            }
            else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 2);
                ((NumberSetting)setting).setValue(newValue);
            }
        }
        Gui.drawRect(parent.x + x - 70, parent.y + y + 10,parent.x + x - 70 + renderWidth2, parent.y + y + 15, new Color(0, 216, 245).getRGB());
        RenderUtils.drawGradientSideways(parent.x + x - 70, parent.y + y + 10, parent.x + x - 70 + renderWidth, parent.y + y + 15, ColorUtil.mythColor(100000), ColorUtil.mythColor(1000000));
        GlStateManager.disableBlend();
        FontLoaders.Sfui20.drawString(setting.getName() + ": " + ((NumberSetting)setting).getValue(),(int)(parent.x + x - 70),(int)(parent.y + y), new Color(200,200,200).getRGB());
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovered(mouseX, mouseY, parent.x + x - 70, parent.y + y + 10,parent.x + x - 70 + renderWidth2, parent.y + y + 20) && mouseButton == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }


    @Override
    public int height() {
        return setting.isVisible() ? 20 : 0;
    }
}
