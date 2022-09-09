package epiccatto.catto.utils.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public abstract class FontLoaders {

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

}
