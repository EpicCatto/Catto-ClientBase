package catto.uwu.ui.hud;

import catto.uwu.Client;
import catto.uwu.module.settings.Serializable;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.RenderUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.optifine.util.FontUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class Element implements Serializable {
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
        Client.instance.blurProcessor.blur((int) x, (int) y, (int) width, (int) height, true);
        Client.instance.blurProcessor.bloom((int) x, (int) y, (int) width, (int) height, 8, new Color(33, 33, 37, 155));
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



    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();

        object.addProperty("name", name);
        object.addProperty("x", x);
        object.addProperty("y", y);
        object.addProperty("width", width);
        object.addProperty("height", height);
        object.addProperty("visible", visible);
        object.addProperty("type", getClass().getSimpleName());

        JsonObject settings = new JsonObject();
        save(settings);
        object.add("values", settings);

        return object;
    }

    @Override
    public void load(JsonObject object, boolean loadConfig) {
        name = object.get("name").getAsString();
        x = object.get("x").getAsFloat();
        y = object.get("y").getAsFloat();
        width = object.get("width").getAsFloat();
        height = object.get("height").getAsFloat();
        visible = object.get("visible").getAsBoolean();

        load(object.get("values").getAsJsonObject());
    }
    public void save(JsonObject object){}
    public void load(JsonObject object){}
}
