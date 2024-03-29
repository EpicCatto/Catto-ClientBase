package catto.uwu.module.modules.render;

import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event2D;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.utils.render.tweek.ColorTween;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

@ModuleData(name = "TestColorTween", description = "Test ColorTween", category = Category.RENDER)
public class TestColorTween extends Module {

    private final ColorTween colorTween = new ColorTween(Color.WHITE, Color.BLACK, 1000);
    private final NumberSetting speed = new NumberSetting("Speed", this, 1000, 1, 10000, true);
    private final NumberSetting coler1R = new NumberSetting("Color1 Red", this, 255, 0, 255, true);
    private final NumberSetting coler1G = new NumberSetting("Color1 Green", this, 255, 0, 255, true);
    private final NumberSetting coler1B = new NumberSetting("Color1 Blue", this, 255, 0, 255, true);
    private final NumberSetting color1A = new NumberSetting("Color1 Alpha", this, 255, 0, 255, true);
    private final NumberSetting coler2R = new NumberSetting("Color2 Red", this, 0, 0, 255, true);
    private final NumberSetting coler2G = new NumberSetting("Color2 Green", this, 0, 0, 255, true);
    private final NumberSetting coler2B = new NumberSetting("Color2 Blue", this, 0, 0, 255, true);
    private final NumberSetting color2A = new NumberSetting("Color2 Alpha", this, 255, 0, 255, true);

    public TestColorTween() {
        addSettings(speed, coler1R, coler1G, coler1B, color1A, coler2R, coler2G, coler2B, color2A);
    }

    @EventTarget
    public void onRender2D(Event2D event){
        ScaledResolution sr = new ScaledResolution(mc);
//        RenderUtils.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(coler1R.getValue().intValue(), coler1G.getValue().intValue(), coler1B.getValue().intValue(), color1A.getValueInt()).getRGB(), new Color(coler2R.getValue().intValue(), coler2G.getValue().intValue(), coler2B.getValue().intValue(), color2A.getValueInt()).getRGB());
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), colorTween.getColor());
//        FontLoaders.Sfui35.drawString("uwu", 100, 100, colorTween.getColor());
        colorTween.update();
    }

    @Override
    public void onEnable() {
        colorTween.setColor1(new Color(coler1R.getValue().intValue(), coler1G.getValue().intValue(), coler1B.getValue().intValue(), color1A.getValueInt()).getRGB());
        colorTween.setColor2(new Color(coler2R.getValue().intValue(), coler2G.getValue().intValue(), coler2B.getValue().intValue(), color2A.getValueInt()).getRGB());
        colorTween.setTime(speed.getValueInt());
        colorTween.reset();
    }
}
