package catto.uwu.ui.hud.elements.sessioninfo;

import catto.uwu.ui.hud.Element;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

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
        this.height = (FontLoaders.Sfui18.getHeight() + 2) * countyes + 20;

        //Draw head
        Gui.drawRect(x + 10, y - FontLoaders.Sfui18.getHeight() + 4, x + 50 + longest, y + 10, new Color(0, 0, 0, 161).getRGB());
        FontLoaders.Sfui18.drawString(name, x + (longest+50)/2 - FontLoaders.Sfui18.getStringWidth(name)/4, y, -1);

        //Backround
        Gui.drawRect(x + 10, y + 10, x + 50 + longest, y + 20 + ((FontLoaders.Sfui18.getHeight() + 2) * countyes), new Color(0, 0, 0, 100).getRGB());


        // list all the info
        for (String s : sorted) {
            FontLoaders.Sfui18.drawStringWithShadow(s + ": " + info.get(s), x + 15, y + 15 + ((FontLoaders.Sfui18.getHeight() + 4) * count), color);
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
