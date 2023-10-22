package catto.uwu.ui.hud.elements;

import catto.uwu.Client;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.modules.render.HUD;
import catto.uwu.ui.hud.Element;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.render.RenderUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
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


        ArrayList<Module> sorted = new ArrayList<>(ModuleManager.getModules());
        if (HUD.getInstance().arraylistFont.getValue()) {
            //TODO: Draw Arraylist of enabled modules
            // from my original myth base

            //            if (!m.isEnabled() || !m.isShow()) {
            //                continue;
            //            }
            if (bottom)
                sorted.sort((o1, o2) -> FontLoaders.Sfui18.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())) - fr.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())));
            else
                sorted.sort((o1, o2) -> FontLoaders.Sfui18.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - fr.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));

            int y = iy;
            int mx = 0;
            int count = 0;
            for (Module m : sorted) {
                String name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
                boolean shouldVisible = m.isEnabled() && m.isShow();
//            (left ? ix - fr.getStringWidth(name) - 2 : ix + width + 2)
//            float hideX = (left ? ix - fr.getStringWidth(name) - 2 : ix + width + 2);
                float hideX = (left ? -FontLoaders.Sfui18.getStringWidth(name) - 2 : sr.getScaledWidth() + FontLoaders.Sfui18.getStringWidth(name) + 2);
                float endX = shouldVisible ? (left ? 2 + ix : (ix + width) - FontLoaders.Sfui18.getStringWidth(name) + 1) : hideX;

//            ChatUtil.sendChatMessageWPrefix(endX + "");

                float animationX = animate(m.getX(), endX, 0.00420f);
                float animationY = animate(m.getY(), y, 0.0042f);
//            if return nan, then set to 0
                if (Float.isNaN(animationX)) animationX = 0;
                if (Float.isNaN(animationY)) animationY = 0;

                m.setX(animationX);
                m.setY(animationY);


                if (left ? (m.getX() > hideX) : (m.getX() < hideX)) {
                    if (mx < FontLoaders.Sfui18.getStringWidth(name))
                        mx = FontLoaders.Sfui18.getStringWidth(name);


                    RenderUtils.drawRoundedRect(m.getX() - 4, count == 0 ? m.getY() : m.getY() + 1, m.getX() + FontLoaders.Sfui18.getStringWidth(name),m.getY() +  FontLoaders.Sfui18.getHeight() + 4, 2, new Color(33, 33, 37, 155).getRGB());
                    Client.instance.blurProcessor.blur(m.getX() - 4, count == 0 ? m.getY() : m.getY() + 1, FontLoaders.Sfui18.getStringWidth(name) + 4,FontLoaders.Sfui18.getHeight() + count == 0 ? 3 : 4, 1);
                    FontLoaders.Sfui18.drawString(name, m.getX() - 2, m.getY() + 2, ColorUtil.getColor("Myth", count * 200));
                }

                if (shouldVisible) {
                    y += 10;
                    count++;
                }

            }
            this.height = count * 10;

        }else {
            //TODO: Draw Arraylist of enabled modules
            // from my original myth base

            //            if (!m.isEnabled() || !m.isShow()) {
            //                continue;
            //            }
            if (bottom)
                sorted.sort((o1, o2) -> fr.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())) - fr.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())));
            else
                sorted.sort((o1, o2) -> fr.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - fr.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));

            int y = iy;
            int mx = 0;
            int count = 0;
            for (Module m : sorted) {
                String name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
                boolean shouldVisible = m.isEnabled() && m.isShow();
//            (left ? ix - fr.getStringWidth(name) - 2 : ix + width + 2)
//            float hideX = (left ? ix - fr.getStringWidth(name) - 2 : ix + width + 2);
                float hideX = (left ? -fr.getStringWidth(name) - 2 : sr.getScaledWidth() + fr.getStringWidth(name) + 2);
                float endX = shouldVisible ? (left ? 2 + ix : (ix + width) - fr.getStringWidth(name) + 1) : hideX;

//            ChatUtil.sendChatMessageWPrefix(endX + "");

                float animationX = animate(m.getX(), endX, 0.00420f);
                float animationY = animate(m.getY(), y, 0.0042f);
//            if return nan, then set to 0
                if (Float.isNaN(animationX)) animationX = 0;
                if (Float.isNaN(animationY)) animationY = 0;

                m.setX(animationX);
                m.setY(animationY);


                if (left ? (m.getX() > hideX) : (m.getX() < hideX)) {
                    if (mx < fr.getStringWidth(name))
                        mx = fr.getStringWidth(name);

                    fr.drawStringWithShadow(name, m.getX() - 2, m.getY() + 2, ColorUtil.getColor("Myth", count * 200));
                }

                if (shouldVisible) {
                    y += 10;
                    count++;
                }

            }
            this.height = count * 10;

        }
    }


    public static float animate(float current, float end, float minSpeed) {
        if (end == current) {
            return current; // If the current and end values are equal, no animation is needed.
        }

        float movement = (end - current) * 0.025f * 240 / Minecraft.getDebugFPS();

        if (movement > 0) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }

        return current + movement;
    }

}
