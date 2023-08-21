package catto.uwu.module.modules.render;

import catto.uwu.Client;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event2D;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

@ModuleData(name = "HUD", description = "Show module on your screen cuz yes :D", category = Category.RENDER)
public class HUD extends Module {

    //Info
    private static final HashMap<String, String> info = new HashMap<>();

    @EventTarget
    public void onMotion(EventMotion event) {
        double bps = Math.round((Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20) * 100.0) / 100.0;

        info.put("FPS", String.valueOf(Minecraft.getDebugFPS()));
        info.put("BPS", String.valueOf(bps));
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        mc.fontRendererObj.drawStringWithShadow(Client.clientName, 2, 2, 0xFFFFFF);
        drawOldArraylist();
        drawInfo();
    }

    private void drawOldArraylist() {
        //TODO: Draw Arraylist of enabled modules
        // from my original myth base

        ArrayList<Module> sorted = new ArrayList<Module>();

        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled() || !m.isShow())
                continue;
            sorted.add(m);
        }

        sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));

        int y = 0;
        int count = 0;
        for (Module m : sorted) {
            String name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
            float x = 0;
            x = width() - mc.fontRendererObj.getStringWidth(name);
            mc.fontRendererObj.drawString(name, (int) (x - 2), y + 2, ColorUtil.getColor("Myth", count * 200));

            mc.fontRendererObj.drawStringWithShadow(name, x - 2, y + 2, ColorUtil.getColor("Myth", count * 200));
            y += 10;
            count++;

        }
    }

    private void drawInfo() {
        ScaledResolution sr = new ScaledResolution(mc);
        int x = -5;
        int y = 20;
        int count = 0;
        int color = -1;

        //Sort info
        ArrayList<String> sorted = new ArrayList<String>();
        for (String s : info.keySet()) {
            sorted.add(s);
        }
        sorted.sort((o1, o2) -> FontLoaders.Sfui18.getStringWidth(o2 + ": ") - FontLoaders.Sfui18.getStringWidth(o1));

        //get the longest string in the info map
        int longest = 0;
        int countyes = 0;
        for (String s : sorted) {
            if (FontLoaders.Sfui18.getStringWidth(s) > longest) {
                longest = FontLoaders.Sfui18.getStringWidth(s);
            }
            countyes++;
        }

        //Draw head
        Gui.drawRect(x + 10, y - FontLoaders.Sfui18.getHeight() + 4, x + 50 + longest, y + 10, new Color(0, 0, 0, 161).getRGB());
        FontLoaders.Sfui18.drawString("Info", x + (longest+50)/2 - FontLoaders.Sfui18.getStringWidth("info")/4, y, -1);

        //Backround
        Gui.drawRect(x + 10, y + 10, x + 50 + longest, y + 20 + ((FontLoaders.Sfui18.getHeight() + 2) * countyes), new Color(0, 0, 0, 100).getRGB());


        // list all the info
        for (String s : sorted) {
            FontLoaders.Sfui18.drawStringWithShadow(s + ": " + info.get(s), x + 15, y + 15 + ((FontLoaders.Sfui18.getHeight() + 4) * count), color);
            count++;
        }
    }


    public static void addInfo(String name, Object value) {
        info.put(name, String.valueOf(value));
    }

    public static void removeInfo(String name) {
        info.remove(name);
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }
}
