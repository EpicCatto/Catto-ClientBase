package catto.uwu.ui.hud;

import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.optifine.util.FontUtils;
import org.lwjgl.input.Mouse;

public class Element {
    public float x, y, width, height;
    public boolean visible;
    public String name;
    public Minecraft mc = Minecraft.getMinecraft();
    public ScaledResolution sr = new ScaledResolution(mc);
    public FontRenderer fr = mc.fontRendererObj;

    public Element(String name, float x, float y, float width, float height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.visible = visible;
    }

    public void draw() {

    }

    public void drawBox() {
        Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), 0x80000000);
        FontLoaders.Sfui14.drawStringWithShadow(name, (int) x, (int) y + height + 2, -1);
    }

    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height && button == 0;
    }

    public void drag() {
        x += (float) (Mouse.getDX() / (sr.getScaleFactor() + 1E-10));
        y -= (float) (Mouse.getDY() / (sr.getScaleFactor() + 1E-10));
        x = Math.max(Math.min(x, sr.getScaledWidth() - width), 0);
        y = Math.max(Math.min(y, sr.getScaledHeight() - height), 0);
    }
}
