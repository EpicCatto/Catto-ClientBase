package catto.uwu.ui.hud.elements;

import catto.uwu.Client;
import catto.uwu.ui.hud.Element;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.render.ColorUtil;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class Label extends Element {
    public String text;
    public boolean dropShadow, bloom;
    public Color bloomColor;


    public Label(String name, String text) {
        super(name, 0, 0, 0, 0);
        this.text = text;
        this.dropShadow = true;
        this.bloom = false;
        this.bloomColor = new Color(33, 33, 37, 155);
    }
    public Label(String name, String text, boolean dropShadow, boolean bloom, Color color) {
        super(name, 0, 0, 0, 0);
        this.text = text;
        this.dropShadow = dropShadow;
        this.bloom = bloom;
        this.bloomColor = color;
    }

    @Override
    public void draw() {
        String displayedText = ChatUtil.formatLabel(text);

        if (bloom)
            Client.instance.blurProcessor.bloom((int) x, (int) y, (int) width, (int) height, 8, bloomColor);

        if (dropShadow)
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(displayedText, x, y, -1);
        else
            Minecraft.getMinecraft().fontRendererObj.drawString(displayedText, (int) x, (int) y, -1);


        this.width = fr.getStringWidth(displayedText);
        this.height = fr.FONT_HEIGHT;

    }

    @Override
    public void save(JsonObject object) {
        object.addProperty("text", text);
        object.addProperty("dropShadow", dropShadow);
        object.addProperty("bloom", bloom);
        object.addProperty("bloomColor", ColorUtil.toHex(bloomColor));
        super.save(object);
    }

    @Override
    public void load(JsonObject object) {
        text = object.get("text").getAsString();
        dropShadow = object.get("dropShadow").getAsBoolean();
        bloom = object.get("bloom").getAsBoolean();
        bloomColor = new Color(ColorUtil.fromHEX(object.get("bloomColor").getAsString()));
        super.load(object);
    }
}
