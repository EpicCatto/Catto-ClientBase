package catto.uwu.ui.hud.elements.sessioninfo;

import catto.uwu.Client;
import catto.uwu.ui.hud.Element;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SessionInfo extends Element{
    private final HashMap<String, String> info;

    public SessionInfo(String name) {
        super(name, 0, 0, 0, 0);
        info = new HashMap<>();
    }

    @Override
    public void draw() {
        drawInfo((int) x, (int) y);
        super.draw();
    }

    private void drawInfo(int x, int y) {
        ScaledResolution sr = new ScaledResolution(mc);
//        int x = -5;
//        int y = 20;
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

        this.width = longest + 50;
        this.height = ((FontLoaders.Sfui18.getHeight() + 2) * countyes + 20) + 5;

        //Backround
//        RenderUtils.drawRoundedRect(x, y, x + width, y + height, 10f, new Color(0, 0, 0, 150).getRGB());
//        Client.instance.blurProcessor.blur(x, y, width, height, false);
        Client.instance.blurProcessor.bloom(x, y, (int) width, (int) height, 8, new Color(0, 0, 0, 163));

        FontLoaders.Sfui18.drawString(name, (int) (x + (width/2) - FontLoaders.Sfui18.getStringWidth(name)/2), y + 5, -1);

//        underline
        Gui.drawRect(x + (width/2) - FontLoaders.Sfui18.getStringWidth(name)/2, y + 5 + FontLoaders.Sfui18.getHeight() + 2, x + (width/2) + FontLoaders.Sfui18.getStringWidth(name)/2, y + 5 + FontLoaders.Sfui18.getHeight() + 3, -1);

        // list all the info
        for (String s : sorted) {
            FontLoaders.Sfui18.drawStringWithShadow(s + ": " + info.get(s), x + (width/2) - FontLoaders.Sfui18.getStringWidth(name)/2, y + 20 + ((FontLoaders.Sfui18.getHeight() + 4) * count), color);
            count++;
        }
    }


    public void addInfo(String name, Object value) {
        info.put(name, String.valueOf(value));
    }

    public void removeInfo(String name) {
        info.remove(name);
    }
}
