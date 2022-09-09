package epiccatto.catto.ui.clickgui.myth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import epiccatto.catto.Client;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleManager;
import epiccatto.catto.module.file.config.Config;
import epiccatto.catto.module.file.config.ConfigManager;
import epiccatto.catto.module.file.config.online.OnlineConfig;
import epiccatto.catto.module.file.config.online.OnlineConfigManager;
import epiccatto.catto.ui.clickgui.myth.component.Component;
import epiccatto.catto.module.settings.Setting;
import epiccatto.catto.module.settings.impl.*;
import epiccatto.catto.ui.clickgui.myth.component.impl.*;
import epiccatto.catto.utils.ChatUtil;
import epiccatto.catto.utils.ColorUtil;
import epiccatto.catto.utils.font.FontLoaders;
import epiccatto.catto.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MythClickGui extends GuiScreen {

    /*
        Created by notthatuwu
        10/28/2020
     */

    public double x, y, width, height, dragX, dragY;
    public Category selectedCategory;
    public Module selectedModule;
    public Config selectedConfig;
    public OnlineConfig selectedOnlineConfig;

    public ArrayList<Module> loadedModules;
    public ArrayList<OnlineConfig> onlineConfigs;
    public ArrayList<Config> localConfigs;

    public boolean dragging, loadedOnlineConfig, loadedLocalConfig = false;

    private static float settingsScrollNow, settingsScroll;

    public ArrayList<Component> components = new ArrayList<>();

    public MythClickGui(){
        x =  scaledResolution().getScaledWidth()/ 2 - 150;
        y = scaledResolution().getScaledHeight() / 2 - 100;
        width = x + 200 * 2;
        height = height + 250;
        selectedCategory = Category.COMBAT;
        loadedModules = ModuleManager.getModules();
        loadedModules.sort((o1, o2) -> FontLoaders.Sfui20.getStringWidth(o2.getName()) - FontLoaders.Sfui20.getStringWidth(o1.getName()));

        for (Module module : loadedModules){
            if (module.isShow())
                updateSettings(module);
        }
    }

    @Override
    public void initGui() {
        if (!loadedOnlineConfig) {
//            new ReloadConfig.GetConfigs().start();
            loadedOnlineConfig = true;
        }
        if (!loadedLocalConfig) {
            localConfigs = ConfigManager.loadConfigs();
            loadedLocalConfig = true;
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
        width = x + 200 * 2;
        height = y + 250;

            RenderUtils.drawGradientSideways(x, y - 20, width, y, ColorUtil.mythColor(20000), ColorUtil.mythColor(10000));
        Gui.drawRect(x, y, width, height, new Color(49, 45, 45, 255).getRGB());
        //Category box
        Gui.drawRect(x, y, x + 60, height, new Color(38, 37, 37, 255).getRGB());
        //Module box
        Gui.drawRect(x + 60, y, x + 180, height, new Color(38, 37, 37, 100).getRGB());
        //Settings box
        Gui.drawRect(x + 180, y, width, height, new Color(38, 37, 37, 40).getRGB());


        FontLoaders.Sfui22.drawString(Client.clientName + " - " + Client.clientVersion + " - " + Client.build, (float) (x + 5), (int) y - FontLoaders.Sfui22.getHeight() - 5, new Color(255, 255, 255, 255).getRGB());

        int offset = 0;
        for (Category category : Category.values()) {
            //if (category != Category.CUSTOMIZE) {
            RenderUtils.drawImage(x + 20, y + 20 + offset, 20, 20, new ResourceLocation("catto/clickgui/myth/categorylogo/" + category.name.toLowerCase() + ".png"), selectedCategory == category ? new Color(255, 255, 255) : new Color(100, 100, 100));
            offset += 40;
            //}
        }

        offset = 10;

        if (selectedCategory == Category.CONFIG) {
            for (OnlineConfig onlineConfig : onlineConfigs) {
                RenderUtils.drawRoundedRect((float) x + 65, (float) y + 1 + offset, (float) x + 70 + FontLoaders.Sfui20.getStringWidth(onlineConfig.getName()), (float) y + 15 + offset, 4, new Color(28, 28, 28).getRGB());
                FontLoaders.Sfui20.drawString(onlineConfig.getName(), (int) x + 67, (int) (y + 5) + offset, new Color(53, 243, 0, 255).getRGB());
                offset += 20;

                if (selectedOnlineConfig != null && onlineConfig == selectedOnlineConfig) {
                    int configInfoOffset = (int) (10 + FontLoaders.Sfui20.getHeight() + 5);

                    ArrayList<String> info = new ArrayList<>();
                    info.add("Name: " + onlineConfig.getName());
                    info.add("Description: " + onlineConfig.getDescription());
                    info.add("Author: " + onlineConfig.getAuthor());
                    info.add("Last Update: " + onlineConfig.getDate() + (Integer.parseInt(onlineConfig.getDate()) == 1 ? " Day ago" : " Days ago"));

                    for (String s : info) {
                        FontLoaders.Sfui20.drawString(s, (int) (275 + x - 90), (int) (configInfoOffset + y + 1), new Color(200, 200, 200).getRGB());
                        configInfoOffset += FontLoaders.Sfui20.getHeight() + 5;
                    }
                }
            }
        }
        for (Config config : localConfigs) {
            if (selectedCategory == Category.CONFIG) {
                RenderUtils.drawRoundedRect((float) x + 65, (float) y + 1 + offset, (float) x + 70 + FontLoaders.Sfui20.getStringWidth(config.getName()), (float) y + 15 + offset, 4, new Color(28, 28, 28).getRGB());
                FontLoaders.Sfui20.drawString(config.getName(), (int) x + 67, (int) (y + 5) + offset, new Color(0, 247, 255, 255).getRGB());
                offset += 20;
            }

            if (selectedConfig != null && selectedCategory == Category.CONFIG && config == selectedConfig) {
                int configInfoOffset = (int) (10 + FontLoaders.Sfui20.getHeight() + 5);

                ArrayList<String> info = new ArrayList<>();
                info.add("Name: " + config.getName());
                info.add("Description: " + config.getDescription());

                for (String s : info) {
                    FontLoaders.Sfui20.drawString(s, (int) (275 + x - 90), (int) (configInfoOffset + y + 1), new Color(200, 200, 200).getRGB());
                    configInfoOffset += FontLoaders.Sfui20.getHeight() + 5;
                }
            }
        }

        for (Module module : loadedModules) {
            if ((module.getCategory() == selectedCategory && module.getCategory() != Category.RENDER) || (module.getCategory() == Category.RENDER /*|| module.getCategory() == Category.CUSTOMIZE)*/ && selectedCategory == Category.RENDER)) {
                RenderUtils.drawRoundedRect((float) x + 65, (float) y + 1 + offset, (float) x + 70 + FontLoaders.Sfui20.getStringWidth(module.getName()), (float) y + 15 + offset, 4, module.isEnabled() ? new Color(0, 216, 245).getRGB() : new Color(28, 28, 28).getRGB());
                FontLoaders.Sfui20.drawString(module.getName(), (int) x + 67, (int) (y + 5) + offset, module.isEnabled() ? new Color(255, 255, 255, 255).getRGB() : new Color(170, 170, 170, 255).getRGB());
                offset += 20;
            }
            if (selectedModule != null && selectedCategory != Category.CONFIG) {
                FontLoaders.Sfui20.drawString(selectedModule.getCategory().name + " > " + selectedModule.getName(), (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedModule.getCategory().name + " > " + selectedModule.getName())/2, (int) y + 10, new Color(200, 200, 200).getRGB());
                if (Client.instance.settingsManager.getSettingsFromModule(selectedModule).isEmpty()) {
                    FontLoaders.Sfui20.drawString("No settings", (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedModule.getCategory().name + " > " + selectedModule.getName()), (int) y + 10 + FontLoaders.Sfui20.getHeight() + 5, new Color(200, 200, 200).getRGB());
                } else {

                    GL11.glPushMatrix();
                    GL11.glPopMatrix();
                    this.prepareScissorBox((float) x + 190, (float)y + 20, (float)width, (float)height);
                    GL11.glEnable(3089);

                    int dWheel = Mouse.getDWheel();

                    if (isHovered(mouseX, mouseY, x + 180, y, width, height)) {
                        if (dWheel < 0 && Math.abs(settingsScroll) + 170 < (Client.instance.settingsManager.getSettingsFromModule(selectedModule).size() * 25)) {
                            settingsScroll -= 32;
                        }
                        if (dWheel > 0 && settingsScroll < 0) {
                            settingsScroll += 32;
                        }
                    }

                    if (settingsScrollNow != settingsScroll) {
                        settingsScrollNow += (settingsScroll - settingsScrollNow) / 20;
                        settingsScrollNow = (int) settingsScrollNow;
                    }

                    //float valuey = windowY + 100 + valueRoleNow;

                    int offsetS = (int) ((int) (10 + FontLoaders.Sfui20.getHeight() + 5) + settingsScrollNow);
                    for (Component component : components) {
                        if (component.module == selectedModule && component.setting.isVisible()) {
                            component.y = offsetS;
                            component.drawScreen(mouseX, mouseY);
                            offsetS += component.height();
                        }
                    }
                    GL11.glDisable(3089);
                }
            } else if (selectedCategory != Category.CONFIG){
                FontLoaders.Sfui20.drawString(selectedCategory.name + " > ", (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedCategory.name + " > ")/2, (int) y + 10, new Color(200, 200, 200).getRGB());
            }else {
                if (selectedConfig != null) {
                    FontLoaders.Sfui20.drawString(selectedCategory.name + " > " + selectedConfig.getName() + EnumChatFormatting.GOLD + " (Local)", (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedCategory.name + " > " + selectedConfig.getName())/2, (int) y + 10, new Color(200, 200, 200).getRGB());
                }else if (selectedOnlineConfig != null){
                    FontLoaders.Sfui20.drawString(selectedCategory.name + " > " + selectedOnlineConfig.getName() + EnumChatFormatting.GREEN + " (Online)", (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedCategory.name + " > " + selectedOnlineConfig.getName())/2, (int) y + 10, new Color(200, 200, 200).getRGB());
                } else {
                    FontLoaders.Sfui20.drawString(selectedCategory.name + " > ", (int) x + 275 - FontLoaders.Sfui20.getStringWidth(selectedCategory.name + " > ")/2, (int) y + 10, new Color(200, 200, 200).getRGB());
                }
            }
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        InputHelper.mouse[mouseButton] = InputHelper.Status.CLICKED;

        if (isHovered(mouseX, mouseY, x, y - 20, width, y) && (InputHelper.mouse[0] == InputHelper.Status.CLICKED)) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }

        int offset = 10;
        for (Category category : Category.values()) {
            //if (category != Category.CUSTOMIZE) {
                if (isHovered(mouseX, mouseY, (float) x + 20, (float) y + 20 + offset, (float) x + 40, (float) y + 40 + offset) && (InputHelper.mouse[0] == InputHelper.Status.CLICKED)) {
                    selectedCategory = category;
                }
                offset += 40;
            //}
        }
        offset = 10;

        if (selectedCategory == Category.CONFIG) {

            for (OnlineConfig onlineConfig : onlineConfigs) {
                offset += 20;
                if (isHovered(mouseX, mouseY, (float) x + 65, (float) y + 1 - 20 + offset, (float) x + 70 + FontLoaders.Sfui20.getStringWidth(onlineConfig.getName()), (float) y + 15 + offset - 20)) {
                    if (InputHelper.mouse[0] == InputHelper.Status.CLICKED) {
                        OnlineConfigManager.jsonGetRequest(onlineConfig.getUrl());
                        Config config = new Config(onlineConfig.getName());
                        try {
                            JsonObject configJson = new JsonParser().parse(OnlineConfigManager.jsonGetRequest(onlineConfig.getUrl())).getAsJsonObject();
                            config.load(configJson, false);
                            ChatUtil.sendChatMessageWPrefix(EnumChatFormatting.GREEN + onlineConfig.getName() + " Online Config loaded");
                        }catch (Exception e){
                            ChatUtil.sendChatMessageWPrefix(EnumChatFormatting.RED + onlineConfig.getName() + " Online Config Failed to load " + Arrays.toString(e.getStackTrace()));
                            e.printStackTrace();
                        }
                    }
                    if (InputHelper.mouse[1] == InputHelper.Status.CLICKED) {
                        selectedOnlineConfig = onlineConfig;
                        selectedConfig = null;
                    }
                }
            }

            for (Config config : localConfigs) {
                offset += 20;
                if (isHovered(mouseX, mouseY, (float) x + 65, (float) y + 1 - 20 + offset, (float) x + 70 + FontLoaders.Sfui20.getStringWidth(config.getName()), (float) y + 15 + offset - 20)) {
                    if (InputHelper.mouse[0] == InputHelper.Status.CLICKED) {
                        ChatUtil.sendChatMessageWPrefix(EnumChatFormatting.GREEN + config.getName() + " Local Config loaded");
                        Client.instance.configManager.loadConfig(config.getName());
                    }
                    if (InputHelper.mouse[1] == InputHelper.Status.CLICKED) {
                        selectedConfig = config;
                        selectedOnlineConfig = null;

                    }
                }
            }
        }

        for (Module module : loadedModules) {
            if ((module.getCategory() == selectedCategory && module.getCategory() != Category.RENDER) || (module.getCategory() == Category.RENDER/* || module.getCategory() == Category.CUSTOMIZE*/) && selectedCategory == Category.RENDER) {
                offset += 20;
                if (isHovered(mouseX, mouseY, (float) x + 65, (float)y + 1 - 20 + offset, (float)x + 70 + FontLoaders.Sfui20.getStringWidth(module.getName()), (float)y + 15 + offset - 20)) {
                    if (InputHelper.mouse[0] == InputHelper.Status.CLICKED) {
                        module.setEnabled(!module.isEnabled());
                    }
                    if (InputHelper.mouse[1] == InputHelper.Status.CLICKED) {
                        settingsScroll = 0;
                        selectedModule = module;
                    }
                }
            }
        }
        for (Component component : components) {
            if (component.module == selectedModule && component.setting.isVisible())
                component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Component component : components) {
            if (component.module == selectedModule && component.setting.isVisible())
                component.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        InputHelper.mouse[clickedMouseButton] = InputHelper.Status.PRESSED;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        InputHelper.mouse[state] = InputHelper.Status.NONE;

        for (Component component : components) {
            if (component.module == selectedModule && component.setting.isVisible())
                component.mouseReleased(mouseX, mouseY, state);
        }

        dragging = false;
    }

    @Override
    public void onGuiClosed() {
        loadedOnlineConfig = false;
        loadedLocalConfig = false;
        super.onGuiClosed();
    }

    public boolean isHovered(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public void updateSettings(Module module){
        int offset = (int) (10 + FontLoaders.Sfui20.getHeight() + 5);
        if (Client.instance.settingsManager.getSettingsFromModule(module) != null) {
            for (Setting setting : Client.instance.settingsManager.getSettingsFromModule(module)) {
                if (setting instanceof BooleanSetting) {
                    BooleanComponent booleanComponent = new BooleanComponent(275, offset, this, module, (BooleanSetting) setting);
                    components.add(booleanComponent);
                    offset += booleanComponent.height();
                }

                if (setting instanceof NumberSetting) {
                    NumberComponent numberSetting = new NumberComponent(275, offset, this, module, (NumberSetting) setting);
                    components.add(numberSetting);
                    offset += numberSetting.height();
                }
                if (setting instanceof ModeSetting) {
                    ModeComponent modeComponent = new ModeComponent(275, offset, this, module, (ModeSetting) setting);
                    components.add(modeComponent);
                    offset += modeComponent.height();
                }

                if (setting instanceof NoteSetting) {
                    NoteComponent noteComponent = new NoteComponent(275, offset, this, module, (NoteSetting) setting);
                    components.add(noteComponent);
                    offset += noteComponent.height();
                }

                if (setting instanceof ColorSetting) {
                    ColorComponent colorComponent = new ColorComponent(275, offset, this, module, (ColorSetting) setting);
                    components.add(colorComponent);
                    offset += colorComponent.height();
                }

                if (setting instanceof StringSetting) {
                    StringComponent colorComponent = new StringComponent(275, offset, this, module, (StringSetting) setting);
                    components.add(colorComponent);
                    offset += colorComponent.height();
                }

            }
        }

    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
    }

    private ScaledResolution scaledResolution(){
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static class InputHelper{
        public static Status[] mouse = new Status[256];
        public enum Status {
            NONE,
            PRESSED,
            CLICKED
        }
    }

}
