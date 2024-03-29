package catto.uwu.utils.render;

import catto.uwu.Client;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

public class ColorUtil {
    public static int getColor(String mode, int delay){
        int color = 0;

        switch (mode){
            case "Myth":
                color = mythColor(delay);
                break;
            case "Rainbow":
                color = rainbow(delay).getRGB();
                break;
        }

        return color;
    }

    public static int mythColor(int delay) {
        float speed = 3200.0F;
        float hue = (float)(System.currentTimeMillis() % (int)speed) + (delay / 2);
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5D)
            hue = 0.5F - hue - 0.5F;
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1.0F);
    }

    public static double  rainbowState;

    public static Color rainbow(int delay) {
        rainbowState = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 75L));
        rainbowState %= 90.0D;
        return Color.getHSBColor((float)(rainbowState / 45.0D), 0.5F, 1.0F);
    }

    public static Color gatoPulseBrightness(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], brightness % 2.0F));
    }

    public static String translateColor(String original) {
        return original.replaceAll("&([0-9a-fk-or])", "\u00a7$1");
    }

    public static int fromHEX(String sex){
        int r = Integer.valueOf(sex.substring(1, 3), 16); int g = Integer.valueOf(sex.substring(3, 5), 16); int b = Integer.valueOf(sex.substring(5, 7), 16);
        return new Color(r, g, b).getRGB();
    }

    public static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    @NotNull
    public static String translateAlternateColorCodes(char altColorChar, @NotNull String textToTranslate) {
        Preconditions.checkArgument(textToTranslate != null, "Cannot translate null text");
        char[] b = textToTranslate.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

}
