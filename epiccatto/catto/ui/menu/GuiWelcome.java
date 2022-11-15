package epiccatto.catto.ui.menu;

import epiccatto.catto.ui.menu.setup.Style;
import epiccatto.catto.utils.TimerUtil;
import epiccatto.catto.utils.font.FontLoaders;
import epiccatto.catto.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiWelcome extends GuiScreen {

    private TimerUtil timer = new TimerUtil();
    private int page, stage = 0;

    private ScreenStage screenStage;
    private ArrayList<Style> styleList;
    private Style selectedStyle;
    private long lastClick;

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        styleList = new ArrayList<>();
        styleList.add(new Style("Gato Client", "Good old gato client theme and uuhh test line break test line break test line break test line break", "NotThatUwU, Memer(Assets)", new ResourceLocation("catto/image/gato icon.png")));
        styleList.add(new Style("Gato Client2", "Good old gato client theme and uuhh test line break test line break test line break test line break", "NotThatUwU, Memer(Assets)", new ResourceLocation("catto/image/gato icon.png")));

        Collections.reverse(styleList);
        page = 0;
        stage = 0;
        selectedStyle= null;
        if (screenStage == null) {
            screenStage = ScreenStage.Welcome;
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        switch (screenStage){
            case Welcome:
                drawWelcome(sr);
                break;
            case Setup:
                drawSetup(sr);
                break;
            case Finish:
                drawFinish(sr);
                break;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_SPACE){
            page++;
            timer.reset();
        }
    }

    private void drawWelcome(ScaledResolution sr) {
        String[] welcomeSplash = {"Hello!", "Welcome to Catto client!", "This is the first time you've opened Catto.", "Here's some quick setup for you!"};
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 255).getRGB());
        if (timer.hasReached(3000)) {
            page++;
            timer.reset();
        }
        if (page > welcomeSplash.length - 1) {
            page = welcomeSplash.length - 1;
            screenStage = ScreenStage.Setup;
        }
        FontLoaders.Sfui40.drawStringWithShadow(welcomeSplash[page], sr.getScaledWidth() / 2 - FontLoaders.Sfui40.getStringWidth(welcomeSplash[page]) / 2, sr.getScaledHeight() / 2 - FontLoaders.Sfui40.getHeight() / 2, -1);
        FontLoaders.Sfui22.drawStringWithShadow("Press spacebar to skip", sr.getScaledWidth() / 2 - FontLoaders.Sfui22.getStringWidth("Press spacebar to skip") / 2, sr.getScaledHeight() / 2 + FontLoaders.Sfui40.getHeight(), -1);
    }

    private void drawSetup(ScaledResolution sr){
        mc.getTextureManager().bindTexture(new ResourceLocation("catto/image/cattosunblur.png"));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());


        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 70).getRGB());
        String[] setupSplash = {"Pick your style!"};
        AtomicInteger count = new AtomicInteger();
        styleList.forEach(style -> {
            //            style.updatePos(sr.getScaledWidth() / 2 + (count.get() * 200), sr.getScaledHeight() / 3, 200, 100);
            style.updatePos((sr.getScaledWidth() / 2)  - (count.get() * 205), sr.getScaledHeight() / 3, 200, 100);
            style.drawStyle(selectedStyle == style);
//            style.updatePos(sr.getScaledWidth() / 2 - 200, sr.getScaledHeight() / 3, 200, 100);
            count.getAndIncrement();
        });

        FontLoaders.Sfui35.drawString(setupSplash[0], sr.getScaledWidth() / 2 - FontLoaders.Sfui35.getStringWidth(setupSplash[0]) / 2, sr.getScaledHeight() / 4 - 50 + FontLoaders.Sfui35.getHeight() / 2, -1);
        RenderUtils.drawImage(sr.getScaledWidth() / 2 - FontLoaders.Sfui35.getStringWidth(setupSplash[0]), sr.getScaledHeight()/4 - 50, 50, 50, new ResourceLocation("catto/image/gato icon.png"));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        styleList.forEach(style -> {
            // double click check
            if (selectedStyle != null && style.isHovered(mouseX, mouseY) && System.currentTimeMillis() - lastClick < 500) {
                screenStage = ScreenStage.Finish;
                System.out.println("Selected style: " + selectedStyle.getName());
                System.out.println("ms clicked: " + (lastClick - System.currentTimeMillis()));
            }

            if(style.isHovered(mouseX, mouseY)){
                selectedStyle = style;
                lastClick = System.currentTimeMillis();
            }
        });
    }

    private void drawFinish(ScaledResolution sr){
        mc.getTextureManager().bindTexture(new ResourceLocation("catto/image/cattosunblur.png"));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
    }

    public boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    enum ScreenStage{
        Welcome, Setup, Finish
    }

}
