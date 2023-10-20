package catto.uwu.ui.hud.elements;

import catto.uwu.Client;
import catto.uwu.module.modules.render.HUD;
import catto.uwu.ui.hud.Element;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import catto.uwu.utils.render.ColorUtil;
import catto.uwu.utils.render.RenderUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.concurrent.Callable;

public class Watermark extends Element {
    private String text;

//    Default text for watermark
    private String defaultMyth;
    private String defaultSense;
    private String defaultExhi;


    public Watermark(String name, String text) {
        super(name, 0, 0, 0, 0);
        this.text = text;
        this.defaultMyth = text;
        this.defaultSense = text + " | %fps% fps | %server%";
        this.defaultExhi = text + " [&f%pctime%&7] [&f%fps%&7] [&f%ping%&7]";
    }

    @Override
    public void draw() {
        drawWatermark();
        super.draw();
    }

    public void drawWatermark() {
        boolean font = HUD.getInstance().watermarkFont.getValue();

        switch (HUD.getInstance().watermarkStyle.getValue().toLowerCase()) {
            case "myth":
                drawWatermarkMyth(ChatUtil.formatLabel(defaultMyth), font);
                break;
            case "sense":
                drawWatermarkSense(ChatUtil.formatLabel(defaultSense), font);
                break;
            case "exhi":
                drawWatermarkExhi(ChatUtil.formatLabel(defaultExhi), font);
                break;
//            case "rise":
//                drawWatermarkRise(ChatUtil.formatLabel(text), font);
//                break;
            case "custom":
                drawWatermarkCustom();
                break;
        }
    }

    public void drawWatermarkMyth(String displayedText, boolean font) {
        if (font){
            FontLoaders.Sfui35.drawStringWithShadow(displayedText.substring(0,1), x, y, ColorUtil.mythColor(10000));
            FontLoaders.Sfui35.drawStringWithShadow(displayedText.substring(1), x + FontLoaders.Sfui35.getStringWidth(text.substring(0,1)), y, -1);
        }else {
            GL11.glPushMatrix();
            GL11.glScalef(2, 2, 2);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(displayedText.substring(0,1), x / 2, y / 2, ColorUtil.mythColor(10000));
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(displayedText.substring(1), (x + 6 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(text.substring(0,1))) / 2, y / 2, -1);
            GL11.glPopMatrix();
        }


        this.width = FontLoaders.Sfui35.getStringWidth(displayedText) + 5;
        this.height = FontLoaders.Sfui35.getHeight();
    }

    public void drawWatermarkSense(String displayedText, boolean font) {
        int posX = (int) x;
        int posY = (int) y;
        int width = (font ? FontLoaders.Sfui16.getStringWidth(displayedText) : fr.getStringWidth(displayedText)) + 10;
        int height = (font ? FontLoaders.Sfui20.getStringHeight(displayedText) : fr.FONT_HEIGHT) + 10;


        Gui.drawRect(posX, posY, posX + width, posY + height, new Color(5, 5, 5, 255).getRGB());
        RenderUtils.drawBorderedRect(posX + .5, posY + .5, posX + width - .5, posY + height - .5, 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB());
        RenderUtils.drawBorderedRect(posX + 2, posY + 2, posX + width - 2, posY + height - 2, 0.5, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB());
        Gui.drawRect(posX + 2.5, posY + 2.5, posX + width - 2.5, posY + 4.5, new Color(9, 9, 9, 255).getRGB());
        RenderUtils.drawGradientSideways(posX + 3, posY + 3, posX + (width / 3), posY + 4, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
        RenderUtils.drawGradientSideways(posX + (width / 3), posY + 3, posX + ((width / 3) * 2), posY + 4, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
        RenderUtils.drawGradientSideways(posX + ((width / 3) * 2), posY + 3, posX + ((width / 3) * 3) - 1, posY + 4, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());

        if (font) {
            FontLoaders.Sfui16.drawStringWithShadow(displayedText, posX + 5, posY + 8, -1);
        } else {
            fr.drawStringWithShadow(displayedText, posX + 5, posY + 8, -1);
        }

        this.width = width + 5;
        this.height = height + 5;

    }

    public void drawWatermarkExhi(String displayedText, boolean font) {
        if (font){
            FontLoaders.Sfui20.drawStringWithShadow(displayedText.substring(0,1), x, y, ColorUtil.rainbow(10000).getRGB());
            FontLoaders.Sfui20.drawStringWithShadow(displayedText.substring(1), x + FontLoaders.Sfui20.getStringWidth(text.substring(0,1)), y, -1);
        }else {
            fr.drawStringWithShadow(displayedText.substring(0,1), x, y, ColorUtil.rainbow(10000).getRGB());
            fr.drawStringWithShadow(displayedText.substring(1), x + fr.getStringWidth(text.substring(0,1)), y, -1);
        }

        this.width = FontLoaders.Sfui20.getStringWidth(displayedText) + 5;
        this.height = FontLoaders.Sfui20.getHeight();

    }

    public void drawWatermarkRise(String displayedText, boolean font) {
        Client.instance.blurProcessor.bloom((int) x, (int) y, (int) width, (int) height, 7, new Color(0, 0, 0, 153));

        if (font){
            FontLoaders.Sfui20.drawStringWithShadow(displayedText.substring(0,1), x, y, ColorUtil.rainbow(10000).getRGB());
            FontLoaders.Sfui20.drawStringWithShadow(displayedText.substring(1), x + FontLoaders.Sfui20.getStringWidth(text.substring(0,1)), y, -1);
        }else {
            fr.drawStringWithShadow(displayedText.substring(0,1), x, y, ColorUtil.rainbow(10000).getRGB());
            fr.drawStringWithShadow(displayedText.substring(1), x + fr.getStringWidth(text.substring(0,1)), y, -1);
        }

        this.width = FontLoaders.Sfui20.getStringWidth(displayedText) + 5;
        this.height = FontLoaders.Sfui20.getHeight();
    }

    public void drawWatermarkCustom() {

    }

    @Override
    public void load(JsonObject object) {
        text = object.get("text").getAsString();
        defaultMyth = object.get("defaultMyth").getAsString();
        defaultSense = object.get("defaultSense").getAsString();
        defaultExhi = object.get("defaultExhi").getAsString();
        super.load(object);
    }

    @Override
    public void save(JsonObject object) {
        object.addProperty("text", text);
        object.addProperty("defaultMyth", defaultMyth);
        object.addProperty("defaultSense", defaultSense);
        object.addProperty("defaultExhi", defaultExhi);
        super.save(object);
    }
}
