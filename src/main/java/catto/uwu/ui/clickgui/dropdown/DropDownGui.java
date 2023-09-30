package catto.uwu.ui.clickgui.dropdown;

import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class DropDownGui extends GuiScreen {
    public List<Frame> frames = new ArrayList<>();
    public Frame dragging = null;
    public int dragX, dragY;

    public DropDownGui() {

        int x = 10;
        for (Category category : Category.values()) {
            frames.add(new Frame(x, 20, category));
            x += 110;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame frame : frames) {
            frame.drawScreen(mouseX, mouseY, partialTicks);
        }

        if(dragging == null)
            return;
        dragging.x = mouseX - dragX;
        dragging.y = mouseY - dragY;
        dragging.x2 = dragging.x + 100;
        dragging.y2 = dragging.y + 10;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);

            if (mouseX < frame.x || mouseX > frame.x2)
                continue;
            if (mouseY <= frame.y)
                continue;
            if (mouseY <= frame.y2) {
                if (mouseButton == 0) {
                    dragging = frame;
                    dragX = (int) (mouseX - frame.x);
                    dragY = (int) (mouseY - frame.y);

                } else if (mouseButton == 1) {
                    frame.expanded = !frame.expanded;
                }
            }

        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = null;

        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, state);
        }
    }

}
