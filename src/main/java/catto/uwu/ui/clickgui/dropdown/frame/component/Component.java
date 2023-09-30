package catto.uwu.ui.clickgui.dropdown.frame.component;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.Setting;
import catto.uwu.ui.clickgui.dropdown.DropDownGui;
import catto.uwu.ui.clickgui.dropdown.frame.Frame;
import catto.uwu.ui.clickgui.myth.MythClickGui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Component {
    public double x, y, x2, y2, offset;
    public Frame parent;
    public Module module;
    public List<Setting> settings = new ArrayList<>();

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
