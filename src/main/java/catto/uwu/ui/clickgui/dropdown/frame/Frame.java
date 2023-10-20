package catto.uwu.ui.clickgui.dropdown.frame;

import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.ModuleComp;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Frame {
    public double x, y, x2, y2;
    public Category category;
    public ArrayList<Module> loadedModules;
    public final ArrayList<ModuleComp> moduleComps = new ArrayList<>();
    public boolean expanded = false;


    public Frame(double x, double y, Category category) {
        this.x = x;
        this.y = y;
        this.x2 = x + 100;
        this.y2 = y + 10;
        this.category = category;

        loadedModules = (ArrayList<Module>) ModuleManager.getModulesByCategory(category);
        loadedModules.sort((o1, o2) -> FontLoaders.Sfui20.getStringWidth(o2.getName()) - FontLoaders.Sfui20.getStringWidth(o1.getName()));

        AtomicReference<Double> moduleOffset = new AtomicReference<>((double) 0);
        for (Module module : loadedModules) {
            ModuleComp moduleComp = new ModuleComp(x, y2 + 1 + moduleOffset.get(), moduleOffset.get(), this, module);
            moduleComps.add(moduleComp);
            moduleOffset.set(moduleOffset.get() + moduleComp.height());
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        background
        Gui.drawRect((int)x, (int)y - 5, (int)x2, (int)y2, new Color(0, 0, 0, 150).getRGB());
//        line
//        Gui.drawRect((int)x, (int)y2, (int)x2, (int)y2 + 1, -1);
        RenderUtils.drawGradientSideways((int)x, (int)y2, (int)x2, (int)y2 + 1, ColorUtil.mythColor(100000), ColorUtil.mythColor(1000000));
        FontLoaders.Sfui20.drawString(category.getName(), (int)x + 5, (int)y, -1);

        if (!expanded)
            return;
        moduleComps.forEach(moduleComp -> {
            moduleComp.x = x;
            moduleComp.y = y2 + 1 + moduleComp.offset;

            moduleComp.drawScreen(mouseX, mouseY);
        });
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!expanded)
            return;
        moduleComps.forEach(moduleComp -> {
            moduleComp.mouseClicked(mouseX, mouseY, mouseButton);
        });
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!expanded)
            return;
        moduleComps.forEach(moduleComp -> {
            moduleComp.mouseReleased(mouseX, mouseY, state);
        });
    }

}
