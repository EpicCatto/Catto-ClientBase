package catto.uwu.module.modules.render;

import catto.uwu.Client;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event2D;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.ui.hud.Element;
import catto.uwu.ui.hud.elements.Arraylist;
import catto.uwu.ui.hud.elements.Label;
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



    private final HUD instance;

    private ArrayList<Element> elements = new ArrayList<>();
    private Element dragging = null;

    private SessionInfo debugInfo = new SessionInfo("Debug Info");

    public HUD() {
        instance = this;
        elements.add(new Label("Client Brand", Client.clientName));
        elements.add(new Arraylist("Modules Array"));
        elements.add(debugInfo);
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





    public HUD getInstance() {
        return instance;
    }
}
