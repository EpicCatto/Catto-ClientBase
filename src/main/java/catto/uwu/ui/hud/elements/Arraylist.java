package catto.uwu.ui.hud.elements;

import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.ui.hud.Element;
import catto.uwu.utils.render.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class Arraylist extends Element {
    public Arraylist(String name) {
        super(name, 0, 0, 90, 0);
    }

    @Override
    public void draw() {
        boolean left = (float) sr.getScaledWidth() / 2 > x + width / 2;
        boolean top = (float) sr.getScaledHeight() / 2 > y + height / 2;

        drawOldArraylist((int) x, (int) y, left, !top);

        super.draw();
    }

    private void drawOldArraylist(int ix, int iy, boolean left, boolean bottom) {
        //TODO: Draw Arraylist of enabled modules
        // from my original myth base

        ArrayList<Module> sorted = new ArrayList<Module>();

        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled() || !m.isShow())
                continue;
            sorted.add(m);
        }

//        Sort from longest to shortest
//        if bottom is true, sort from shortest to longest
//        sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));
        if (bottom)
            sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())) - mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())));
        else
            sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));

        int y = iy;
        int mx = 0;
        int count = 0;
        for (Module m : sorted) {
            String name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
            float x = 0;
            x = (left ? 2 + ix : (ix+width) - mc.fontRendererObj.getStringWidth(name) + 1);
            if (mx < mc.fontRendererObj.getStringWidth(name))
                mx = mc.fontRendererObj.getStringWidth(name);
            mc.fontRendererObj.drawString(name, (int) (x - 2), y + 2, ColorUtil.getColor("Myth", count * 200));

            mc.fontRendererObj.drawStringWithShadow(name, x - 2, y + 2, ColorUtil.getColor("Myth", count * 200));
            y += 10;
            count++;

        }
        this.height = count * 10;
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

}
