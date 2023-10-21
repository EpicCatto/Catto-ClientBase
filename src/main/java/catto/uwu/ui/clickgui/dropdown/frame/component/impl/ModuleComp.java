package catto.uwu.ui.clickgui.dropdown.frame.component.impl;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NoteSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.dropdown.frame.component.Component;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings.BooleanComponent;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings.ModeComponent;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings.NoteComponent;
import catto.uwu.ui.clickgui.dropdown.frame.component.impl.settings.NumberComponent;
import catto.uwu.utils.ChatUtil;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ModuleComp extends Component {

    private final ArrayList<Component> components = new ArrayList<>();

    public ModuleComp(double x, double y, double offset, Frame parent, Module module) {
        this.x = x;
        this.y = y;
        this.offset = offset;
        this.parent = parent;
        this.module = module;
        this.settings = module.getSettings();
        updateSettings();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect((int) x, (int) y, (int) (x + 100), (int) (y + 20), module.isEnabled() ? new Color(198, 109, 255, 255).getRGB() : new Color(0, 0, 0, 150).getRGB());
        FontLoaders.Sfui20.drawString(module.getName(), (int) x + 5, (int) (y + 7),-1);
        Gui.drawRect((int) x, (int) y, (int) (x + 100), (int) (y + 1), new Color(255, 255, 255, 76).getRGB());
        AtomicInteger height = new AtomicInteger();
        if (module.isExtended()) {
            components.forEach(component -> {
                if ((component.settings.get(0).isVisible() && (component.settings.get(0).getParentNote() != null ? component.settings.get(0).getParentNote().getValue() : true))) {
                    component.x = x + 100;
                    component.y = y + height.get();
                    component.drawScreen(mouseX, mouseY);
                    height.addAndGet(component.height());
                }
            });

        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, (int) x, (int) y, (int) (x + 100), (int) (y + 20))) {
            if (mouseButton == 0)
                module.toggle();
            if (mouseButton == 1) {
                module.setExtended(!module.isExtended());
            }
        }
        if (module.isExtended()) {
            components.forEach(component -> {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            });
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (module.isExtended()) {
            components.forEach(component -> {
                component.mouseReleased(mouseX, mouseY, state);
            });
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public int height() {
        return 20;
    }

    private void updateSettings(){
        AtomicInteger height = new AtomicInteger();
        settings.forEach(setting -> {
            if (setting instanceof BooleanSetting) {
                BooleanSetting booleanSetting = (BooleanSetting) setting;
                BooleanComponent booleanSettingComponent = new BooleanComponent(x + 100, y + height.get(),parent, module, booleanSetting);
                    components.add(booleanSettingComponent);
                    height.addAndGet(booleanSettingComponent.height());

            }
            if (setting instanceof ModeSetting) {
                ModeSetting modeSetting = (ModeSetting) setting;
                ModeComponent modeSettingComponent = new ModeComponent(x + 100, y + height.get(), parent, module, modeSetting);
                    components.add(modeSettingComponent);
                    height.addAndGet(modeSettingComponent.height());
            }
            if (setting instanceof NumberSetting) {
                NumberSetting numberSetting = (NumberSetting) setting;
                NumberComponent numberSettingComponent = new NumberComponent(x + 100, y + height.get(), parent, module, numberSetting);
                    components.add(numberSettingComponent);
                    height.addAndGet(numberSettingComponent.height());

            }
            if (setting instanceof NoteSetting) {
                NoteSetting noteSetting = (NoteSetting) setting;
                NoteComponent noteSettingComponent = new NoteComponent(x + 100, y + height.get(), parent, module, noteSetting);
                components.add(noteSettingComponent);
                height.addAndGet(noteSettingComponent.height());
            }
        });
    }
}
