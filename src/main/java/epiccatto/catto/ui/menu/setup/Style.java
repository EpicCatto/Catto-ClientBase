package epiccatto.catto.ui.menu.setup;

import epiccatto.catto.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;

public class Style {
    private String name;
    private String description;
    private String author;
    private ResourceLocation logo;
    private int x, y, width, height;
    public Style(String name, String description, String author, ResourceLocation logo) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ResourceLocation getLogo() {
        return logo;
    }

    public void setLogo(ResourceLocation logo) {
        this.logo = logo;
    }

    public void updatePos(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawStyle(boolean selected){
        Gui.drawRect(x, y, x + width, y + height, selected ? new Color(0, 0, 0, 100).getRGB() : new Color(0, 0, 0, 50).getRGB());
        FontLoaders.Sfui22.drawStringWithShadow(name, x + 5, y + 5, Color.WHITE.getRGB());
//        FontLoaders.Sfui20.drawStringWithShadow(WordUtils.wrap(description, 10), x + 5, y + 5 + FontLoaders.Sfui35.getHeight(), Color.WHITE.getRGB());
        String in = WordUtils.wrap(description, width / FontLoaders.Sfui20.getStringWidth("a"));
        String[] lines = in.split("\\r?\\n");
        int i = 0;
        for (String line : lines) {
            FontLoaders.Sfui20.drawStringWithShadow(line, x + 5, y + 5 + FontLoaders.Sfui35.getHeight() + (i * FontLoaders.Sfui20.getHeight()), Color.WHITE.getRGB());
            i++;
        }
        FontLoaders.Sfui15.drawStringWithShadow("By " + author, x + 5, y + height - FontLoaders.Sfui22.getHeight(), Color.WHITE.getRGB());
    }

    public boolean isHovered(int mouseX, int mouseY){
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
