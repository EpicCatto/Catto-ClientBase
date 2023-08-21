package catto.uwu.ui.clickgui.myth.component;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.Setting;
import catto.uwu.ui.clickgui.myth.MythClickGui;

public class Component {
    public double x, y, x2, y2;
    public MythClickGui parent;
    public Module module;
    public Setting setting;

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void drawScreen(int mouseX, int mouseY) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public int height(){
        return 0;
    }

    public boolean isHovered(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

}
