package catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.dropdown.frame.component.Component;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberComponent extends Component {
    private final NumberSetting numberSetting;
    private boolean dragging;

    public NumberComponent(double x, double y, Frame parent, Module module, NumberSetting noteSetting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.numberSetting = noteSetting;
        this.settings.add(noteSetting);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

        double min = numberSetting.getMin();
        double max = numberSetting.getMax();
        double l = 90;

        double renderWidth = (l) * (numberSetting.getValue() - min) / (max - min);

        double diff = Math.min(l, Math.max(0, mouseX - x));

        if (dragging) {
            if (diff == 0) {
                numberSetting.setValue(numberSetting.getMin());
            } else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 2);
                numberSetting.setValue(newValue);
            }
        }

        Gui.drawRect((int) x, (int) y, (int) x + 90, (int) y + 15, numberSetting.getParentNote() != null ? new Color(0, 0, 0, 200).getRGB() : new Color(0, 0, 0, 150).getRGB());
        Gui.drawRect((int) x, (int) y + 13, (int) x + renderWidth, (int) y + 15, new Color(0, 216, 245).getRGB());
        FontLoaders.Sfui18.drawString(numberSetting.getName() + ": " + numberSetting.getValue(), (int) x + 2, (int) y + FontLoaders.Sfui18.getHeight()/2, -1);

//        draw where diff is
//        Gui.drawRect((int) x + (int) diff, (int) y, (int) x + (int) diff + 1, (int) y + 15, new Color(255, 255, 255).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY,  x, (int) y, (int) x + 90, (int) y + 15)) {
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
        return 15;
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
