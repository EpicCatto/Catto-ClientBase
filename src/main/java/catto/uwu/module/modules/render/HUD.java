package catto.uwu.module.modules.render;

import catto.uwu.Client;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event2D;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings.NoteComponent;
import catto.uwu.ui.hud.Element;
import catto.uwu.ui.hud.elements.Arraylist;
import catto.uwu.ui.hud.elements.Label;
import catto.uwu.ui.hud.elements.Watermark;
import catto.uwu.ui.hud.elements.sessioninfo.SessionInfo;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

@ModuleData(name = "HUD", description = "Show module on your screen cuz yes :D", category = Category.RENDER)
public class HUD extends Module {

    private static HUD instance = null;

    private final ArrayList<Element> elements = new ArrayList<>();
    private Element dragging = null;

    private final SessionInfo debugInfo = new SessionInfo("Debug Info");

//    Settingss
    public NoteSetting watermarkSettings = new NoteSetting("Watermark Settings", this);
    public NoteSetting arraylistSettings = new NoteSetting("Arraylist Settings", this);
    public NoteSetting sessionInfoSettings = new NoteSetting("Session Info Settings", this);

//    Watermark
    public ModeSetting watermarkStyle = new ModeSetting("Style", this, new String[]{"Myth", "Sense", "Exhi", "Custom"}, "Myth", watermarkSettings);
    public BooleanSetting watermarkFont = new BooleanSetting("Font", this, true, watermarkSettings);
    public ModeSetting watermarkFontStyle = new ModeSetting("Fonts", this, new String[]{"SF-UI", "Product Sans", "Ali"}, "Normal", watermarkSettings);

    public HUD() {
        instance = this;
        elements.add(new Watermark("Client Brand", Client.clientName));
        elements.add(new Arraylist("Modules Array"));
        elements.add(debugInfo);

        addSettings(watermarkSettings, watermarkStyle, watermarkFont);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        double bps = Math.round((Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20) * 100.0) / 100.0;

        debugInfo.addInfo("FPS", String.valueOf(Minecraft.getDebugFPS()));
        debugInfo.addInfo("BPS", String.valueOf(bps));
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        drawElements();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Element element : elements) {
            if (element.mouseClicked(mouseX, mouseY, mouseButton)) {
                dragging = element;
            }
        }
    }

    public void mouseReleased() {
        dragging = null;
    }


    private void drawElements() {
        ScaledResolution sr = new ScaledResolution(mc);
        for (Element element : elements) {
            element.sr = sr;
            if (element == dragging) {
                element.drag();
            }

            if (mc.currentScreen instanceof GuiChat)
                element.drawBox();
            element.draw();
        }
    }




    public static HUD getInstance() {
        return instance;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }
}
