package epiccatto.catto.module.modules.render;

import epiccatto.catto.Client;
import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.Event2D;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleManager;
import epiccatto.catto.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Show module on your screen cuz yes :D", Category.RENDER, 0);
    }

    @EventTarget
    public void onRender2D(Event2D event){
        mc.fontRendererObj.drawStringWithShadow(Client.clientName, 2, 2, 0xFFFFFF);
        drawOldArraylist();
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
    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }
}
