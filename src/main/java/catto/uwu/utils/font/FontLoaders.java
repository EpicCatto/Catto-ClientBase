package catto.uwu.utils.font;

import catto.uwu.Client;
import catto.uwu.module.file.FileFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;

public abstract class FontLoaders {
    public static HashMap<String, CFontRenderer> fonts = new HashMap<String, CFontRenderer>();

    public static CFontRenderer Ali15 = new CFontRenderer(FontLoaders.getFonts("Ali.ttf",15), true, true);
    public static CFontRenderer Ali18 = new CFontRenderer(FontLoaders.getFonts("Ali.ttf",18), true, true);
    public static CFontRenderer Ali25 = new CFontRenderer(FontLoaders.getFonts("Ali.ttf",25), true, true);

    public static CFontRenderer Sfui14 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",14), true, true);
    public static CFontRenderer Sfui15 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",15), true, true);
    public static CFontRenderer Sfui16 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",16), true, true);
    public static CFontRenderer Sfui18 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",18), true, true);
    public static CFontRenderer Sfui19 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",19), true, true);
    public static CFontRenderer Sfui20 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",20), true, true);
    public static CFontRenderer Sfui22 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",22), true, true);
    public static CFontRenderer Sfui35 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",40), true, true);
    public static CFontRenderer Sfui40 = new CFontRenderer(FontLoaders.getFonts("SF-UI.ttf",40), true, true);

    //ez
    private static Font getFonts(String fontname,int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("catto/font/"+fontname)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static CFontRenderer getCustomFont(String fontName, int size) {
        if(fonts.containsKey(fontName + size))
            return fonts.get(fontName + size);
        Font font;
        try {
            InputStream is;
            if(fontName.startsWith("http")) {
                URL url = new URL(fontName);
                URLConnection connection = url.openConnection();
                connection.addRequestProperty("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                is = new BufferedInputStream(connection.getInputStream());
            } else {
                is = Files.newInputStream(new File(Client.instance.fileFactory.getRoot() + "/fonts", fontName + ".ttf").toPath());
            }
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load fonts!");
            font = new Font("default", 0, size);
        }
        CFontRenderer cfr = new CFontRenderer(font, true, false);
        fonts.put(fontName + size, cfr);
        return cfr;
    }



}
